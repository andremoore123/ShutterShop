package com.id.shuttershop.ui.screen.launchpad;

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.id.shuttershop.ui.components.topbar.TitleTopBar
import com.id.shuttershop.ui.navigation.launchpad.LaunchpadMenus
import com.id.shuttershop.ui.navigation.launchpad.LaunchpadRoute
import com.id.shuttershop.ui.screen.home.HomeScreen
import com.id.shuttershop.ui.screen.profile.ProfileScreen
import com.id.shuttershop.ui.screen.transaction.TransactionScreen
import com.id.shuttershop.ui.screen.wishlist.WishlistScreen
import com.id.shuttershop.ui.theme.ShutterShopTheme

/**
 * Created by: andreputras.
 * Date: 05/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@Composable
fun LaunchpadScreen(
    modifier: Modifier = Modifier,
) {
    val launchdPadNavController = rememberNavController()
    val navBackStackEntry by launchdPadNavController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = modifier,
        topBar = {
            if (currentRoute != LaunchpadMenus.HOME.navigation.route) {
                TitleTopBar(
                    title = LaunchpadMenus.entries.find { it.navigation.route == currentRoute }?.title
                        ?: ""
                )
            }
        },
        bottomBar = {
            BottomAppBar {
                LaunchpadMenus.entries.toList().forEach { menu ->
                    NavigationBarItem(
                        label = {
                            Text(text = menu.title)
                        },
                        selected = currentRoute == menu.navigation.route,
                        onClick = {
                            launchdPadNavController.navigate(menu.navigation.route) {
                                popUpTo(launchdPadNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        },
                        icon = { Icon(imageVector = menu.icon, contentDescription = null) }
                    )
                }
            }
        }) { innerPadding ->
        NavHost(
            navController = launchdPadNavController,
            startDestination = LaunchpadRoute.HomeRoute.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(LaunchpadRoute.HomeRoute.route) {
                HomeScreen()
            }
            composable(LaunchpadRoute.TransactionRoute.route) {
                TransactionScreen()
            }
            composable(LaunchpadRoute.WishlistRoute.route) {
                WishlistScreen()
            }
            composable(LaunchpadRoute.ProfileRoute.route) {
                ProfileScreen()
            }
        }
    }
}

@Composable
@Preview
internal fun ShowLaunchpadScreenPreview() {
    ShutterShopTheme {
        LaunchpadScreen()
    }
}
