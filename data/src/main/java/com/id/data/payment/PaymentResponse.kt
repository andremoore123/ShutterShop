package com.id.data.payment

import com.google.gson.annotations.SerializedName
import com.id.domain.payment.PaymentModel

/**
 * Created by: andre.
 * Date: 14/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
data class PaymentResponse(
    @SerializedName("data")
    val data: List<PaymentDataResponse>? = null
)


