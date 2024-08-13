package com.id.shuttershop.ui.navigation.main

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.id.domain.cart.CartModel
import com.id.shuttershop.ui.navigation.MainRoute
import com.id.shuttershop.ui.screen.cart.CartScreen
import com.id.shuttershop.ui.screen.checkout.CheckoutScreen
import com.id.shuttershop.ui.screen.launchpad.LaunchpadScreen
import com.id.shuttershop.ui.screen.notification.NotificationScreen
import com.id.shuttershop.ui.screen.product_detail.DetailProductScreen
import com.id.shuttershop.ui.screen.search.SearchScreen

/**
 * Created by: andreputras.
 * Date: 05/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

fun NavGraphBuilder.mainNavigation(navController: NavController) {
    navigation(
        startDestination = MainNavRoute.LAUNCHPAD_SCREEN.route,
        route = MainRoute.MainNavigation.route
    ) {
        composable(MainNavRoute.LAUNCHPAD_SCREEN.route) {
            LaunchpadScreen(mainNavController = navController)
        }
        composable(MainNavRoute.SEARCH_SCREEN.route) {
            SearchScreen(
                navigateBack = { navController.popBackStack() },
                navigateToDetail = {
                    navigateToCartDetail(navController, it)
                }
            )
        }
        composable(MainNavRoute.NOTIFICATION_SCREEN.route) {
            NotificationScreen(onBackClick = {navController.popBackStack()})
        }
        composable(MainNavRoute.PRODUCT_DETAIL_SCREEN.route) { navBackStackEntry ->
            val productId = navBackStackEntry.arguments?.getString(USER_ID)
            productId?.let {
                DetailProductScreen(
                    idProduct = it,
                    onBackClick = { navController.popBackStack() },
                    onCheckoutClick = { cart ->
                        navController.navigate(route = MainNavRoute.CHECKOUT_SCREEN.route)
                        navBackStackEntry.savedStateHandle[CHECKOUT_DATA] = ArrayList(listOf(cart))
                    }
                )
            }
        }
        composable(MainNavRoute.CART_SCREEN.route) { navBackStackEntry ->
            CartScreen(
                onBackClick = { navController.popBackStack() },
                navigateToCheckout = { carts: List<CartModel> ->
                    navController.navigate(route = MainNavRoute.CHECKOUT_SCREEN.route)
                    navBackStackEntry.savedStateHandle[CHECKOUT_DATA] = ArrayList(carts)
                },
                navigateToProductDetail = {
                    navigateToCartDetail(navController, it)
                }
            )
        }
        composable(MainNavRoute.CHECKOUT_SCREEN.route) {
            val checkoutItems =
                navController.previousBackStackEntry?.savedStateHandle?.get<ArrayList<CartModel>>(
                    CHECKOUT_DATA
                ) ?: listOf()
            CheckoutScreen(
                onBackClick = { navController.popBackStack() },
                checkoutItems = checkoutItems,
                navigateToPaymentStatus = {

                }
            )
        }
    }
}

fun navigateToCartDetail(navController: NavController, productId: String) {
    navController.navigate(
        MainNavRoute.PRODUCT_DETAIL_SCREEN.route.replace(
            USER_WITH_BRACKET, productId
        )
    )
}
