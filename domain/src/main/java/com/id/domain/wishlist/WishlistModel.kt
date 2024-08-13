package com.id.domain.wishlist

/**
 * Created by: andreputras.
 * Date: 02/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

/**
 * Item Desc is Chosen Item
 */
data class WishlistModel(
    val wishlistId: Int? = null,
    val itemId: String,
    val itemName: String,
    val itemSold: String,
    val itemVariantName: String = "",
    val itemPrice: Int,
    val itemRating: String,
    val itemSeller: String,
    val itemImageUrl: String = "",
) {
    companion object {
        val dummyData = WishlistModel(
            itemId = "2990",
            itemName = "Angelina Hesdfjsdifjdsfisdfndrix",
            itemSold = "5",
            itemPrice = 0,
            itemRating = "3",
            itemSeller = "noluisse",
            itemImageUrl = "",
        )
    }
}
