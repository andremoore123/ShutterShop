package com.id.data.cart

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.id.domain.cart.CartModel

/**
 * Created by: andre.
 * Date: 08/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@Entity(tableName = "cart")
data class CartEntity(
    @PrimaryKey(autoGenerate = true)
    val cartId: Int? = null,
    val itemId: String,
    val itemName: String,
    val itemVariantName: String,
    val itemCount: Int = 1,
    val itemPrice: Int
)

fun CartEntity.toModel() = CartModel(
    cartId = cartId,
    itemId = itemId,
    itemName = itemName,
    itemCount = itemCount,
    itemVariantName = itemVariantName,
    itemPrice = itemPrice
)

fun CartModel.toEntity() = CartEntity(
    cartId = cartId,
    itemId = itemId,
    itemName = itemName,
    itemCount = itemCount,
    itemVariantName = itemVariantName,
    itemPrice = itemPrice
)
