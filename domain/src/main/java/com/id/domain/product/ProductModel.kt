package com.id.domain.product

/**
 * Created by: andreputras.
 * Date: 05/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
data class ProductModel(
    val id: String,
    val itemName: String,
    val itemSold: Int,
    val itemPrice: Int,
    val itemRating: String,
    val itemSeller: String,
    val imageUrl: String,
) {
    companion object {
        val dummyData = ProductModel(
            id = "Sdfsdfdfs",
            itemName = "Angelina Hesdfjsdifjdsfisdfndrix",
            itemSold = 3,
            itemPrice = 3443,
            itemRating = "3",
            itemSeller = "noluisse",
            imageUrl = "dfsfsdf"
        )
    }
}
