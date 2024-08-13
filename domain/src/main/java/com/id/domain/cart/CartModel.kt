package com.id.domain.cart

import android.os.Parcelable
import com.id.domain.ext.formatToRupiah
import kotlinx.parcelize.Parcelize

/**
 * Created by: andre.
 * Date: 08/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@Parcelize
data class CartModel(
    val cartId: Int? = null,
    val itemId: String,
    val itemName: String,
    val itemVariantName: String = "",
    val itemStock: Int = 5,
    val itemCount: Int = 1,
    val itemPrice: Int = 250000,
) : Parcelable {
    fun getFormattedCurrency(): String {
        return itemPrice.formatToRupiah()
    }
}