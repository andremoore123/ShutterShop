package com.id.data.product.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ProductDetailResponse(

    @field:SerializedName("image")
    val image: List<String>? = null,

    @field:SerializedName("productId")
    val productId: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("totalRating")
    val totalRating: Int? = null,

    @field:SerializedName("store")
    val store: String? = null,

    @field:SerializedName("productName")
    val productName: String? = null,

    @field:SerializedName("totalSatisfaction")
    val totalSatisfaction: Int? = null,

    @field:SerializedName("sale")
    val sale: Int? = null,

    @field:SerializedName("productVariant")
    val productVariant: List<ProductVariantResponse>? = null,

    @field:SerializedName("stock")
    val stock: Int? = null,

    @field:SerializedName("productRating")
    val productRating: Double? = null,

    @field:SerializedName("brand")
    val brand: String? = null,

    @field:SerializedName("productPrice")
    val productPrice: Int? = null,

    @field:SerializedName("totalReview")
    val totalReview: Int? = null
)

