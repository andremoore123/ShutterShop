package com.id.shuttershop.ui.screen.home;

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.id.domain.product.ProductModel
import com.id.shuttershop.R
import com.id.shuttershop.ui.components.SearchTextField
import com.id.shuttershop.ui.components.button.PrimaryIconButton
import com.id.shuttershop.ui.components.card.HomeCard
import com.id.shuttershop.ui.components.card.HomeCardOrientation
import com.id.shuttershop.ui.components.topbar.HomeTopBar
import com.id.shuttershop.ui.screen.wishlist.WishlistViewModel.Companion.COLUMN_LAYOUT
import com.id.shuttershop.ui.screen.wishlist.WishlistViewModel.Companion.GRID_LAYOUT
import com.id.shuttershop.ui.theme.ShutterShopTheme
import com.id.shuttershop.utils.UiState
import com.id.shuttershop.utils.onSuccess

/**
 * Created by: andreputras.
 * Date: 05/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val currentLayoutType by viewModel.isColumnLayout.collectAsState()
    val productState by viewModel.productUiState.collectAsState()
    val userState by viewModel.userData.collectAsState()

    val navigateToDetail: (ProductModel) -> Unit = {
        viewModel.logHomeDetailProduct(it.itemName)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchProducts()
        viewModel.fetchUserData()
    }

    HomeContent(
        modifier = modifier.padding(horizontal = 16.dp),
        currentLayoutType = currentLayoutType,
        productState = productState,
        onLayoutChange = viewModel::setLayoutType,
        userName = userState.name,
        userImageUrl = userState.email,
        navigateToDetail = navigateToDetail,
        logEvent = HomeLogEvent(
            logSearchButton = viewModel::logSearchButton,
            logNotificationButton = viewModel::logNotificationButton,
            logCartButton = viewModel::logCartButton
        )
    )
}

@Composable
internal fun HomeContent(
    modifier: Modifier = Modifier,
    currentLayoutType: String,
    userName: String,
    userImageUrl: String,
    productState: UiState<List<ProductModel>>,
    onLayoutChange: (String) -> Unit,
    navigateToDetail: (ProductModel) -> Unit,
    logEvent: HomeLogEvent,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HomeHeader(
            currentLayoutType = currentLayoutType,
            onLayoutChange = onLayoutChange,
            userName = userName,
            userImageUrl = userImageUrl,
            logEvent = logEvent,
            showBottomSheet = {},
            navigateToSearch = {},
        )
        productState.onSuccess {
            when (currentLayoutType) {
                GRID_LAYOUT -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(it) {
                            HomeCard(
                                modifier = Modifier.clickable {
                                    navigateToDetail(it)
                                },
                                productModel = it,
                                cardOrientation = HomeCardOrientation.GRID
                            )
                        }
                    }
                }

                COLUMN_LAYOUT -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(it) {
                            HomeCard(
                                modifier = Modifier.clickable {
                                    navigateToDetail(it)
                                },
                                productModel = it,
                                cardOrientation = HomeCardOrientation.COLUMN
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
internal fun HomeHeader(
    modifier: Modifier = Modifier,
    currentLayoutType: String,
    logEvent: HomeLogEvent,
    userName: String = "",
    userImageUrl: String = "",
    onLayoutChange: (String) -> Unit,
    showBottomSheet: () -> Unit,
    navigateToSearch: () -> Unit = {},
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HomeTopBar(
            userName = userName,
            userImageUrl = userImageUrl
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SearchTextField(
                modifier = Modifier.weight(1f),
                hint = stringResource(R.string.text_search_camera),
                enabled = false,
                onClick = {
                    navigateToSearch()
                    logEvent.logSearchButton()
                }
            )
            PrimaryIconButton(
                icon = Icons.Default.Notifications,
                modifier = Modifier.size(60.dp),
                onClick = {
                    logEvent.logNotificationButton()
                })
            PrimaryIconButton(
                icon = Icons.Default.ShoppingCart,
                modifier = Modifier.size(60.dp),
                onClick = {
                    logEvent.logCartButton()
                })
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            AssistChip(onClick = showBottomSheet, label = {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.baseline_tune_24),
                    contentDescription = null
                )
                Text(text = "Filter")
            })
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
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
internal fun ShowHomeScreenPreview() {
    ShutterShopTheme {
        HomeContent(
            currentLayoutType = HomeViewModel.GRID_LAYOUT,
            productState = UiState.Success(
                listOf(
                    ProductModel.dummyData,
                    ProductModel.dummyData
                )
            ),
            onLayoutChange = {},
            userName = "",
            userImageUrl = "",
            navigateToDetail = {},
            logEvent = HomeLogEvent()
        )
    }
}
