package com.id.shuttershop.ui.navigation.transaction

import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.id.domain.cart.CartModel
import com.id.domain.transaction.CheckoutModel
import com.id.shuttershop.ui.navigation.MainRoute
import com.id.shuttershop.ui.navigation.main.MainNavRoute
import com.id.shuttershop.ui.navigation.main.PRODUCT_WITH_BRACKET
import com.id.shuttershop.ui.screen.cart.CartScreen
import com.id.shuttershop.ui.screen.checkout.CheckoutScreen
import com.id.shuttershop.ui.screen.rating.TransactionRatingScreen
import com.id.shuttershop.utils.navigateAndPopUpAll

/**
 * Created by: andre.
 * Date: 15/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

fun NavGraphBuilder.transactionNavigation(navController: NavController) {
    fun navigateToCartDetail(navController: NavController, productId: String) {
        navController.navigate(
            MainNavRoute.PRODUCT_DETAIL_SCREEN.route.replace(
                PRODUCT_WITH_BRACKET, productId
            )
        )
    }
    navigation(
        route = MainRoute.TransactionNavigation.route,
        startDestination = TransactionRoute.CART_SCREEN.route
    ) {
        composable(TransactionRoute.CART_SCREEN.route) { navBackStackEntry ->
            CartScreen(
                onBackClick = { navController.popBackStack() },
                navigateToCheckout = { carts: List<CartModel> ->
                    navController.navigate(route = TransactionRoute.CHECKOUT_SCREEN.route)
                    navBackStackEntry.savedStateHandle[CHECKOUT_DATA] = ArrayList(carts)
                },
                navigateToProductDetail = {
                    navigateToCartDetail(navController, it)
                }
            )
        }
        composable(TransactionRoute.CHECKOUT_SCREEN.route) { navBackStackEntry ->
            val checkoutItems =
                navController.previousBackStackEntry?.savedStateHandle?.get<ArrayList<CartModel>>(
                    CHECKOUT_DATA
                ) ?: listOf()
            CheckoutScreen(
                onBackClick = { navController.popBackStack() },
                checkoutItems = checkoutItems,
                navigateToPaymentStatus = {
                    navController.navigate(route = TransactionRoute.TRANSACTION_RATING_SCREEN.route)
                    navBackStackEntry.savedStateHandle[CHECKOUT_DATA_RESPONSE] = it
                }
            )
        }

        composable(TransactionRoute.TRANSACTION_RATING_SCREEN.route) {
            val checkoutModel =
                navController.previousBackStackEntry?.savedStateHandle?.get<CheckoutModel>(
                    CHECKOUT_DATA_RESPONSE
                )
            Log.d("TransactionRatingScreen", checkoutModel.toString())
            checkoutModel?.let {
                TransactionRatingScreen(checkoutModel = it, navigateToHome = {
                    navController.navigateAndPopUpAll(MainNavRoute.LAUNCHPAD_SCREEN.route)
                })
            }
        }
    }
}