package com.id.shuttershop.ui.navigation.launchpad

/**
 * Created by: andreputras.
 * Date: 05/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
sealed class LaunchpadRoute(val route: String) {
    data object HomeRoute : LaunchpadRoute("homeRoute")
    data object TransactionRoute : LaunchpadRoute("transactionRoute")
    data object WishlistRoute : LaunchpadRoute("wishlistRoute")
    data object ProfileRoute : LaunchpadRoute("profileRoute")
}