package com.id.shuttershop.ui.screen.cart

import com.id.domain.cart.CartModel

/**
 * Created by: andre.
 * Date: 09/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
data class CartEvent(
    val onAddClick: (CartModel) -> Unit,
    val onMinusClick: (CartModel) -> Unit,
    val onRemoveCartClick: (CartModel) -> Unit,
    val onCheckoutClick: () -> Unit,
    val onSelectCart: (Boolean, CartModel) -> Unit,
    val onSelectAllCart: (Boolean, List<CartModel>) -> Unit,
    val onSelectedCartRemove: (List<CartModel>) -> Unit,
)
