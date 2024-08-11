package com.id.domain.cart

import android.os.Parcelable
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
    val itemId: Int,
    val itemName: String,
    val itemVariantName: String = "",
    val itemStock: Int = 0,
    val itemCount: Int = 1,
    val itemPrice: Int = 0,
) : Parcelable
