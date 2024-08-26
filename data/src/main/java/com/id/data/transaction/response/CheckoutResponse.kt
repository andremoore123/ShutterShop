package com.id.data.transaction.response

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.id.domain.transaction.CheckoutModel

@Keep
data class CheckoutResponse(

    @field:SerializedName("date")
    val date: String? = null,

    @field:SerializedName("total")
    val total: Int? = null,

    @field:SerializedName("invoiceId")
    val invoiceId: String? = null,

    @field:SerializedName("payment")
    val payment: String? = null,

    @field:SerializedName("time")
    val time: String? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)

fun CheckoutResponse.toModel() = CheckoutModel(
    invoiceId = invoiceId ?: "",
    date = date ?: "",
    time = time ?: "",
    paymentName = payment ?: "",
    total = total ?: 0
)