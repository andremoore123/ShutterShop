package com.id.shuttershop.ui.components.state.shimmer

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.id.shuttershop.ui.components.card.HomeCardOrientation
import com.id.shuttershop.ui.theme.ShutterShopTheme
import com.id.shuttershop.utils.shimmerEffect

/**
 * Created by: andre.
 * Date: 20/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@Composable
fun HomeCardLoading(
    modifier: Modifier = Modifier,
    cardOrientation: HomeCardOrientation = HomeCardOrientation.COLUMN,
) {
    when (cardOrientation) {
        HomeCardOrientation.GRID -> {
            Box(
                modifier = modifier
                    .width(170.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        2.dp,
                        MaterialTheme.colorScheme.secondaryContainer,
                        RoundedCornerShape(16.dp)
                    )
            ) {
                Box(modifier = Modifier, contentAlignment = Alignment.TopEnd) {
                    Column {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(170.dp)
                                .shimmerEffect()
                        )
                        Column(
                            modifier = Modifier.padding(10.dp),
                            verticalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            HomeTextCardLoading(modifier = Modifier.padding(bottom = 10.dp))
                        }
                    }
                }
            }
        }

        HomeCardOrientation.COLUMN -> {
            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        2.dp,
                        MaterialTheme.colorScheme.secondaryContainer,
                        RoundedCornerShape(16.dp)
                    ),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .size(130.dp)
                        .shimmerEffect()
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    HomeTextCardLoading()
                }
            }
        }
    }
}

@Composable
internal fun HomeTextCardLoading(modifier: Modifier = Modifier) {
    val shimmerModifier = Modifier
        .shimmerEffect()
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Box(modifier = shimmerModifier.size(100.dp, 15.dp))
        Box(modifier = shimmerModifier.size(150.dp, 20.dp))
        Box(modifier = shimmerModifier.size(200.dp, 15.dp))
        Box(modifier = shimmerModifier.size(200.dp, 15.dp))
    }
}

@Composable
@Preview
internal fun HomeCardLoadingPreview() {
    ShutterShopTheme {
        HomeCardLoading()
    }
}