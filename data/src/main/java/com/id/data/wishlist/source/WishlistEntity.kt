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
    val wishlistId: Int? = null,
    val productId: String,
    val itemName: String,
    val itemSold: String,
    val itemPrice: Int,
    val itemVariantName: String,
    val itemRating: String,
    val itemSeller: String,
)

fun WishlistEntity.mapToModel(): WishlistModel = WishlistModel(
    wishlistId = wishlistId,
    itemId = productId,
    itemName = itemName,
    itemSold = itemSold,
    itemPrice = itemPrice,
    itemRating = itemRating,
    itemSeller = itemSeller,
    itemVariantName = itemVariantName
)

fun WishlistModel.mapToEntity(): WishlistEntity = WishlistEntity(
    wishlistId = wishlistId,
    productId = itemId,
    itemName = itemName,
    itemSold = itemSold,
    itemPrice = itemPrice,
    itemRating = itemRating,
    itemSeller = itemSeller,
    itemVariantName = itemVariantName
)