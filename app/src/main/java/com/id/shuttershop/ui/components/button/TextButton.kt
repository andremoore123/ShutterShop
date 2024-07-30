package com.id.shuttershop.ui.components.button

import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.id.shuttershop.ui.theme.AppTypography
import com.id.shuttershop.ui.theme.ShutterShopTheme

/**
 * Created by: andreputras.
 * Date: 30/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@Composable
fun PrimaryTextButton(
    modifier: Modifier = Modifier,
    text: String = "",
    onClick: () -> Unit = {},
) {
    TextButton(onClick = onClick, modifier = modifier) {
        Text(text = text, style = AppTypography.titleMedium)
    }
}

@Composable
@Preview
fun PreviewTextButton() {
    ShutterShopTheme {
        PrimaryTextButton(text = "String")
    }
}