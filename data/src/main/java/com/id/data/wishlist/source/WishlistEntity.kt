package com.id.data.wishlist.source

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.id.domain.wishlist.WishlistModel

/**
 * Created by: andreputras.
 * Date: 02/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@Entity("wishlist")
data class WishlistEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val itemName: String,
    val itemSold: String,
    val itemPrice: String,
    val itemRating: String,
    val itemSeller: String,
)

fun WishlistEntity.mapToModel(): WishlistModel = WishlistModel(
    id = id ?: 0,
    itemName = itemName,
    itemSold = itemSold,
    itemPrice = itemPrice,
    itemRating = itemRating,
    itemSeller = itemSeller
)

fun WishlistModel.mapToEntity(): WishlistEntity = WishlistEntity(
    id = id,
    itemName = itemName,
    itemSold = itemSold,
    itemPrice = itemPrice,
    itemRating = itemRating,
    itemSeller = itemSeller
)