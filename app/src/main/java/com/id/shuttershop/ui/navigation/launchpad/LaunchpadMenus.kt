package com.id.shuttershop.ui.navigation.launchpad

import android.content.res.Resources
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.id.shuttershop.R

/**
 * Created by: andreputras.
 * Date: 05/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
enum class LaunchpadMenus(
    val title: String,
    val navigation: LaunchpadRoute,
    val icon: ImageVector,
) {
    HOME("Home", LaunchpadRoute.HomeRoute, Icons.Default.Home),
    TRANSACTION(
        "Transaction",
        LaunchpadRoute.TransactionRoute,
        Icons.Default.Star
    ),
    WISHLIST(
        "Wishlist",
        LaunchpadRoute.WishlistRoute,
        Icons.Default.Favorite,
    ),
    PROFILE(
        "Profile",
        LaunchpadRoute.ProfileRoute,
        Icons.Default.AccountCircle
    )
}
