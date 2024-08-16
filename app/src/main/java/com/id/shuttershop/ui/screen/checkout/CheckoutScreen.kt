package com.id.shuttershop.ui.screen.checkout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.id.domain.cart.CartModel
import com.id.domain.payment.PaymentModel
import com.id.domain.transaction.CheckoutModel
import com.id.shuttershop.R
import com.id.shuttershop.ui.components.button.PrimaryButton
import com.id.shuttershop.ui.components.card.CheckoutCard
import com.id.shuttershop.ui.components.card.PaymentCard
import com.id.shuttershop.ui.components.state.LoadingState
import com.id.shuttershop.ui.components.topbar.TitleTopBar
import com.id.shuttershop.ui.theme.ShutterShopTheme
import com.id.shuttershop.utils.onError
import com.id.shuttershop.utils.onLoading
import com.id.shuttershop.utils.onSuccess
import kotlinx.coroutines.launch

/**
 * Created by: andre.
 * Date: 09/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@Composable
fun CheckoutScreen(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    checkoutItems: List<CartModel>,
    navigateToPaymentStatus: (CheckoutModel) -> Unit,
    viewModel: CheckoutViewModel = hiltViewModel()
) {
    val paymentMethods by viewModel.paymentMethods.collectAsState()
    val isBottomShowValue by viewModel.isBottomShowValue.collectAsState()
    val selectedPaymentValue by viewModel.selectedPaymentValue.collectAsState()
    val paymentState by viewModel.paymentState.collectAsState()

    val scope = rememberCoroutineScope()
    val snackBarHostState = remember { SnackbarHostState() }

    val checkoutEvent = CheckoutEvent(
        calculatePrice = viewModel::calculateTotalPrice,
        changeBottomSheetValue = viewModel::modifySheetValue,
        onPaymentMethodClick = viewModel::modifySelectPayment,
        onPaymentClick = viewModel::payItem,
    )

    val showSnackBar: (String) -> Unit = {
        scope.launch {
            snackBarHostState.showSnackbar(it)
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            paymentState.onLoading {
                LoadingState()
            }.onError {
                showSnackBar(stringResource(R.string.text_error_checkout))
            }.onSuccess {
                LaunchedEffect(key1 = Unit) {
                    navigateToPaymentStatus(it)
                }
            }
            CheckoutContent(
                modifier = Modifier,
                checkoutItems = checkoutItems,
                paymentMethods = paymentMethods,
                isBottomSheetShow = isBottomShowValue,
                selectedPaymentMethod = selectedPaymentValue,
                checkoutEvent = checkoutEvent,
                onBackClick = onBackClick
            )
        }
    }
}

@Composable
internal fun CheckoutContent(
    modifier: Modifier = Modifier,
    isBottomSheetShow: Boolean,
    selectedPaymentMethod: PaymentModel?,
    checkoutItems: List<CartModel>,
    paymentMethods: List<PaymentModel>,
    checkoutEvent: CheckoutEvent,
    onBackClick: () -> Unit = {},
) {
    Box(modifier = modifier.fillMaxSize()) {
        if (isBottomSheetShow) {
            PaymentBottomSheet(
                paymentMethods = paymentMethods,
                onPaymentTypeClick = checkoutEvent.onPaymentMethodClick,
                changeBottomSheetValue = checkoutEvent.changeBottomSheetValue,
            )
        }
        LazyColumn(
            modifier = Modifier.padding(bottom = 100.dp)
        ) {
            item {
                TitleTopBar(
                    title = stringResource(id = R.string.text_checkout),
                    onBackClickListener = onBackClick,
                    showNavigation = true
                )
                Text(
                    text = "Item Purchased",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )
            }
            items(checkoutItems) {
                CheckoutCard(
                    cartModel = it, modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp)
                )
            }
            item {
                HorizontalDivider()
                Text(
                    text = "Payment Methods",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(16.dp)
                )
                PaymentCard(
                    paymentModel = selectedPaymentMethod,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .border(1.dp, DividerDefaults.color, RoundedCornerShape(10.dp)),
                    onPaymentClick = { checkoutEvent.changeBottomSheetValue(true) }
                )
            }
        }
        CheckoutBottom(
            modifier = Modifier.align(Alignment.BottomCenter),
            totalPrice = checkoutEvent.calculatePrice(checkoutItems),
            enabled = selectedPaymentMethod != null,
            onPayClick = {
                checkoutEvent.onPaymentClick(checkoutItems, selectedPaymentMethod!!)
            }
        )
    }
}

@Composable
internal fun CheckoutBottom(
    modifier: Modifier = Modifier,
    totalPrice: String,
    enabled: Boolean = true,
    onPayClick: () -> Unit = {}
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
            PrimaryButton(text = "Pay", onClick = onPayClick, enabled = enabled)
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
internal fun CheckoutScreenPreview() {
    ShutterShopTheme {
        CheckoutContent(
            checkoutEvent = CheckoutEvent(
                onPaymentMethodClick = {},
                calculatePrice = { "" },
                changeBottomSheetValue = {},
                onPaymentClick = { _, _ -> }
            ),
            paymentMethods = listOf(),
            isBottomSheetShow = false,

            checkoutItems = listOf(
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
            ),
            onBackClick = {},
            selectedPaymentMethod = null,
        )
    }
}