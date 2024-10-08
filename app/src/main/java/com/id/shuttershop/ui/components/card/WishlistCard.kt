package com.id.shuttershop.ui.components.card

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
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.id.domain.utils.formatToRupiah
import com.id.domain.wishlist.WishlistModel
import com.id.shuttershop.R
import com.id.shuttershop.ui.components.button.PrimaryButton
import com.id.shuttershop.ui.components.button.PrimaryIconButton
import com.id.shuttershop.ui.theme.ShutterShopTheme

/**
 * Created by: andreputras.
 * Date: 02/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

enum class WishlistCardType {
    GRID, COLUMN
}

@Composable
fun WishlistCard(
    modifier: Modifier = Modifier,
    data: WishlistModel,
    wishlistType: WishlistCardType = WishlistCardType.GRID,
    onDeleteClick: () -> Unit,
    onAddClick: () -> Unit,
) {
    when (wishlistType) {
        WishlistCardType.GRID -> {
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
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(170.dp)
                                .clip(
                                    RoundedCornerShape(16.dp)
                                ),
                            model = data.itemImageUrl, contentDescription = data.itemName
                        )
                        PrimaryIconButton(
                            modifier = Modifier.padding(10.dp),
                            icon = Icons.Default.Delete, onClick = onDeleteClick
                        )
                    }
                    Column(
                        modifier = Modifier.padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        WishlistItemText(data = data, modifier = Modifier.fillMaxWidth())
                        PrimaryButton(
                            text = stringResource(id = R.string.text_button_add_cart),
                            modifier = Modifier.fillMaxWidth(),
                            onClick = onAddClick
                        )
                    }
                }
            }
        }

        WishlistCardType.COLUMN -> {
            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .border(
                        2.dp,
                        MaterialTheme.colorScheme.secondaryContainer,
                        RoundedCornerShape(16.dp)
                    )
                    .padding(15.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .size(100.dp),
                        model = data.itemImageUrl,
                        contentDescription = data.itemName
                    )
                    WishlistItemText(modifier = Modifier.padding(bottom = 10.dp), data = data)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    PrimaryIconButton(icon = Icons.Default.Delete, onClick = onDeleteClick)
                    PrimaryButton(
                        text = stringResource(R.string.text_button_add_cart),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp),
                        onClick = onAddClick
                    )
                }
            }
        }
    }
}

@Composable
internal fun WishlistItemText(
    modifier: Modifier = Modifier,
    data: WishlistModel,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Text(
            text = "${data.itemName} (${data.itemVariantName})",
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1
        )
        Text(
            text = data.itemPrice.formatToRupiah(),
            style = MaterialTheme.typography.labelLarge.copy(
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
            Text(text = data.itemSeller, style = MaterialTheme.typography.bodySmall)
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
                    data.itemRating,
                    data.itemSold
                ),
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
internal fun ShowWishlistCardPreview() {
    ShutterShopTheme {
        WishlistCard(
            data = WishlistModel.dummyData,
            wishlistType = WishlistCardType.COLUMN,
            onDeleteClick = {},
            onAddClick = {}
        )
    }
}
