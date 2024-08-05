package com.id.shuttershop.ui.components.card;

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.id.domain.transaction.TransactionModel
import com.id.domain.transaction.TransactionStatus
import com.id.shuttershop.R
import com.id.shuttershop.ui.components.button.PrimaryButton
import com.id.shuttershop.ui.theme.ShutterShopTheme

/**
 * Created by: andreputras.
 * Date: 05/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@Composable
fun TransactionCard(
    modifier: Modifier = Modifier,
    transactionModel: TransactionModel,
    onRateClick: () -> Unit = {},
) {
    Column(
        modifier = modifier
            .border(2.dp, DividerDefaults.color, RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
    ) {
        Row(
            modifier = Modifier.padding(15.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null)
            Text(
                text = stringResource(R.string.text_shop),
                style = MaterialTheme.typography.labelSmall
            )
            Text(
                text = transactionModel.transactionDate,
                style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Normal)
            )
            Spacer(modifier = Modifier.weight(1f))
            TransactionStatusChip(status = transactionModel.transactionStatus.status)
        }
        HorizontalDivider()
        Column(
            modifier = Modifier.padding(15.dp)
        ) {
            Row {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .size(60.dp)
                        .background(Color.Black)
                )
                Column(
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Text(
                        text = transactionModel.itemName,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = stringResource(
                            id = R.string.text_total_item,
                            transactionModel.itemTotal
                        ), style = MaterialTheme.typography.bodySmall
                    )
                }
            }
            Row(
                modifier = Modifier.padding(top = 10.dp),
                verticalAlignment = Alignment.Bottom,
            ) {
                Column {
                    Text(
                        text = stringResource(R.string.text_total_payment),
                        style = MaterialTheme.typography.bodyMedium
                    )
                    Text(
                        text = transactionModel.transactionTotal,
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )

                }
                Spacer(modifier = Modifier.weight(1f))
                PrimaryButton(text = stringResource(R.string.text_rating), onClick = onRateClick)
            }
        }
    }
}

@Composable
internal fun TransactionStatusChip(
    modifier: Modifier = Modifier,
    status: String = TransactionStatus.FAILED.status,
) {
    Column(
        modifier = modifier
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        Text(
            text = status,
            modifier = Modifier.padding(vertical = 2.dp, horizontal = 10.dp),
            style = MaterialTheme.typography.labelLarge.copy(color = MaterialTheme.colorScheme.primary)
        )
    }
}


@Composable
@Preview(showBackground = true, showSystemUi = true)
internal fun ShowTransactionCardPreview() {
    ShutterShopTheme {
        TransactionCard(
            transactionModel = TransactionModel(
                itemName = "Juliet Cleveland",
                itemTotal = "2",
                itemPrice = "faucibus",
                itemImageUrl = "https://duckduckgo.com/?q=gloriatur",
                transactionTotal = "Rp 23.000.000",
                transactionStatus = TransactionStatus.FAILED,
                transactionDate = "23 Januari 2024"
            )
        )
    }
}
