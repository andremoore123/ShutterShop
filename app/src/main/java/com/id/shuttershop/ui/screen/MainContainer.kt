package com.id.shuttershop.ui.screen;

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.id.shuttershop.ui.navigation.MainRoute
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
) {
    val mainNavController = rememberNavController()

    NavHost(
        navController = mainNavController,
        startDestination = MainRoute.SplashNavigation.route,
        modifier = modifier
    ) {
        splashNavigation(mainNavController)
    }
}

@Composable
@Preview
fun ShowMainContainerPreview() {
    ShutterShopTheme {
        MainContainer()
    }
}
