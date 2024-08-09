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
    @PrimaryKey
    val itemId: Int,
    val itemName: String,
    val itemCount: Int = 1
)

fun CartEntity.toModel() = CartModel(
    itemId = itemId, itemName = itemName, itemCount = itemCount
)

fun CartModel.toEntity() = CartEntity(
    itemId = itemId, itemName = itemName, itemCount = itemCount
)