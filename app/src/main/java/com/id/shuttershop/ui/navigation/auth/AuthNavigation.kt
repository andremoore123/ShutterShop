package com.id.shuttershop.ui.navigation.auth

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.id.shuttershop.ui.navigation.MainRoute
import com.id.shuttershop.ui.screen.auth.login.LoginScreen

/**
 * Created by: andreputras.
 * Date: 31/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

fun NavGraphBuilder.authNavigation(navController: NavController) {
    navigation(AuthRoute.LOGIN_SCREEN.route, route = MainRoute.AuthNavigation.route) {
        composable(AuthRoute.LOGIN_SCREEN.route) {
            LoginScreen(modifier = Modifier.padding(16.dp), navigateToRegister = {
                navController.navigate(AuthRoute.REGISTER_SCREEN.route) {
                    popUpTo(AuthRoute.LOGIN_SCREEN.route) { inclusive = true }
                }
            })
        }
        composable(AuthRoute.REGISTER_SCREEN.route) {

        }
    }
}