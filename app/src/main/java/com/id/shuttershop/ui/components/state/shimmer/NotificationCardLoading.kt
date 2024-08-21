package com.id.shuttershop.ui.components.state.shimmer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.id.shuttershop.ui.theme.ShutterShopTheme
import com.id.shuttershop.utils.shimmerEffect

/**
 * Created by: andre.
 * Date: 21/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@Composable
fun NotificationCardLoading(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .shimmerEffect()
            )
            Column(
                modifier = Modifier.padding(start = 10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(30.dp, 15.dp)
                        .shimmerEffect()
                )
                Box(
                    modifier = Modifier
                        .size(150.dp, 30.dp)
                        .shimmerEffect()
                )
            }
        }
        Column(
            modifier = Modifier.padding(top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .shimmerEffect())
            Box(modifier = Modifier
                .fillMaxWidth(0f)
                .height(20.dp)
                .shimmerEffect())
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
internal fun NotificationCardLoadingPreview() {
    ShutterShopTheme {
        NotificationCardLoading()
    }
}