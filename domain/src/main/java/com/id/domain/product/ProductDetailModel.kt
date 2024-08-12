package com.id.domain.product

import com.id.domain.ext.formatToRupiah
import com.id.domain.wishlist.WishlistModel

/**
 * Created by: andre.
 * Date: 08/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
data class ProductDetailModel(
    val id: Int,
    val productName: String,
    val productDesc: String,
    val productVariance: List<VarianceModel>,
    val productPrice: Int,
    val productSold: String,
    val productRating: String,
    val totalRating: String,
    val imageUrl: List<String>,
    val productStore: String,
) {
    fun getFormattedCurrency(): String {
        return productPrice.formatToRupiah()
    }
}

fun ProductDetailModel.toWishlist(selectedVariant: VarianceModel): WishlistModel {
    return WishlistModel(
        wishlistId = null,
        itemId = id,
        itemName = productName,
        itemSold = productSold,
        itemPrice = productPrice,
        itemVariantName = selectedVariant.title,
        itemRating = productRating,
        itemSeller = productStore,
        itemImageUrl = imageUrl.firstOrNull() ?: ""
    )
}