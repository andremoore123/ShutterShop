package com.id.data.product.response

import com.google.gson.annotations.SerializedName

data class ProductVariantResponse(

    @field:SerializedName("variantPrice")
    val variantPrice: Int? = null,

    @field:SerializedName("variantName")
    val variantName: String? = null
)