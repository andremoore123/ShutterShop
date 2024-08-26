package com.id.data.payment

import androidx.annotation.Keep

/**
 * Created by: andre.
 * Date: 14/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@Keep
data class PaymentDataResponse(
    val title: String? = null,
    val item: List<PaymentItemResponse> ? = null
)

