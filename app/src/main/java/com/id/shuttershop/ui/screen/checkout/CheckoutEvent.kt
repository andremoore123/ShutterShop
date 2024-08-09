package com.id.shuttershop.ui.screen.checkout

import com.id.domain.cart.CartModel
import com.id.domain.payment.PaymentModel

/**
 * Created by: andre.
 * Date: 10/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
data class CheckoutEvent(
    val onPaymentMethodClick: (PaymentModel) -> Unit,
    val calculatePrice: (List<CartModel>) -> String,
    val changeBottomSheetValue: (Boolean) -> Unit,
    val onPaymentClick: (List<CartModel>, PaymentModel) -> Unit,
)
