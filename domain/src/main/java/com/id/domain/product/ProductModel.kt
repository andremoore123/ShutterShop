package com.id.domain.product

/**
 * Created by: andreputras.
 * Date: 05/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
data class ProductModel(
    val id: Int,
    val itemName: String,
    val itemSold: String,
    val itemPrice: String,
    val itemRating: String,
    val itemSeller: String,
) {
    companion object {
        val dummyData = ProductModel(
            id = 2990,
            itemName = "Angelina Hesdfjsdifjdsfisdfndrix",
            itemSold = "5",
            itemPrice = "230 000",
            itemRating = "3",
            itemSeller = "noluisse"
        )
    }
}
