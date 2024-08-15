package com.id.shuttershop.ui.components.card.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.id.domain.utils.formatToRupiah
import com.id.shuttershop.R
import com.id.shuttershop.ui.components.button.PrimaryButton
import com.id.shuttershop.ui.theme.ShutterShopTheme

/**
 * Created by: andre.
 * Date: 15/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@Composable
fun PaymentDetail(
    modifier: Modifier = Modifier,
    transactionId: String = "",
    transactionStatus: String = "",
    transactionDate: String = "",
    transactionTime: String = "",
    paymentMethod: String = "",
    nominalTransaction: Int = 0,
    onDoneClick: () -> Unit = {}
) {
    Column(modifier = modifier) {
        Text(text = "Transaction Detail", style = MaterialTheme.typography.titleMedium)
        Column(
            modifier = Modifier.padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.text_transaction_id),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(text = transactionId, style = MaterialTheme.typography.labelLarge)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.text_transaction_status),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(text = transactionStatus, style = MaterialTheme.typography.labelLarge)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.text_date),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(text = transactionDate, style = MaterialTheme.typography.labelLarge)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.text_time),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(text = transactionTime, style = MaterialTheme.typography.labelLarge)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.text_payment_method),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(text = paymentMethod, style = MaterialTheme.typography.labelLarge)
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(id = R.string.text_total_payment),
                    style = MaterialTheme.typography.bodyMedium
                )
                Text(
                    text = nominalTransaction.formatToRupiah(),
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        PrimaryButton(
            text = stringResource(id = R.string.text_done),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            onClick = onDoneClick
        )
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
internal fun PaymentDetailPreview() {
    ShutterShopTheme {
        PaymentDetail()
    }
}