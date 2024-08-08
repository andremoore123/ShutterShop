package com.id.shuttershop.ui.screen.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.id.domain.product.ProductModel
import com.id.shuttershop.R
import com.id.shuttershop.ui.components.SearchTextField
import com.id.shuttershop.ui.components.button.PrimaryIconButton
import com.id.shuttershop.ui.components.card.HomeCard
import com.id.shuttershop.ui.components.card.HomeCardOrientation
import com.id.shuttershop.ui.theme.ShutterShopTheme
import com.id.shuttershop.utils.UiState
import com.id.shuttershop.utils.onSuccess
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay

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
    navigateToDetail: (Int) -> Unit = {},
) {
    val searchState by viewModel.searchState.collectAsState()
    val searchValue by viewModel.searchValue.collectAsState()
    val coroutine = rememberCoroutineScope()

    LaunchedEffect(key1 = searchValue) {
        delay(500)
        viewModel.fetchSearch(searchValue)
        coroutine.coroutineContext.cancel()
    }

    SearchContent(
        modifier = modifier.padding(horizontal = 16.dp),
        searchState = searchState,
        searchValue = searchValue,
        onSearchValueChange = viewModel::onSearchValueChange,
        onProductClick = navigateToDetail,
        onNavigateBack = navigateBack
    )
}

@Composable
internal fun SearchContent(
    modifier: Modifier = Modifier,
    searchState: UiState<List<ProductModel>>,
    searchValue: String,
    onSearchValueChange: (String) -> Unit,
    onProductClick: (Int) -> Unit,
    onNavigateBack: () -> Unit,
) {
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
        searchState.onSuccess {
            LazyColumn(
                modifier = Modifier.padding(top = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(it) {
                    HomeCard(
                        modifier = Modifier.clickable { onProductClick(it.id) },
                        productModel = it,
                        cardOrientation = HomeCardOrientation.COLUMN
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
internal fun SearchScreenPreview() {
    ShutterShopTheme {
        SearchContent(
            searchState = UiState.Success(listOf()),
            searchValue = "sdf",
            onSearchValueChange = {},
            onProductClick = {},
            onNavigateBack = {}
        )
    }
}
