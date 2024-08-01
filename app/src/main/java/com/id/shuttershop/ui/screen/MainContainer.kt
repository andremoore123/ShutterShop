package com.id.shuttershop.ui.screen;

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.id.shuttershop.ui.navigation.MainRoute
import com.id.shuttershop.ui.navigation.auth.authNavigation
import com.id.shuttershop.ui.navigation.splash.SplashRoute
import com.id.shuttershop.ui.navigation.splash.splashNavigation
import com.id.shuttershop.ui.theme.ShutterShopTheme

/**
 * Created by: andreputras.
 * Date: 30/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@Composable
fun MainContainer(
    modifier: Modifier = Modifier,
    viewModel: MainContainerViewModel = hiltViewModel(),
) {
    val mainNavController = rememberNavController()
    val isOnboardShowed by viewModel.isOnboardShowed.collectAsState()

    LaunchedEffect(key1 = isOnboardShowed) {
        viewModel.fetchIsOnboardShowed()
        if (isOnboardShowed) {
            mainNavController.navigate(MainRoute.AuthNavigation.route) {
                popUpTo(MainRoute.SplashNavigation.route) {inclusive = true}
            }
        }
    }

    NavHost(
        navController = mainNavController,
        startDestination = MainRoute.SplashNavigation.route,
        modifier = modifier
    ) {
        splashNavigation()
        authNavigation(mainNavController)
    }
}

@Composable
@Preview
internal fun ShowMainContainerPreview() {
    ShutterShopTheme {
        MainContainer()
    }
}
