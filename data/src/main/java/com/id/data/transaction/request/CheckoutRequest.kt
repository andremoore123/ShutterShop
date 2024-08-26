package com.id.data.transaction.request

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

/**
 * Created by: andre.
 * Date: 14/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@Keep
data class CheckoutRequest(
    @SerializedName("payment")
    val paymentName: String,
    @SerializedName("items")
    val data: List<ItemCheckoutRequest>
)
