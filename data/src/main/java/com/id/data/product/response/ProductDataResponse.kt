package com.id.data.product.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.id.domain.product.ProductModel

@Keep
data class ProductDataResponse(

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("sale")
    val sale: Int? = null,

    @field:SerializedName("productId")
    val productId: String? = null,

    @field:SerializedName("store")
    val store: String? = null,

    @field:SerializedName("productRating")
    val productRating: Double? = null,

    @field:SerializedName("brand")
    val brand: String? = null,

    @field:SerializedName("productName")
    val productName: String? = null,

    @field:SerializedName("productPrice")
    val productPrice: Int? = null
)

fun ProductDataResponse.mapToModel(): ProductModel {
    return ProductModel(
        id = productId ?: "",
        itemName = productName ?: "",
        itemSold = sale ?: 0,
        itemPrice = productPrice ?: 0,
        itemRating = productRating.toString(),
        itemSeller = store ?: "",
        imageUrl = image ?: ""
    )
}