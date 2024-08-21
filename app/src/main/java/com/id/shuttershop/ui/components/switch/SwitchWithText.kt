package com.id.shuttershop.ui.components.switch;

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.id.shuttershop.ui.theme.AppTypography
import com.id.shuttershop.ui.theme.ShutterShopTheme

/**
 * Created by: andreputras.
 * Date: 01/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@Composable
fun SwitchWithText(
    modifier: Modifier = Modifier,
    firstText: String = "",
    secondText: String = "",
    enabled: Boolean = true,
    checked: Boolean = false,
    onCheckChange: (Boolean) -> Unit = {},
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = firstText, style = AppTypography.titleMedium)
        Switch(checked = checked, onCheckedChange = onCheckChange, enabled = enabled)
        Text(text = secondText, style = AppTypography.titleMedium)
    }
}

@Composable
@Preview
internal fun ShowSwitchWithTextPreview() {
    ShutterShopTheme {
        SwitchWithText()
    }
}
