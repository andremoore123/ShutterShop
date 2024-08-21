package com.id.shuttershop.ui.navigation.main

/**
 * Created by: andreputras.
 * Date: 05/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

const val PRODUCT_ID = "productId"
const val PRODUCT_WITH_BRACKET = "{${PRODUCT_ID}}"


enum class MainNavRoute(val route: String) {
    LAUNCHPAD_SCREEN("launchpadScreen"),
    SEARCH_SCREEN("searchScreen"),
    NOTIFICATION_SCREEN("notificationScreen"),
    PRODUCT_DETAIL_SCREEN("productDetailScreen?$PRODUCT_ID=$PRODUCT_WITH_BRACKET"),

}
