package com.id.shuttershop.ui.components.button

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.id.shuttershop.ui.theme.ShutterShopTheme

/**
 * Created by: andre.
 * Date: 08/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@Composable
fun IconTextButton(
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.colorScheme.primary,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit = {}
) {
    Button(
        modifier = modifier,
        onClick = onClick, colors = ButtonDefaults.buttonColors().copy(
            containerColor = containerColor
        )
    ) {
        content()
    }
}

@Composable
@Preview
internal fun IconTextButtonPreview() {
    ShutterShopTheme {
        IconTextButton()
    }
}