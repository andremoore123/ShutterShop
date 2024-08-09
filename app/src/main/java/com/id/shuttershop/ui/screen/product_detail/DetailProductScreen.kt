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
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.id.domain.product.ProductDetailModel
import com.id.domain.product.VarianceModel
import com.id.domain.rating.RatingModel
import com.id.shuttershop.R
import com.id.shuttershop.ui.components.button.IconTextButton
import com.id.shuttershop.ui.components.button.PrimaryButton
import com.id.shuttershop.ui.components.button.PrimaryTextButton
import com.id.shuttershop.ui.components.topbar.TitleTopBar
import com.id.shuttershop.ui.theme.ShutterShopTheme
import com.id.shuttershop.utils.UiState
import com.id.shuttershop.utils.onSuccess

/**
 * Created by: andre.
 * Date: 08/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@Composable
fun DetailProductScreen(
    modifier: Modifier = Modifier,
    idProduct: Int,
    viewModel: ProductDetailViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
) {
    val productState by viewModel.productState.collectAsState()
    val isInWishlist by viewModel.isInWishlist.collectAsState()
    val isInCart by viewModel.isInCart.collectAsState()
    val selectedVariant by viewModel.selectedVariant.collectAsState()
    val isBottomShowValue by viewModel.isBottomShowValue.collectAsState()
    val ratingState by viewModel.ratingState.collectAsState()

    val detailEvent = DetailProductEvent(
        onVarianceChange = viewModel::setSelectedVariant,
        onCheckoutClick = {},
        onCartClick = viewModel::onChartClick,
        onWishlistClick = viewModel::checkOnWishlist,
        onShareClick = {},
        checkIsOnWishlist = viewModel::checkIsInWishlist,
        checkIsOnCart = viewModel::checkIsOnCart,
        changeBottomSheetValue = viewModel::modifySheetValue
    )

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchProduct(idProduct)
        viewModel.fetchProductRating(idProduct)
    }

    DetailProductContent(
        modifier = modifier,
        productState = productState,
        ratingState = ratingState,
        onBackClick = onBackClick,
        detailEvent = detailEvent,
        selectedVariant = selectedVariant,
        isInWishlist = isInWishlist,
        isInCart = isInCart,
        isBottomSheetShow = isBottomShowValue
    )
}

@Composable
internal fun DetailProductContent(
    modifier: Modifier = Modifier,
    productState: UiState<ProductDetailModel>,
    ratingState: UiState<List<RatingModel>>,
    onBackClick: () -> Unit,
    isInWishlist: Boolean,
    isInCart: Boolean,
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
                detailEvent.checkIsOnWishlist(it)
                detailEvent.checkIsOnCart(it)
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
                        onShareClick = detailEvent.onShareClick
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
                    if (isInCart) {
                        IconTextButton(
                            modifier = Modifier.fillMaxWidth(1f),
                            containerColor = MaterialTheme.colorScheme.error,
                            content = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = null
                                    )
                                    Text(text = stringResource(R.string.text_remove))
                                }
                            }, onClick = { detailEvent.onCartClick(it, true) }
                        )
                    } else {
                        PrimaryButton(
                            text = stringResource(id = R.string.text_button_add_cart),
                            modifier = Modifier.fillMaxWidth(1f),
                            onClick = { detailEvent.onCartClick(it, false) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
internal fun DetailTitle(
    modifier: Modifier = Modifier, detailModel: ProductDetailModel,
    onShareClick: () -> Unit = {},
    isInWishlist: Boolean,
    onAddToWishlist: (ProductDetailModel) -> Unit,
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
                text = detailModel.productPrice,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = onShareClick) {
                Icon(imageVector = Icons.Default.Share, contentDescription = null)
            }
            IconButton(onClick = { onAddToWishlist(detailModel) }) {
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
    onVarianceChange: (VarianceModel) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Text(text = "Choose Variant", style = MaterialTheme.typography.bodyMedium)
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(detailModel.productVariance) {
                FilterChip(
                    onClick = { onVarianceChange(it) },
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
            Box(
                modifier = Modifier
                    .aspectRatio(1.4f)
                    .background(Color.Black)
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
            id = 8569,
            productName = "Ira Jenkins",
            productDesc = "phasellus",
            productVariance = listOf(
                VarianceModel(id = 4447, title = "a"), VarianceModel(
                    id = 9198,
                    title = "mea"
                )
            ),
            productPrice = "posidonium",
            productSold = "23",
            productRating = "4.5",
            totalRating = "5",
            imageUrl = listOf("sdf", "df")
        )
        DetailProductContent(
            productState = UiState.Success(dummyData),
            onBackClick = {},
            detailEvent = DetailProductEvent(
                onVarianceChange = {},
                onCheckoutClick = {},
                onCartClick = { _, _ -> },
                onWishlistClick = {},
                onShareClick = {},
                checkIsOnWishlist = {},
                checkIsOnCart = {},
                changeBottomSheetValue = {}
                ),
            selectedVariant = null,
            isInWishlist = false,
            isInCart = true,
            isBottomSheetShow = false,
            ratingState = UiState.Loading
        )
    }
}