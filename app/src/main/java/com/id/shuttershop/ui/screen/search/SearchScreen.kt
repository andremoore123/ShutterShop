package com.id.shuttershop.ui.screen.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.id.domain.product.ProductModel
import com.id.shuttershop.R
import com.id.shuttershop.ui.components.SearchTextField
import com.id.shuttershop.ui.components.button.PrimaryIconButton
import com.id.shuttershop.ui.components.card.HomeCard
import com.id.shuttershop.ui.components.card.HomeCardOrientation
import com.id.shuttershop.ui.components.state.HttpErrorState
import com.id.shuttershop.ui.components.state.UnknownErrorState
import com.id.shuttershop.ui.components.state.shimmer.HomeCardLoading
import com.id.shuttershop.ui.theme.ShutterShopTheme
import com.id.shuttershop.utils.onAddContentLoading
import com.id.shuttershop.utils.onEmptyResultError
import com.id.shuttershop.utils.onLoaded
import com.id.shuttershop.utils.onLoadingState
import com.id.shuttershop.utils.onUnknownError
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

/**
 * Created by: andre.
 * Date: 08/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@Composable
fun SearchScreen(
    modifier: Modifier = Modifier,
    viewModel: SearchViewModel = hiltViewModel(),
    navigateBack: () -> Unit = {},
    navigateToDetail: (String) -> Unit,
) {
    val searchData = viewModel.searchData.collectAsLazyPagingItems()
    val searchValue by viewModel.searchValue.collectAsState()
    val messageValue by viewModel.messageValue.collectAsState()
    val coroutine = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = searchValue) {
        delay(500)
        viewModel.fetchSearch(searchValue)
        coroutine.coroutineContext.cancel()
    }

    LaunchedEffect(key1 = messageValue) {
        if (messageValue.isNotEmpty()) {
            coroutine.launch {
                snackBarHostState.showSnackbar(messageValue)
            }
        }
    }

    LaunchedEffect(key1 = searchData.loadState) {
        if (searchData.loadState.refresh is LoadState.Error) {
            val errorMessage =
                (searchData.loadState.refresh as LoadState.Error).error.message.toString()
            viewModel.setMessageValue(errorMessage)
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { innerPadding ->
        SearchContent(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(innerPadding),
            searchState = searchData,
            searchValue = searchValue,
            onSearchValueChange = viewModel::onSearchValueChange,
            onProductClick = navigateToDetail,
            onNavigateBack = navigateBack,
        )
    }
}


@Composable
internal fun SearchContent(
    modifier: Modifier = Modifier,
    searchState: LazyPagingItems<ProductModel>,
    searchValue: String,
    onSearchValueChange: (String) -> Unit,
    onProductClick: (String) -> Unit,
    onNavigateBack: () -> Unit,
) {
    val onRetry = {
        searchState.retry()
    }
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            PrimaryIconButton(
                icon = Icons.AutoMirrored.Default.ArrowBack,
                modifier = Modifier
                    .padding(bottom = 5.dp)
                    .size(45.dp),
                onClick = onNavigateBack
            )
            SearchTextField(
                value = searchValue,
                onValueChange = onSearchValueChange,
                hint = stringResource(id = R.string.text_search_camera),
                enabled = true
            )
        }

        Box(modifier = Modifier) {
            searchState.run {
                onLoadingState {
                    SearchScreenLoading()
                }
                onLoaded {
                    LazyColumn(
                        modifier = Modifier.padding(top = 24.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(searchState.itemCount) { index ->
                            searchState[index]?.let {
                                HomeCard(
                                    modifier = Modifier.clickable { onProductClick(it.id) },
                                    productModel = it,
                                    cardOrientation = HomeCardOrientation.COLUMN
                                )
                            }
                        }
                        this@run.onAddContentLoading {
                            item {
                                HomeCardLoading()
                            }
                        }
                    }
                }
                onEmptyResultError {
                    HttpErrorState(errorCode = it, onRetryClick = onRetry)
                }
                onUnknownError {
                    UnknownErrorState(onRetryClick = onRetry)
                }
            }
        }
    }
}

@Composable
internal fun SearchScreenLoading(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier.padding(top = 24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(5) {
            HomeCardLoading(cardOrientation = HomeCardOrientation.COLUMN)
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
internal fun SearchScreenPreview() {
    ShutterShopTheme {
        SearchContent(
            searchState = flowOf(
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
            searchValue = "sdf",
            onSearchValueChange = {},
            onProductClick = {},
            onNavigateBack = {},
        )
    }
}
