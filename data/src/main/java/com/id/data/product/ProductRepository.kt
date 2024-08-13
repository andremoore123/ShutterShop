package com.id.data.product

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.id.data.product.response.mapToModel
import com.id.domain.ext.Resource
import com.id.domain.product.IProductRepository
import com.id.domain.product.ProductDetailModel
import com.id.domain.product.ProductFilterParams
import com.id.domain.product.ProductModel
import com.id.domain.product.VarianceModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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

    override suspend fun fetchProductDetail(id: Int): Resource<ProductDetailModel> {
        val dummyValue = ProductDetailModel(
            id = "5797",
            productName = "Macbook",
            productDesc = "Loremipsdfudjfsdfndsf",
            productVariance = listOf(
                VarianceModel(id = 6613, title = "reque"), VarianceModel(
                    id = 6110,
                    title = "sit"
                )
            ),
            productStore = "Uvuvwvwe",
            productPrice = 230232323,
            productSold = "23",
            productRating = "4.5",
            totalRating = "300",
            imageUrl = listOf("test", "test")
        )
        return Resource.Success(dummyValue)
    }
}
