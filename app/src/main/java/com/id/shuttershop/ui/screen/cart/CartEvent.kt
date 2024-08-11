package com.id.shuttershop.ui.screen.cart

import com.id.domain.cart.CartModel

/**
 * Created by: andre.
 * Date: 09/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
data class CartEvent(
    val addCartQuantity: (CartModel) -> Unit,
    val reduceCartQuantity: (CartModel) -> Unit,
    val onCheckoutClick: () -> Unit,
    val onSelectCart: (Boolean, CartModel) -> Unit,
    val onSelectAllCart: (Boolean) -> Unit,
    val removeCarts: (List<Int>) -> Unit,
    val isAllChartSelected: () -> Boolean?,
)
