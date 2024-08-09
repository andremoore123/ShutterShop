package com.id.shuttershop.ui.screen.cart

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.id.domain.cart.CartModel
import com.id.shuttershop.R
import com.id.shuttershop.ui.components.button.PrimaryButton
import com.id.shuttershop.ui.components.button.PrimaryTextButton
import com.id.shuttershop.ui.components.card.CartCard
import com.id.shuttershop.ui.components.topbar.TitleTopBar
import com.id.shuttershop.ui.theme.ShutterShopTheme
import com.id.shuttershop.utils.UiState
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
    viewModel: CartViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
) {
    val selectedCart by viewModel.selectedCart.collectAsState()
    val screenState by viewModel.screenState.collectAsState()
    val cartList by viewModel.cartList.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.updateCartStock()
    }

    val cartEvent = CartEvent(
        onAddClick = viewModel::addItemCart,
        onMinusClick = viewModel::reduceItemCart,
        onRemoveCartClick = viewModel::removeCartFromSelected,
        onCheckoutClick = {},
        onSelectCart = viewModel::onSelectCart,
        onSelectAllCart = viewModel::onSelectAllProducts,
        onSelectedCartRemove = viewModel::removeCarts
    )
    CartContent(
        modifier = modifier,
        cartEvent = cartEvent,
        onBackClick = onBackClick,
        selectedCart = selectedCart,
        productList = cartList,
        screenState = screenState,
        totalPrice = viewModel.calculateTotalPrice(selectedCart)
    )
}

@Composable
internal fun CartContent(
    modifier: Modifier = Modifier,
    cartEvent: CartEvent,
    onBackClick: () -> Unit,
    selectedCart: List<CartModel>,
    productList: List<CartModel>,
    totalPrice: String,
    screenState: UiState<Boolean>,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(bottom = 100.dp)) {
            TitleTopBar(
                title = stringResource(R.string.text_cart),
                onBackClickListener = onBackClick,
                showNavigation = true
            )
            screenState.onSuccess {
                val showSelectOption =
                    productList.all { it.itemStock > 0 } and productList.isNotEmpty()
                AnimatedVisibility(visible = showSelectOption) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp, start = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            enabled = it,
                            checked = selectedCart.containsAll(productList) and productList.isNotEmpty(),
                            onCheckedChange = { state ->
                                cartEvent.onSelectAllCart(state, productList)
                            })
                        Text(
                            text = stringResource(R.string.text_select_all),
                            modifier = Modifier.padding(start = 5.dp)
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        PrimaryTextButton(
                            text = stringResource(id = R.string.text_delete),
                            enabled = selectedCart.isNotEmpty(),
                            onClick = { cartEvent.onSelectedCartRemove.invoke(selectedCart) }
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
                            cartModel = cart,
                            isSelected = selectedCart.contains(cart),
                            onCheckClick = { cartEvent.onSelectCart.invoke(it, cart) },
                            onItemAdd = { cartEvent.onAddClick(cart) },
                            onItemMinus = { cartEvent.onMinusClick(cart) },
                            onRemoveClick = { cartEvent.onSelectedCartRemove(listOf(cart)) },
                        )
                    }
                }
            }
        }
        CartBottom(
            modifier = Modifier.align(Alignment.BottomCenter),
            totalPrice = totalPrice,
            enabled = selectedCart.isNotEmpty()
        )
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
                Text(text = "Total Payment")
                Text(
                    text = totalPrice, style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold
                    )
                )
            }
            PrimaryButton(text = "Checkout", onClick = onCheckoutClick, enabled = enabled)
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
            ), onBackClick = {}, cartEvent = CartEvent(onAddClick = {},
                onMinusClick = {},
                onRemoveCartClick = {},
                onCheckoutClick = {},
                onSelectCart = { _, _ -> },
                onSelectAllCart = { _, _ -> },
                onSelectedCartRemove = {}),
            selectedCart = listOf(),
            productList = listOf(
                CartModel(
                    itemId = 2626,
                    itemName = "Bertie Conway",
                    itemDesc = "eius",
                    itemStock = 1,
                    itemCount = 1,
                    itemPrice = 1333
                ),
                CartModel(
                    itemId = 2626,
                    itemName = "Bertie Conway",
                    itemDesc = "eius",
                    itemStock = 4,
                    itemCount = 3,
                    itemPrice = 1333
                ),
                CartModel(
                    itemId = 2626,
                    itemName = "Bertie Conway",
                    itemDesc = "eius",
                    itemStock = 0,
                    itemCount = 5,
                    itemPrice = 1333
                ),
                CartModel(
                    itemId = 2626,
                    itemName = "Bertie Conway",
                    itemDesc = "eius",
                    itemStock = 2492,
                    itemCount = 5,
                    itemPrice = 1333
                ),
                CartModel(
                    itemId = 2626,
                    itemName = "Bertie Conway",
                    itemDesc = "eius",
                    itemStock = 2492,
                    itemCount = 5,
                    itemPrice = 1333
                ),
                CartModel(
                    itemId = 2626,
                    itemName = "Bertie Conway",
                    itemDesc = "eius",
                    itemStock = 2492,
                    itemCount = 5,
                    itemPrice = 1333
                ),
                CartModel(
                    itemId = 2626,
                    itemName = "Bertie Conway",
                    itemDesc = "eius",
                    itemStock = 2492,
                    itemCount = 5,
                    itemPrice = 1333
                ),
                CartModel(
                    itemId = 2626,
                    itemName = "Bertie Conway",
                    itemDesc = "eius",
                    itemStock = 2492,
                    itemCount = 5,
                    itemPrice = 1333
                ),
            ),
            totalPrice = ""
        )
    }
}
