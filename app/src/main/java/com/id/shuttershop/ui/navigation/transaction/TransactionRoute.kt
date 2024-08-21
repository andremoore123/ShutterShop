package com.id.shuttershop.ui.navigation.transaction

/**
 * Created by: andre.
 * Date: 15/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
const val CHECKOUT_DATA = "checkoutData"
const val CHECKOUT_DATA_RESPONSE = "checkoutDataResponse"

enum class TransactionRoute(val route: String) {
    CART_SCREEN("cartScreen"),
    CHECKOUT_SCREEN("checkoutScreen/cartList"),
    TRANSACTION_RATING_SCREEN("transactionRatingScreen")
}