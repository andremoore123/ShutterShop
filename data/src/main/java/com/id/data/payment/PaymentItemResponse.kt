package com.id.data.payment

import com.id.domain.payment.PaymentModel
import com.id.domain.payment.PaymentType

data class PaymentItemResponse(
    val label: String? = null,
    val image: String? = null,
    val status: Boolean? = null
)

fun PaymentItemResponse.toModel(paymentType: String) = PaymentModel(
    idPayment = 0,
    paymentName = label ?: "",
    paymentImageUrl = image ?: "",
    paymentType = PaymentType.entries.find { it.value == paymentType } ?: PaymentType.OTHERS,
    paymentStatus = status ?: false
)
