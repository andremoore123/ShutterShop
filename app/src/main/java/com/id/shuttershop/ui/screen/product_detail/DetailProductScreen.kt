package com.id.shuttershop.ui.screen.product_detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.id.domain.cart.CartModel
import com.id.domain.product.ProductDetailModel
import com.id.domain.product.VarianceModel
import com.id.domain.rating.RatingModel
import com.id.shuttershop.R
import com.id.shuttershop.ui.components.button.PrimaryButton
import com.id.shuttershop.ui.components.button.PrimaryTextButton
import com.id.shuttershop.ui.components.state.LoadingBar
import com.id.shuttershop.ui.components.topbar.TitleTopBar
import com.id.shuttershop.ui.theme.ShutterShopTheme
import com.id.shuttershop.utils.UiState
import com.id.shuttershop.utils.onError
import com.id.shuttershop.utils.onLoading
import com.id.shuttershop.utils.onSuccess
import kotlinx.coroutines.launch

/**
 * Created by: andre.
 * Date: 08/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@Composable
fun DetailProductScreen(
    modifier: Modifier = Modifier,
    idProduct: String,
    onCheckoutClick: (CartModel) -> Unit,
    viewModel: ProductDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
) {
    val productState by viewModel.productState.collectAsState()
    val isInWishlist by viewModel.isInWishlist.collectAsState()
    val selectedVariant by viewModel.selectedVariant.collectAsState()
    val isBottomShowValue by viewModel.isBottomShowValue.collectAsState()
    val ratingState by viewModel.ratingState.collectAsState()
    val message by viewModel.message.collectAsState()
    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }
    val currentContext = LocalContext.current

    val addItemToCart: (ProductDetailModel, VarianceModel?) -> Unit = { product, variance ->
        viewModel.addItemToCart(product, variance)
        val newMessage = currentContext.getString(R.string.text_add_cart_success)
        viewModel.updateMessage(newMessage)
    }

    productState.onError {
        viewModel.updateMessage(it.errorMessage)
    }

    val detailEvent = DetailProductEvent(
        onVarianceChange = viewModel::setSelectedVariant,
        onCheckoutClick = {
            val data = viewModel::convertDetailToCart.invoke(it, selectedVariant)
            onCheckoutClick(data)
        },
        addItemToCart = addItemToCart,
        onWishlistClick = viewModel::checkOnWishlist,
        onShareClick = {},
        checkIsOnWishlist = viewModel::checkIsInWishlist,
        changeBottomSheetValue = viewModel::modifySheetValue
    )


    LaunchedEffect(key1 = Unit) {
        viewModel.fetchProduct(idProduct)
        viewModel.fetchProductRating(idProduct)
    }

    LaunchedEffect(key1 = message) {
        message?.let {
            scope.launch {
                snackBarHostState.showSnackbar(it)
            }
        }
    }

    Scaffold(modifier = modifier, snackbarHost = {
        SnackbarHost(hostState = snackBarHostState)
    }) { innerPadding ->
        DetailProductContent(
            modifier = Modifier.padding(innerPadding),
            productState = productState,
            ratingState = ratingState,
            onBackClick = onBackClick,
            detailEvent = detailEvent,
            selectedVariant = selectedVariant,
            isInWishlist = isInWishlist,
            isBottomSheetShow = isBottomShowValue
        )
    }
}

@Composable
internal fun DetailProductContent(
    modifier: Modifier = Modifier,
    productState: UiState<ProductDetailModel>,
    ratingState: UiState<List<RatingModel>>,
    onBackClick: () -> Unit,
    isInWishlist: Boolean,
    isBottomSheetShow: Boolean,
    selectedVariant: VarianceModel?,
    detailEvent: DetailProductEvent,
) {
    val defaultHorizontalPadding = Modifier.padding(horizontal = 16.dp)
    Column(
        modifier = modifier
    ) {
        if (isBottomSheetShow) {
            RateBottomSheet(
                ratingState = ratingState,
                changeBottomSheetValue = detailEvent.changeBottomSheetValue
            )
        }
        TitleTopBar(
            showNavigation = true,
            onBackClickListener = onBackClick,
            title = stringResource(R.string.text_detail_product)
        )
        productState.onSuccess {
            LaunchedEffect(key1 = Unit) {
                detailEvent.checkIsOnWishlist(it, selectedVariant)
            }

            Box {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(bottom = 80.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    DetailImages(imageUrls = it.imageUrl)
                    DetailTitle(
                        modifier = Modifier
                            .padding(top = 16.dp)
                            .then(defaultHorizontalPadding),
                        detailModel = it,
                        isInWishlist = isInWishlist,
                        onAddToWishlist = detailEvent.onWishlistClick,
                        onShareClick = detailEvent.onShareClick,
                        selectedVariant = selectedVariant
                    )
                    HorizontalDivider()
                    DetailVariance(
                        modifier = defaultHorizontalPadding,
                        selectedVariant = selectedVariant,
                        detailModel = it,
                        onVarianceChange = detailEvent.onVarianceChange
                    )
                    HorizontalDivider()
                    DetailDesc(detailModel = it, modifier = defaultHorizontalPadding)
                    HorizontalDivider()
                    DetailReview(
                        detailModel = it,
                        modifier = defaultHorizontalPadding,
                        onBottomSheetChange = detailEvent.changeBottomSheetValue
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.surface)
                        .then(defaultHorizontalPadding)
                        .padding(vertical = 10.dp)
                        .align(Alignment.BottomCenter),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedButton(
                        onClick = { detailEvent.onCheckoutClick(it) },
                        modifier = Modifier.fillMaxWidth(0.5f)
                    ) {
                        Text(text = stringResource(R.string.text_buy_now))
                    }
                    PrimaryButton(text = stringResource(id = R.string.text_button_add_cart),
                        modifier = Modifier.fillMaxWidth(1f),
                        onClick = { detailEvent.addItemToCart(it, selectedVariant) })
                }
            }
        }
            .onLoading {
                LoadingBar()
            }
    }
}

@Composable
internal fun DetailTitle(
    modifier: Modifier = Modifier, detailModel: ProductDetailModel,
    onShareClick: () -> Unit = {},
    selectedVariant: VarianceModel?,
    isInWishlist: Boolean,
    onAddToWishlist: (ProductDetailModel, VarianceModel?) -> Unit,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = detailModel.getFormattedCurrency(selectedVariant),
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = onShareClick) {
                Icon(imageVector = Icons.Default.Share, contentDescription = null)
            }
            IconButton(onClick = { onAddToWishlist(detailModel, selectedVariant) }) {
                Icon(
                    imageVector = if (isInWishlist) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = null
                )
            }
        }
        Text(text = detailModel.productName, style = MaterialTheme.typography.titleMedium)
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.text_sold, detailModel.productSold),
                style = MaterialTheme.typography.bodyMedium
            )
            Row(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .border(1.dp, DividerDefaults.color, RoundedCornerShape(16.dp))
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(imageVector = Icons.Default.Star, contentDescription = null)
                Text(text = "${detailModel.productRating} (${detailModel.totalRating})")
            }
        }
    }
}

@Composable
internal fun DetailDesc(modifier: Modifier = Modifier, detailModel: ProductDetailModel) {
    Column(modifier = modifier) {
        Text(text = "Product Description", style = MaterialTheme.typography.bodyMedium)
        Text(text = detailModel.productDesc, style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
internal fun DetailReview(
    modifier: Modifier = Modifier,
    detailModel: ProductDetailModel,
    onBottomSheetChange: (Boolean) -> Unit,
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "User Reviews", style = MaterialTheme.typography.bodyMedium)
            PrimaryTextButton(text = stringResource(R.string.text_see_all),
                onClick = {
                    onBottomSheetChange(true)
                })
        }
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = Icons.Default.Star, contentDescription = null)
            Text(
                text = detailModel.productRating,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold)
            )
            Text(
                text = "/5.0",
                modifier = Modifier
                    .align(Alignment.Bottom)
                    .padding(bottom = 3.dp),
                style = MaterialTheme.typography.bodyMedium
            )

            Column(
                modifier = Modifier.padding(start = 35.dp)
            ) {
                Text(text = "100% Pembeli Merasa Puas", style = MaterialTheme.typography.labelLarge)
                Row {
                    Text(
                        text = stringResource(R.string.text_user_review, detailModel.totalRating),
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }

    }
}

@Composable
internal fun DetailVariance(
    modifier: Modifier = Modifier,
    selectedVariant: VarianceModel?,
    detailModel: ProductDetailModel,
    onVarianceChange: (ProductDetailModel, VarianceModel) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.text_choose_variance),
            style = MaterialTheme.typography.bodyMedium
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(detailModel.productVariance) {
                FilterChip(
                    onClick = { onVarianceChange(detailModel, it) },
                    selected = selectedVariant == it,
                    label = {
                        Text(text = it.title)
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun DetailImages(
    modifier: Modifier = Modifier,
    imageUrls: List<String> = listOf()
) {
    val pagerState = rememberPagerState {
        imageUrls.size
    }

    Box(modifier = modifier) {
        HorizontalPager(state = pagerState) {
            AsyncImage(
                modifier = Modifier.aspectRatio(1.4f),
                model = imageUrls[it],
                contentDescription = null
            )
        }
        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = 24.dp)
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.primary else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(16.dp)
                )
            }
        }
    }

}

@Composable
@Preview(showBackground = true, showSystemUi = true)
internal fun DetailProductScreenPreview() {
    ShutterShopTheme {
        val dummyData = ProductDetailModel(
            id = "8569",
            productName = "Ira Jenkins",
            productDesc = "phasellus",
            productVariance = listOf(
                VarianceModel(id = 4447, title = "a", additionalPrice = 0), VarianceModel(
                    id = 9198, title = "mea", additionalPrice = 0
                )
            ), productPrice = 230232323,
            productSold = "23",
            productRating = "4.5",
            totalRating = "5",
            imageUrl = listOf("sdf", "df"),
            productStore = "uvuvwve"
        )
        DetailProductContent(
            productState = UiState.Success(dummyData),
            onBackClick = {},
            detailEvent = DetailProductEvent(
                onVarianceChange = { _, _ -> },
                onCheckoutClick = {},
                addItemToCart = { _, _ -> },
                onWishlistClick = { _, _ -> },
                onShareClick = {},
                checkIsOnWishlist = { _, _ -> },
                changeBottomSheetValue = {}
                ),
            selectedVariant = null,
            isInWishlist = false,
            isBottomSheetShow = false,
            ratingState = UiState.Loading
        )
    }
}