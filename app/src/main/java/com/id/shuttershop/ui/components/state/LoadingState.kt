package com.id.shuttershop.ui.components.state

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.id.shuttershop.ui.theme.ShutterShopTheme

/**
 * Created by: andre.
 * Date: 12/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@Composable
fun LoadingState(
    modifier: Modifier = Modifier,
    parentPadding: Dp = 30.dp
) {
    Box(
        modifier = modifier
            .layout { measurable, constraints ->
                val placeable = measurable.measure(
                    constraints.copy(
                        maxWidth = constraints.maxWidth + 2 * parentPadding.roundToPx(),
                        maxHeight = constraints.maxHeight + 2 * parentPadding.roundToPx()
                    ),
                )
                layout(width = constraints.maxWidth, height = constraints.maxHeight) {
                    placeable.place(-parentPadding.roundToPx(), -parentPadding.roundToPx())
                }
            }
            .fillMaxSize()
            .zIndex(1f)
            .clickable(enabled = false, onClick = {})
            .background(MaterialTheme.colorScheme.inverseSurface.copy(alpha = 0.3f)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
internal fun LoadingStatePreview() {
    ShutterShopTheme {
        LoadingState()
    }
}
