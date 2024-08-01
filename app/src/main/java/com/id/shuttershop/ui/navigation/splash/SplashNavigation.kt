package com.id.shuttershop.ui.navigation.splash

import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.id.shuttershop.ui.navigation.MainRoute
import com.id.shuttershop.ui.screen.onboarding.OnboardingScreen

/**
 * Created by: andreputras.
 * Date: 30/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
fun NavGraphBuilder.splashNavigation(
    navController: NavController,
) {
    navigation(SplashRoute.ON_BOARDING.route, route = MainRoute.SplashNavigation.route) {
        composable(SplashRoute.ON_BOARDING.route) {
            OnboardingScreen(modifier = Modifier.padding(24.dp)) {
                navController.navigate(MainRoute.AuthNavigation.route) {
                    popUpTo(SplashRoute.ON_BOARDING.route) { inclusive = true }
                }
            }
        }
    }
}
