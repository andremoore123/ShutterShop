package com.id.shuttershop.ui.navigation

/**
 * Created by: andreputras.
 * Date: 30/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
sealed class MainRoute(val route: String) {
    data object SplashNavigation : MainRoute("splashNavRoute")
    data object AuthNavigation : MainRoute("authNavRoute")
    data object MainNavigation : MainRoute("mainNavRoute")
    data object TransactionNavigation : MainRoute("transactionNavRoute")
}
