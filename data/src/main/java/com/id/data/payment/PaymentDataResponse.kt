package com.id.data.payment

import com.id.domain.payment.PaymentModel
import com.id.domain.payment.PaymentType

/**
 * Created by: andre.
 * Date: 14/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
data class PaymentDataResponse(
    val title: String? = null,
    val item: List<PaymentItemResponse> ? = null
)

