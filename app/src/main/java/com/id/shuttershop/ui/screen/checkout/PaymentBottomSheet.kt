package com.id.shuttershop.ui.screen.checkout

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.id.domain.payment.PaymentModel
import com.id.domain.payment.PaymentType
import com.id.shuttershop.ui.components.card.PaymentCard
import com.id.shuttershop.ui.theme.ShutterShopTheme

/**
 * Created by: andre.
 * Date: 09/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentBottomSheet(
    modifier: Modifier = Modifier,
    paymentMethods: List<PaymentModel>,
    onPaymentTypeClick: (PaymentModel) -> Unit,
    changeBottomSheetValue: (Boolean) -> Unit,
) {
    ModalBottomSheet(modifier = modifier, onDismissRequest = { changeBottomSheetValue(false) }) {
        PaymentBottomSheetContent(
            modifier = modifier,
            paymentMethods = paymentMethods,
            onPaymentTypeClick = onPaymentTypeClick,
            changeBottomSheetValue = changeBottomSheetValue
        )
    }
}

@Composable
internal fun PaymentBottomSheetContent(
    modifier: Modifier = Modifier,
    paymentMethods: List<PaymentModel>,
    changeBottomSheetValue: (Boolean) -> Unit,
    onPaymentTypeClick: (PaymentModel) -> Unit,
) {
    Column(
        modifier = modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.CenterHorizontally),
            text = "Choose Payment Methods",
            style = MaterialTheme.typography.headlineSmall.copy(
                fontWeight = FontWeight.Bold
            )
        )
        HorizontalDivider()
        LazyColumn {
            val listPaymentType = PaymentType.entries
            listPaymentType.forEach { type ->
                val itemList = paymentMethods.filter { it.paymentType == type }
                if (itemList.isNotEmpty()) {
                    item {
                        Text(
                            text = type.title,
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(top = 20.dp)
                        )
                    }
                    items(itemList) {
                        PaymentCard(paymentModel = it, onPaymentClick = {
                            onPaymentTypeClick(it)
                            changeBottomSheetValue(false)
                        })
                    }
                    if (listPaymentType.last() != type) {
                        item {
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
internal fun PreviewPaymentBottomSheetContent() {
    ShutterShopTheme {
        PaymentBottomSheetContent(
            changeBottomSheetValue = {},
            onPaymentTypeClick = {}, paymentMethods = listOf(
                PaymentModel(
                    idPayment = 234,
                    paymentName = "Colin Hopkins",
                    paymentImageUrl = "https://www.google.com/#q=magnis",
                    paymentType = PaymentType.INSTANT_PAYMENT
                ),
                PaymentModel(
                    idPayment = 343,
                    paymentName = "Colin Hopkins",
                    paymentImageUrl = "https://www.google.com/#q=magnis",
                    paymentType = PaymentType.INSTANT_PAYMENT
                ),
                PaymentModel(
                    idPayment = 234,
                    paymentName = "Colin Hopkins",
                    paymentImageUrl = "https://www.google.com/#q=magnis",
                    paymentType = PaymentType.VIRTUAL_ACCOUNT
                ),
            )
        )
    }
}