package com.id.data.product

import com.id.domain.ext.Resource
import com.id.domain.product.IProductRepository
import com.id.domain.product.ProductDetailModel
import com.id.domain.product.ProductFilterParams
import com.id.domain.product.ProductModel
import com.id.domain.product.VarianceModel
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

    override suspend fun searchProduct(query: String): Resource<List<ProductModel>> {
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

    override suspend fun fetchProductDetail(id: Int): Resource<ProductDetailModel> {
        val dummyValue = ProductDetailModel(
            id = 5797,
            productName = "Macbook",
            productDesc = "Loremipsdfudjfsdfndsf",
            productVariance = listOf(
                VarianceModel(id = 6613, title = "reque"), VarianceModel(
                    id = 6110,
                    title = "sit"
                )
            ),
            productPrice = "25.000.000",
            productSold = "23",
            productRating = "4.5",
            totalRating = "300",
            imageUrl = listOf("test", "test")
        )
        return Resource.Success(dummyValue)
    }
}
