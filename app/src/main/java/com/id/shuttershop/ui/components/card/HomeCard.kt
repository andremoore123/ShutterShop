package com.id.shuttershop.ui.components.card;

import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.id.domain.product.ProductModel
import com.id.shuttershop.R
import com.id.shuttershop.ui.theme.ShutterShopTheme

/**
 * Created by: andreputras.
 * Date: 05/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

enum class HomeCardOrientation {
    GRID, COLUMN
}

@Composable
fun HomeCard(
    modifier: Modifier = Modifier,
    cardOrientation: HomeCardOrientation = HomeCardOrientation.GRID,
    productModel: ProductModel,
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
                Column {
                    Box(modifier = Modifier, contentAlignment = Alignment.TopEnd) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(170.dp)
                                .clip(
                                    RoundedCornerShape(16.dp)
                                )
                                .background(Color.Black)
                        )
                    }
                    Column(
                        modifier = Modifier.padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        ItemText(
                            modifier = Modifier.padding(bottom = 10.dp),
                            productModel = productModel,
                        )
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
                        .background(Color.Black)
                        .size(130.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {

                    ItemText(
                        modifier = Modifier.padding(10.dp),
                        productModel = productModel,
                    )
                }
            }
        }
    }
}

@Composable
internal fun ItemText(
    modifier: Modifier = Modifier,
    productModel: ProductModel,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(text = productModel.itemName, style = MaterialTheme.typography.bodyMedium)
        Text(
            text = productModel.itemPrice, style = MaterialTheme.typography.labelLarge.copy(
                fontWeight = FontWeight.Bold
            )
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = null,
                modifier = Modifier.size(14.dp)
            )
            Text(text = productModel.itemSeller, style = MaterialTheme.typography.bodySmall)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                modifier = Modifier.size(14.dp)
            )
            Text(
                text = stringResource(
                    R.string.text_item_subtitle,
                    productModel.itemRating,
                    productModel.itemSold
                ),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
internal fun ShowHomeCardPreview() {
    ShutterShopTheme {
        HomeCard(
            cardOrientation = HomeCardOrientation.COLUMN,
            productModel = ProductModel.dummyData,
        )
    }
}
