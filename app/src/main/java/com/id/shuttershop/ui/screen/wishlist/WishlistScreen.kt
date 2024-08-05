package com.id.shuttershop.ui.screen.wishlist;

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.id.domain.wishlist.WishlistModel
import com.id.shuttershop.R
import com.id.shuttershop.ui.components.card.WishlistCard
import com.id.shuttershop.ui.components.card.WishlistCardType
import com.id.shuttershop.ui.screen.wishlist.WishlistViewModel.Companion.COLUMN_LAYOUT
import com.id.shuttershop.ui.screen.wishlist.WishlistViewModel.Companion.GRID_LAYOUT
import com.id.shuttershop.ui.theme.ShutterShopTheme

/**
 * Created by: andreputras.
 * Date: 02/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@Composable
fun WishlistScreen(
    modifier: Modifier = Modifier,
    viewModel: WishlistViewModel = hiltViewModel(),
) {
    val wishlists by viewModel.wishlists.collectAsState()
    val currentLayoutType by viewModel.isColumnLayout.collectAsState()

    WishlistContent(
        modifier = modifier,
        currentLayoutType = currentLayoutType,
        wishlists = wishlists,
        onLayoutChange = viewModel::setLayoutType,
        onDeleteClick = viewModel::deleteWishlist,
        onAddToCard = viewModel::addToCart
    )
}

@Composable
internal fun WishlistContent(
    modifier: Modifier = Modifier,
    currentLayoutType: String,
    wishlists: List<WishlistModel>,
    onLayoutChange: (String) -> Unit,
    onDeleteClick: (WishlistModel) -> Unit = {},
    onAddToCard: (WishlistModel) -> Unit = {},
) {
    Column(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.text_total_item, wishlists.size),
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.weight(1f))
            VerticalDivider(modifier = Modifier.height(30.dp))
            IconButton(onClick = {
                onLayoutChange(currentLayoutType)
            }) {
                if (currentLayoutType == GRID_LAYOUT) {
                    Icon(imageVector = Icons.Default.Menu, contentDescription = null)
                } else {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.baseline_grid_view_24),
                        contentDescription = null
                    )
                }
            }
        }
        when (currentLayoutType) {
            GRID_LAYOUT -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(wishlists) {
                        WishlistCard(
                            data = it,
                            onDeleteClick = { onDeleteClick(it) },
                            onAddClick = { onAddToCard(it) },
                            wishlistType = WishlistCardType.GRID
                        )
                    }
                }
            }

            COLUMN_LAYOUT -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    items(wishlists) {
                        WishlistCard(
                            data = it,
                            onDeleteClick = { onDeleteClick(it) },
                            onAddClick = { onAddToCard(it) },
                            wishlistType = WishlistCardType.COLUMN
                        )
                    }
                }
            }
        }
    }

}

@Composable
@Preview(showSystemUi = true, showBackground = true)
internal fun ShowWishlistScreenPreview() {
    ShutterShopTheme() {
        WishlistContent(
            wishlists = listOf(
                WishlistModel.dummyData,
                WishlistModel.dummyData,
                WishlistModel.dummyData,
            ),
            onLayoutChange = {},
            currentLayoutType = COLUMN_LAYOUT
        )
    }
}
