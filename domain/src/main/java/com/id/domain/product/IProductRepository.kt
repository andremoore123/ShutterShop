package com.id.domain.product

import androidx.paging.PagingData
import com.id.domain.ext.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by: andreputras.
 * Date: 05/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
interface IProductRepository {

    fun fetchProducts(productFilterParams: ProductFilterParams): Flow<PagingData<ProductModel>>

    fun searchProduct(query: String): Flow<PagingData<ProductModel>>

    suspend fun fetchProductDetail(id: Int): Resource<ProductDetailModel>
}
