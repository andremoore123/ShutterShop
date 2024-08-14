package com.id.shuttershop.ui.screen.wishlist

import com.id.domain.wishlist.WishlistModel

/**
 * Created by: andre.
 * Date: 14/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
data class WishlistEvent(
 val onLayoutChange: (String) -> Unit,
 val onDeleteClick: (WishlistModel) -> Unit = {},
 val addToCart: (WishlistModel) -> Unit = {},
 val navigateToDetail: (productId: String) -> Unit = {},
)
