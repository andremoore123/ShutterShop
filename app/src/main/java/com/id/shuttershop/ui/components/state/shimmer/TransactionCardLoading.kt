package com.id.shuttershop.ui.components.state.shimmer

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
fun TransactionCardLoading(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .border(2.dp, DividerDefaults.color, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
    ) {
        Row(
            modifier = Modifier.padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Box(modifier = Modifier.size(20.dp).shimmerEffect())
            Box(
                modifier = Modifier
                    .size(100.dp, 20.dp)
                    .shimmerEffect()
            )
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .size(150.dp, 30.dp)
                    .clip(CircleShape)
                    .shimmerEffect()
            )
        }
        HorizontalDivider()
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Row {
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .shimmerEffect()
                )
                Column(
                    modifier = Modifier.padding(start = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(150.dp, 30.dp)
                            .shimmerEffect()
                    )
                    Box(
                        modifier = Modifier
                            .size(40.dp, 20.dp)
                            .shimmerEffect()
                    )
                }
            }
            Row(
                modifier = Modifier.padding(top = 10.dp),
                verticalAlignment = Alignment.Bottom,
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp, 20.dp)
                            .shimmerEffect()
                    )
                    Box(
                        modifier = Modifier
                            .size(80.dp, 30.dp)
                            .shimmerEffect()
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Box(modifier = Modifier
                    .size(100.dp, 30.dp)
                    .shimmerEffect())
            }
        }
    }
}

@Composable
@Preview
internal fun TransactionCardLoadingPreview() {
    ShutterShopTheme {
        TransactionCardLoading()
    }
}