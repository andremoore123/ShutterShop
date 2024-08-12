package com.id.shuttershop.ui.navigation.main

/**
 * Created by: andreputras.
 * Date: 05/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

const val USER_ID = "userID"
const val USER_WITH_BRACKET = "{${USER_ID}}"

const val CHECKOUT_DATA = "checkoutData"

enum class MainNavRoute(val route: String) {
    LAUNCHPAD_SCREEN("launchpadScreen"),
    SEARCH_SCREEN("searchScreen"),
    NOTIFICATION_SCREEN("notificationScreen"),
    PRODUCT_DETAIL_SCREEN("productDetailScreen/${USER_WITH_BRACKET}"),
    CART_SCREEN("cartScreen"),
    CHECKOUT_SCREEN("checkoutScreen/cartList"),
}
