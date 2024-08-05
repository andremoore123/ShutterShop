package com.id.data.product

import com.id.domain.ext.Resource
import com.id.domain.product.IProductRepository
import com.id.domain.product.ProductFilterParams
import com.id.domain.product.ProductModel
import javax.inject.Inject

/**
 * Created by: andreputras.
 * Date: 05/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
class ProductRepository @Inject constructor(

) : IProductRepository {
    override suspend fun fetchProducts(productFilterParams: ProductFilterParams?): Resource<List<ProductModel>> {
        val dummyData = listOf(
            ProductModel(
                id = 3063,
                itemName = "Michael O'Brien",
                itemSold = "pericula",
                itemPrice = "tincidunt",
                itemRating = "ponderum",
                itemSeller = "nostrum"
            ),
            ProductModel(
                id = 3063,
                itemName = "Michael O'Brien",
                itemSold = "pericula",
                itemPrice = "tincidunt",
                itemRating = "ponderum",
                itemSeller = "nostrum"
            ),
        )
        return Resource.Success(dummyData)
    }
}