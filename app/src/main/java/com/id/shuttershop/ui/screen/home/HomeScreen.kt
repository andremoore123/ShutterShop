package com.id.shuttershop.ui.screen.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.AssistChip
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
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
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.id.domain.product.ProductFilterParams
import com.id.domain.product.ProductModel
import com.id.shuttershop.R
import com.id.shuttershop.ui.components.SearchTextField
import com.id.shuttershop.ui.components.button.PrimaryIconButton
import com.id.shuttershop.ui.components.card.HomeCard
import com.id.shuttershop.ui.components.card.HomeCardOrientation
import com.id.shuttershop.ui.components.state.NotFoundErrorState
import com.id.shuttershop.ui.components.state.UnknownErrorState
import com.id.shuttershop.ui.components.state.shimmer.HomeCardLoading
import com.id.shuttershop.ui.components.topbar.HomeTopBar
import com.id.shuttershop.ui.screen.wishlist.WishlistViewModel.Companion.COLUMN_LAYOUT
import com.id.shuttershop.ui.screen.wishlist.WishlistViewModel.Companion.GRID_LAYOUT
import com.id.shuttershop.ui.theme.ShutterShopTheme
import com.id.shuttershop.utils.formatToRupiah
import com.id.shuttershop.utils.onAddContentLoading
import com.id.shuttershop.utils.onEmptyResultError
import com.id.shuttershop.utils.onLoaded
import com.id.shuttershop.utils.onLoadingState
import com.id.shuttershop.utils.onUnknownError
import com.id.shuttershop.utils.toTitleCase
import kotlinx.coroutines.flow.flowOf

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
    navigateToSearch: () -> Unit = {},
    navigateToNotification: () -> Unit,
    navigateToCart: () -> Unit,
    navigateToDetailProduct: (String) -> Unit,
) {
    val currentLayoutType by viewModel.isColumnLayout.collectAsState()
    val products = viewModel.products.collectAsLazyPagingItems()
    val userState by viewModel.userData.collectAsState()
    val isBottomSheetShow by viewModel.isBottomShowValue.collectAsState()
    val productFilterParams by viewModel.productFilter.collectAsState()


    val navigateToDetail: (ProductModel) -> Unit = {
        viewModel.logHomeDetailProduct(it.itemName)
        navigateToDetailProduct(it.id)
    }

    LaunchedEffect(key1 = productFilterParams, key2 = Unit) {
        viewModel.fetchProducts(productFilterParams)
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchUserData()
    }

    val homeEvent = HomeEvent(
        navigateToSearch = navigateToSearch,
        navigateToCart = navigateToCart,
        navigateToNotification = navigateToNotification,
        navigateToDetail = navigateToDetail,
        onLayoutChange = viewModel::setLayoutType,
        changeBottomSheetValue = viewModel::modifySheetValue,
        onShowProduct = viewModel::onFilterChange
    )

    Scaffold(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp),
    ) { innerPadding ->
        HomeContent(
            modifier = Modifier.padding(innerPadding),
            currentLayoutType = currentLayoutType,
            userName = userState.name,
            userImageUrl = userState.email,
            logEvent = HomeLogEvent(
                logSearchButton = viewModel::logSearchButton,
                logNotificationButton = viewModel::logNotificationButton,
                logCartButton = viewModel::logCartButton
            ),
            homeEvent = homeEvent,
            isBottomSheetShow = isBottomSheetShow,
            products = products,
            filterParams = productFilterParams
        )
    }
}

@Composable
internal fun HomeContent(
    modifier: Modifier = Modifier,
    currentLayoutType: String,
    userName: String,
    userImageUrl: String,
    filterParams: ProductFilterParams,
    products: LazyPagingItems<ProductModel>,
    isBottomSheetShow: Boolean,
    homeEvent: HomeEvent,
    logEvent: HomeLogEvent,
) {
    if (isBottomSheetShow) {
        FilterBottomSheet(
            modifier = modifier,
            onShowProduct = homeEvent.onShowProduct,
            changeBottomSheetValue = homeEvent.changeBottomSheetValue,
            filterParams = filterParams
        )
    }
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HomeHeader(
            currentLayoutType = currentLayoutType,
            onLayoutChange = homeEvent.onLayoutChange,
            userName = userName,
            userImageUrl = userImageUrl,
            logEvent = logEvent,
            showBottomSheet = { homeEvent.changeBottomSheetValue(true) },
            navigateToSearch = homeEvent.navigateToSearch,
            navigateToNotification = homeEvent.navigateToNotification,
            navigateToCart = homeEvent.navigateToCart,
            productFilterParams = filterParams,
            onProductFilterChange = homeEvent.onShowProduct
        )
        products.onLoadingState {
            HomeScreenLoading(cardOrientation = currentLayoutType)
        }.onLoaded {
            when (currentLayoutType) {
                GRID_LAYOUT -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        contentPadding = PaddingValues(bottom = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        items(
                            count = products.itemCount,
                            key = products.itemKey(),
                            contentType = { products[it] },
                            itemContent = { index ->
                                products[index]?.let {
                                    HomeCard(
                                        modifier = Modifier.clickable {
                                            homeEvent.navigateToDetail(it)
                                        },
                                        productModel = it,
                                        cardOrientation = HomeCardOrientation.GRID
                                    )
                                }
                            }
                        )
                        products.onAddContentLoading {
                            item {
                                HomeCardLoading(cardOrientation = HomeCardOrientation.GRID)
                            }
                            item {
                                HomeCardLoading(cardOrientation = HomeCardOrientation.GRID)
                            }
                        }
                    }
                }

                COLUMN_LAYOUT -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(
                            count = products.itemCount,
                            key = products.itemKey(),
                            contentType = { products[it] },
                            itemContent = { index ->
                                products[index]?.let {
                                    HomeCard(
                                        modifier = Modifier.clickable {
                                            homeEvent.navigateToDetail(it)
                                        },
                                        productModel = it,
                                        cardOrientation = HomeCardOrientation.COLUMN
                                    )
                                }
                            }
                        )
                        products.onAddContentLoading {
                            item {
                                HomeCardLoading(cardOrientation = HomeCardOrientation.COLUMN)
                            }
                        }
                    }
                }
            }
            }.onUnknownError {
                UnknownErrorState(onRetryClick = { products.refresh() })
        }.onEmptyResultError {
            NotFoundErrorState(modifier = Modifier.fillMaxSize())
        }
    }
}

@Composable
internal fun HomeScreenLoading(
    cardOrientation: String
) {
    when (cardOrientation) {
        GRID_LAYOUT -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(5) {
                    HomeCardLoading(cardOrientation = HomeCardOrientation.GRID)
                }
            }
        }

        COLUMN_LAYOUT -> {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(5) {
                    HomeCardLoading(cardOrientation = HomeCardOrientation.COLUMN)
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
    productFilterParams: ProductFilterParams,
    onProductFilterChange: (ProductFilterParams) -> Unit,
    onLayoutChange: (String) -> Unit,
    showBottomSheet: () -> Unit,
    navigateToNotification: () -> Unit,
    navigateToCart: () -> Unit,
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
                    navigateToNotification.invoke()
                })
            PrimaryIconButton(
                icon = Icons.Default.ShoppingCart,
                modifier = Modifier.size(60.dp),
                onClick = {
                    logEvent.logCartButton()
                    navigateToCart.invoke()
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
                Text(text = stringResource(R.string.text_filter))
            })
            ActiveFilterChip(
                productFilterParams = productFilterParams,
                onClickFilterClick = onProductFilterChange
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
    }
}

@Composable
internal fun ActiveFilterChip(
    modifier: Modifier = Modifier,
    productFilterParams: ProductFilterParams,
    onClickFilterClick: (ProductFilterParams) -> Unit,
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 10.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        item {
            if (productFilterParams.sortBy != null) {
                FilterChip(selected = true,
                    onClick = { onClickFilterClick.invoke(productFilterParams.copy(sortBy = null)) },
                    label = {
                        Text(
                            text = stringResource(
                                R.string.text_sort_by, productFilterParams.sortBy.toTitleCase()
                            )
                        )
                    })
            }
        }
        item {
            if (productFilterParams.lowestPrice != null) {
                FilterChip(selected = true,
                    onClick = { onClickFilterClick.invoke(productFilterParams.copy(lowestPrice = null)) },
                    label = {
                        Text(
                            text = stringResource(
                                R.string.text_lowest_price,
                                productFilterParams.lowestPrice.formatToRupiah()
                            )
                        )
                    })
            }
        }
        item {
            if (productFilterParams.highestPrice != null) {
                FilterChip(selected = true,
                    onClick = { onClickFilterClick.invoke(productFilterParams.copy(highestPrice = null)) },
                    label = {
                        Text(
                            text = stringResource(
                                R.string.text_highest_price,
                                productFilterParams.highestPrice.formatToRupiah()
                            )
                        )
                    })
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
            userName = "",
            userImageUrl = "",
            logEvent = HomeLogEvent(),
            homeEvent = HomeEvent(
                onLayoutChange = {},
                navigateToDetail = {},
                navigateToSearch = {},
                navigateToNotification = {},
                navigateToCart = {},
                changeBottomSheetValue = {},
                onShowProduct = {}
            ),
            isBottomSheetShow = false,
            filterParams = ProductFilterParams(),
            products = flowOf(
                PagingData.from(
                    listOf<ProductModel>(),
                    sourceLoadStates =
                    LoadStates(
                        refresh = LoadState.NotLoading(false),
                        append = LoadState.NotLoading(false),
                        prepend = LoadState.NotLoading(false),
                    ),
                ),
            ).collectAsLazyPagingItems(),
        )
    }
}
