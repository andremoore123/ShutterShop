package com.id.shuttershop.ui.components.topbar;

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.id.shuttershop.ui.theme.AppTypography
import com.id.shuttershop.ui.theme.ShutterShopTheme

/**
 * Created by: andreputras.
 * Date: 01/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleTopBar(
    modifier: Modifier = Modifier,
    title: String = "",
    showNavigation: Boolean = false,
    onBackClickListener: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        modifier = modifier,
        title = { Text(text = title, style = AppTypography.titleLarge) },
        navigationIcon = {
            if (showNavigation) {
                IconButton(onClick = onBackClickListener) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = null
                    )
                }
            }
        }
    )
}

@Composable
@Preview
internal fun ShowTextTopBarPreview() {
    ShutterShopTheme {
        TitleTopBar(title = "Test")
    }
}
