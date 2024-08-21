package com.id.shuttershop.ui.screen.cart

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.id.domain.cart.CartModel
import com.id.domain.utils.formatToRupiah
import com.id.shuttershop.R
import com.id.shuttershop.ui.components.button.PrimaryButton
import com.id.shuttershop.ui.components.button.PrimaryTextButton
import com.id.shuttershop.ui.components.card.CartCard
import com.id.shuttershop.ui.components.state.EmptyState
import com.id.shuttershop.ui.components.state.LoadingState
import com.id.shuttershop.ui.components.topbar.TitleTopBar
import com.id.shuttershop.ui.theme.ShutterShopTheme
import com.id.shuttershop.utils.UiState
import com.id.shuttershop.utils.onLoading
import com.id.shuttershop.utils.onSuccess

/**
 * Created by: andre.
 * Date: 09/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@Composable
fun CartScreen(
    modifier: Modifier = Modifier,
    navigateToCheckout: (List<CartModel>) -> Unit,
    viewModel: CartViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
    navigateToProductDetail: (String) -> Unit = {},
) {
    val selectedCart by viewModel.selectedCart.collectAsState()
    val screenState by viewModel.screenState.collectAsState()
    val cartList by viewModel.cartList.collectAsState()
    val totalPayment by viewModel.totalPaymentValue.collectAsState()
    var alreadyFetched by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(key1 = cartList) {
        if (cartList.isNotEmpty() && alreadyFetched.not()) {
            viewModel.updateCartStockFromNetwork()
            alreadyFetched = true
        }
    }

    LaunchedEffect(key1 = cartList, key2 = selectedCart) {
        val newPaymentValue = viewModel.calculateTotalPrice()
        viewModel.updateTotalPayment(newPaymentValue)
    }

    val cartEvent = CartEvent(
        addCartQuantity = viewModel::addCartQuantity,
        reduceCartQuantity = viewModel::reduceCartQuantity,
        onCheckoutClick = { navigateToCheckout(viewModel.getSelectedCartModelsByIds(selectedCart)) },
        onSelectCart = viewModel::onSelectCart,
        onSelectAllCart = viewModel::onSelectAllClick,
        removeCarts = viewModel::removeCarts,
        isAllChartSelected = viewModel::isAllCartSelected,
        navigateToProductDetail = navigateToProductDetail
    )
    CartContent(
        modifier = modifier,
        cartEvent = cartEvent,
        onBackClick = onBackClick,
        productList = cartList,
        screenState = screenState,
        selectedCart = selectedCart,
        totalPrice = totalPayment.formatToRupiah()
    )
}

@Composable
internal fun CartContent(
    modifier: Modifier = Modifier,
    cartEvent: CartEvent,
    onBackClick: () -> Unit,
    selectedCart: List<Int>,
    productList: List<CartModel>,
    totalPrice: String,
    screenState: UiState<Boolean>,
) {
    Column(
        modifier = modifier
    ) {
        TitleTopBar(
            title = stringResource(R.string.text_cart),
            onBackClickListener = onBackClick,
            showNavigation = true
        )
        if (productList.isNotEmpty()) {
            Box(modifier = Modifier.fillMaxSize()) {
                screenState.onLoading {
                    LoadingState()
                }.onSuccess {
                    Column(modifier = Modifier.padding(bottom = 100.dp)) {
                        val showSelectOption = productList.isNotEmpty()
                        AnimatedVisibility(visible = showSelectOption) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(end = 16.dp, start = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    enabled = cartEvent.isAllChartSelected() != null,
                                    checked = cartEvent.isAllChartSelected() == true,
                                    onCheckedChange = { state ->
                                        cartEvent.onSelectAllCart(state)
                                    })
                                Text(
                                    text = stringResource(R.string.text_select_all),
                                    modifier = Modifier.padding(start = 5.dp)
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                PrimaryTextButton(
                                    text = stringResource(id = R.string.text_delete),
                                    enabled = selectedCart.isNotEmpty(),
                                    onClick = { cartEvent.removeCarts(listOf()) }
                                )
                            }
                            HorizontalDivider()
                        }
                        LazyColumn(
                            modifier = Modifier
                                .padding(top = 16.dp)
                                .padding(start = 4.dp, end = 16.dp),
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(productList) { cart ->
                                CartCard(
                                    modifier = Modifier.clickable {
                                        cartEvent.navigateToProductDetail(cart.itemId)
                                    },
                                    cartModel = cart,
                                    isSelected = selectedCart.any { cart.cartId == it },
                                    onCheckClick = { cartEvent.onSelectCart.invoke(it, cart) },
                                    onItemAdd = { cartEvent.addCartQuantity(cart) },
                                    onItemMinus = { cartEvent.reduceCartQuantity(cart) },
                                    onRemoveClick = { cartEvent.removeCarts(listOf(cart.cartId ?: 0)) },
                                )
                            }
                        }
                    }
                    CartBottom(
                        modifier = Modifier.align(Alignment.BottomCenter),
                        totalPrice = totalPrice,
                        enabled = selectedCart.isNotEmpty(),
                        onCheckoutClick = cartEvent.onCheckoutClick
                    )
                }
            }

        } else {
            EmptyState(title = stringResource(R.string.text_title_empty_cart), desc = stringResource(
                R.string.text_desc_empty_cart
            )
            )
        }
    }
}

@Composable
internal fun CartBottom(
    modifier: Modifier = Modifier,
    totalPrice: String,
    enabled: Boolean = true,
    onCheckoutClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.onPrimary)
    ) {
        HorizontalDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = stringResource(id = R.string.text_total_payment))
                Text(
                    text = totalPrice, style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            PrimaryButton(
                text = stringResource(id = R.string.text_checkout),
                onClick = onCheckoutClick,
                enabled = enabled
            )
        }
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
internal fun CartScreenPreview() {
    ShutterShopTheme {
        CartContent(
            screenState = UiState.Success(
                true
            ), onBackClick = {}, cartEvent = CartEvent(addCartQuantity = {},
                reduceCartQuantity = {},
                onCheckoutClick = {},
                onSelectCart = { _, _ -> },
                onSelectAllCart = {},
                removeCarts = {},
                isAllChartSelected = { false },
                navigateToProductDetail = {}),
            selectedCart = listOf(),
            productList = listOf(
                CartModel(
                    itemId = "2626",
                    itemName = "Bertie Conway",
                    itemVariantName = "eius",
                    itemStock = 1,
                    itemCount = 1,
                    itemPrice = 1333
                ),
                CartModel(
                    itemId = "2626",
                    itemName = "Bertie Conway",
                    itemVariantName = "eius",
                    itemStock = 4,
                    itemCount = 3,
                    itemPrice = 1333
                ),
                CartModel(
                    itemId = "2626",
                    itemName = "Bertie Conway",
                    itemVariantName = "eius",
                    itemStock = 0,
                    itemCount = 5,
                    itemPrice = 1333
                ),
                CartModel(
                    itemId = "2626",
                    itemName = "Bertie Conway",
                    itemVariantName = "eius",
                    itemStock = 2492,
                    itemCount = 5,
                    itemPrice = 1333
                ),
                CartModel(
                    itemId = "2626",
                    itemName = "Bertie Conway",
                    itemVariantName = "eius",
                    itemStock = 2492,
                    itemCount = 5,
                    itemPrice = 1333
                ),
                CartModel(
                    itemId = "2626",
                    itemName = "Bertie Conway",
                    itemVariantName = "eius",
                    itemStock = 2492,
                    itemCount = 5,
                    itemPrice = 1333
                ),
                CartModel(
                    itemId = "2626",
                    itemName = "Bertie Conway",
                    itemVariantName = "eius",
                    itemStock = 2492,
                    itemCount = 5,
                    itemPrice = 1333
                ),
                CartModel(
                    itemId = "2626",
                    itemName = "Bertie Conway",
                    itemVariantName = "eius",
                    itemStock = 2492,
                    itemCount = 5,
                    itemPrice = 1333
                ),
            ),
            totalPrice = ""
        )
    }
}
