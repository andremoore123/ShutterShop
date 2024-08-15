package com.id.data.product

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.id.data.product.mapper.mapToModel
import com.id.data.product.response.mapToModel
import com.id.domain.product.IProductRepository
import com.id.domain.product.ProductDetailModel
import com.id.domain.product.ProductFilterParams
import com.id.domain.product.ProductModel
import com.id.domain.utils.network_response.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import javax.inject.Inject

/**
 * Created by: andreputras.
 * Date: 05/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
class ProductRepository @Inject constructor(
    private val apiService: ProductApiService
) : IProductRepository {

    override fun fetchProducts(productFilterParams: ProductFilterParams): Flow<PagingData<ProductModel>> =
        Pager(
            PagingConfig(10)
        ) {
            ProductPagingSource(apiService, parameter = productFilterParams, "")
        }.flow.map { it.map { data -> data.mapToModel() } }

    override fun searchProduct(query: String): Flow<PagingData<ProductModel>> = Pager(
        PagingConfig(10)
    ) {
        ProductPagingSource(apiService, parameter = ProductFilterParams(), query)
    }.flow.map {
        it.map { data ->
            data.mapToModel()
        }
    }

    override suspend fun fetchProductDetail(id: String): NetworkResponse<ProductDetailModel> {
        return try {
            val response = apiService.fetchProductDetail(id).data
            response?.let {
                NetworkResponse.Success(it.mapToModel())
            } ?: throw NullPointerException()
        } catch (e: NullPointerException) {
            NetworkResponse.EmptyValueError
        } catch (e: HttpException) {
            NetworkResponse.HttpError(e.code(), e.message())
        } catch (e: Exception) {
            NetworkResponse.UnknownError(e)
        }
    }
}
