package com.id.shuttershop.ui.screen.wishlist;

import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
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
import kotlinx.coroutines.launch

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
    navigateToDetail: (productId: String) -> Unit,
) {
    val wishlists by viewModel.wishlists.collectAsState()
    val currentLayoutType by viewModel.isColumnLayout.collectAsState()
    val message by viewModel.message.collectAsState()
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val currentContext = LocalContext.current

    val addProductToCart: (WishlistModel) -> Unit = {
        viewModel.addToCart(it)
        val newMessage = currentContext.getString(R.string.text_add_cart_success)
        viewModel.updateMessage(newMessage)
    }

    LaunchedEffect(key1 = message) {
        message?.let {
            scope.launch {
                snackBarHostState.showSnackbar(it)
            }
        }
    }

    val wishlistEvent = WishlistEvent(
        onLayoutChange = viewModel::setLayoutType,
        onDeleteClick = viewModel::deleteWishlist,
        addToCart = addProductToCart,
        navigateToDetail = navigateToDetail
    )

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { innerPadding ->
        WishlistContent(
            modifier = Modifier.padding(innerPadding),
            currentLayoutType = currentLayoutType,
            wishlists = wishlists,
            wishlistEvent = wishlistEvent,
        )
    }

}

@Composable
internal fun WishlistContent(
    modifier: Modifier = Modifier,
    currentLayoutType: String,
    wishlists: List<WishlistModel>,
    wishlistEvent: WishlistEvent,
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
                wishlistEvent.onLayoutChange(currentLayoutType)
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
                            modifier = Modifier.clickable {
                                wishlistEvent.navigateToDetail(it.itemId)
                            },
                            data = it,
                            onDeleteClick = { wishlistEvent.onDeleteClick(it) },
                            onAddClick = { wishlistEvent.addToCart(it) },
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
                            modifier = Modifier.clickable {
                                wishlistEvent.navigateToDetail(it.itemId)
                            },
                            data = it,
                            onDeleteClick = { wishlistEvent.onDeleteClick(it) },
                            onAddClick = { wishlistEvent.addToCart(it) },
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
            currentLayoutType = COLUMN_LAYOUT,
            wishlistEvent = WishlistEvent(
                onLayoutChange = {},
                onDeleteClick = {},
                addToCart = {},
                navigateToDetail = {}
            )
        )
    }
}
