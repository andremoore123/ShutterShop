package com.id.domain.cart

import android.os.Parcelable
import com.id.domain.utils.formatToRupiah
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
    val itemPrice: Int,
    val itemStock: Int = 0,
    val itemCount: Int = 1,
    val itemVariantName: String = "",
    val imageUrl: String = ""

) : Parcelable {
    fun getFormattedCurrency(): String {
        return itemPrice.formatToRupiah()
    }
}