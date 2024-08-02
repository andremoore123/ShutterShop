package com.id.domain.wishlist

/**
 * Created by: andreputras.
 * Date: 02/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
data class WishlistModel(
    val id: Int,
    val itemName: String,
    val itemSold: String,
    val itemPrice: String,
    val itemRating: String,
    val itemSeller: String,
) {
    companion object {
        val dummyData = WishlistModel(
            id = 2990,
            itemName = "Angelina Hendrix",
            itemSold = "aliquid",
            itemPrice = "pro",
            itemRating = "vocibus",
            itemSeller = "noluisse"
        )
    }
}
