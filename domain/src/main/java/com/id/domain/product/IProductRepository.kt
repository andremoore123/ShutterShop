package com.id.domain.product

import com.id.domain.ext.Resource

/**
 * Created by: andreputras.
 * Date: 05/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
interface IProductRepository {
    suspend fun fetchProducts(productFilterParams: ProductFilterParams? = null): Resource<List<ProductModel>>

    suspend fun searchProduct(query: String): Resource<List<ProductModel>>
}
