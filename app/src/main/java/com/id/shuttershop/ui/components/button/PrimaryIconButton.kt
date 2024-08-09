package com.id.shuttershop.ui.components.button

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.id.shuttershop.ui.theme.ShutterShopTheme

/**
 * Created by: andreputras.
 * Date: 02/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@Composable
fun PrimaryIconButton(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    iconSize: Dp? = null,
    enabled: Boolean = true,
    onClick: () -> Unit = {},
    color: Color = MaterialTheme.colorScheme.primary,
) {
    val modifierSize = if (iconSize != null) Modifier.size(iconSize) else Modifier
    Box(modifier = modifier
        .clip(CircleShape)
        .clickable(enabled = enabled) { onClick() }
        .border(2.dp, MaterialTheme.colorScheme.secondaryContainer, CircleShape)
        .background(color = MaterialTheme.colorScheme.surface),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            tint = color,
            contentDescription = null,
            modifier = Modifier
                .then(modifierSize)
                .padding(8.dp)
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ShowPrimaryIconButtonPreview() {
    ShutterShopTheme {
        PrimaryIconButton(
            icon = Icons.Default.Delete
        )
    }
}
