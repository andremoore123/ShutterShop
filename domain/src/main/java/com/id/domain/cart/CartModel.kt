package com.id.domain.cart

/**
 * Created by: andre.
 * Date: 08/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
data class CartModel(
    val itemId: Int,
    val itemName: String,
    val itemDesc: String = "",
    val itemStock: Int = 0,
    val itemCount: Int = 1,
    val itemPrice: Int = 0,
)
