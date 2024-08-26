package com.id.data.transaction.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class TransactionItemResponse(

    @field:SerializedName("quantity")
    val quantity: Int? = null,

    @field:SerializedName("productId")
    val productId: String? = null,

    @field:SerializedName("variantName")
    val variantName: String? = null
)