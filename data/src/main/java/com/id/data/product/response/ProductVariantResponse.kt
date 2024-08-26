package com.id.data.product.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ProductVariantResponse(

    @field:SerializedName("variantPrice")
    val variantPrice: Int? = null,

    @field:SerializedName("variantName")
    val variantName: String? = null
)