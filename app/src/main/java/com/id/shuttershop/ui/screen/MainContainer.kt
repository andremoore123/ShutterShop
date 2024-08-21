package com.id.shuttershop.ui.screen;

import android.net.Uri
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
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
import com.id.shuttershop.ui.navigation.main.mainNavigation
import com.id.shuttershop.ui.navigation.splash.SplashRoute
import com.id.shuttershop.ui.navigation.splash.splashNavigation
import com.id.shuttershop.ui.navigation.transaction.transactionNavigation
import com.id.shuttershop.ui.theme.ShutterShopTheme
import com.id.shuttershop.utils.navigateAndPopUpAll
import kotlinx.coroutines.delay

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
    uriData: Uri?
) {
    val mainNavController = rememberNavController()
    val isOnboardShowed by viewModel.isOnboardShowed.collectAsState()
    val isUserLogin by viewModel.isUserLogin.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchIsOnboardShowed()
    }

    LaunchedEffect(key1 = isUserLogin, key2 = uriData) {
        delay(1_500)
        if (isUserLogin == true) {
            if (uriData == null) {
                mainNavController.navigateAndPopUpAll(MainRoute.MainNavigation.route)
            } else {
                val onBackPressedDispatcher = OnBackPressedDispatcher().apply {
                    addCallback(onBackPressedCallback = object :
                        OnBackPressedCallback(enabled = true) {
                        override fun handleOnBackPressed() {
                            mainNavController.navigateAndPopUpAll(MainRoute.MainNavigation.route)
                        }
                    })
                }

                mainNavController.setOnBackPressedDispatcher(onBackPressedDispatcher)
            }
        } else {
            if (isOnboardShowed) {
                mainNavController.navigateAndPopUpAll(MainRoute.AuthNavigation.route)
            } else {
                mainNavController.navigateAndPopUpAll(SplashRoute.ON_BOARDING.route)
            }
        }
    }

    NavHost(
        navController = mainNavController,
        startDestination = MainRoute.SplashNavigation.route,
        modifier = modifier
    ) {
        splashNavigation(mainNavController)
        authNavigation(mainNavController)
        mainNavigation(mainNavController)
        transactionNavigation(mainNavController)
    }
}

@Composable
@Preview
internal fun ShowMainContainerPreview() {
    ShutterShopTheme {
        MainContainer(uriData = null)
    }
}
