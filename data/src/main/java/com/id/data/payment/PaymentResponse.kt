package com.id.data.payment

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * Created by: andre.
 * Date: 14/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@Keep
data class PaymentResponse(
    @SerializedName("data")
    val data: List<PaymentDataResponse>? = null
)


