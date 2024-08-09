package com.id.shuttershop.ui.components.button

import androidx.compose.material3.Button
import androidx.compose.material3.Text
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
fun PrimaryButton(
    modifier: Modifier = Modifier,
    text: String = "",
    enabled: Boolean = true,
    onClick: () -> Unit = {},
) {
    Button(onClick = onClick, modifier = modifier, enabled = enabled) {
        Text(text = text, style = AppTypography.titleMedium)
    }
}

@Composable
@Preview
internal fun ShowPrimaryButtonPreview() {
    ShutterShopTheme {
        PrimaryButton(text = "Test Button")
    }
}
