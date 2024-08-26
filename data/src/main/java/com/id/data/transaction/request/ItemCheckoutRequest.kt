package com.id.data.transaction.request

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import com.id.domain.cart.CartModel

/**
 * Created by: andre.
 * Date: 14/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@Keep
data class ItemCheckoutRequest(
    @SerializedName("productId")
    val productId: String,
    @SerializedName("variantName")
    val variantName: String,
    @SerializedName("quantity")
    val quantity: Int,
)

fun CartModel.toItemCheckoutRequest() = ItemCheckoutRequest(
    productId = itemId,
    variantName = itemVariantName,
    quantity = itemCount
)