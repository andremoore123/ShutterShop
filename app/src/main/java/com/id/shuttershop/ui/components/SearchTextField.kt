package com.id.shuttershop.ui.components;

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.id.shuttershop.ui.theme.AppTypography
import com.id.shuttershop.ui.theme.ShutterShopTheme

/**
 * Created by: andreputras.
 * Date: 05/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@Composable
fun SearchTextField(
    modifier: Modifier = Modifier,
    enabled: Boolean = false,
    hint: String = "",
    value: String = "",
    onValueChange: (String) -> Unit = {},
    onClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
    ) {
        AnimatedVisibility(visible = value.isNotEmpty()) {
            Text(
                text = hint,
                style = AppTypography.titleMedium,
                modifier = Modifier.padding(bottom = 5.dp)
            )

        }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(1f)
                .clip(OutlinedTextFieldDefaults.shape)
                .clickable(enabled.not(), onClick = onClick),
            value = value,
            onValueChange = onValueChange,
            enabled = enabled,
            shape = CircleShape,
            label = {
                AnimatedVisibility(visible = value.isEmpty()) {
                    Text(text = hint)
                }
            },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
            }
        )
    }

}

@Composable
@Preview(showBackground = true, showSystemUi = true)
internal fun ShowSearchTextFieldPreview() {
    ShutterShopTheme {
        SearchTextField(hint = "Search Camera", value = "s")
    }
}
