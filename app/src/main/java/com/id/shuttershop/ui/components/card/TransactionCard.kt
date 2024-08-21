package com.id.shuttershop.ui.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.id.domain.transaction.ItemStatus
import com.id.domain.transaction.TransactionModel
import com.id.domain.transaction.TransactionStatus
import com.id.domain.utils.formatToRupiah
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
    navigateToRating: (TransactionModel) -> Unit,
) {
    val itemDesc = when (transactionModel.itemStatus) {
        ItemStatus.ONE_TYPE_ITEM -> {
            stringResource(id = R.string.text_total_item, transactionModel.itemTotal)
        }

        ItemStatus.MORE_THAN_ONE_TYPE_ITEM -> {
            stringResource(R.string.other_products, transactionModel.itemTotal)
        }
    }

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
                AsyncImage(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .size(60.dp),
                    model = transactionModel.itemImageUrl,
                    contentDescription = transactionModel.itemName
                )
                Column(
                    modifier = Modifier.padding(start = 10.dp)
                ) {
                    Text(
                        text = transactionModel.itemName,
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = itemDesc, style = MaterialTheme.typography.bodySmall
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
                        text = transactionModel.transactionTotal.formatToRupiah(),
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                    )
                }
                if (transactionModel.review.isEmpty() && transactionModel.rating == 0) {
                    Spacer(modifier = Modifier.weight(1f))
                    PrimaryButton(text = stringResource(R.string.text_review), onClick = {
                        navigateToRating(transactionModel)
                    })
                }
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
                itemName = "Aurelia Craig",
                itemTotal = 7737,
                itemImageUrl = "http://www.bing.com/search?q=populo",
                transactionTotal = 3778,
                transactionStatus = TransactionStatus.SUCCESS,
                transactionDate = "dis",
                itemStatus = ItemStatus.MORE_THAN_ONE_TYPE_ITEM
            ),
            navigateToRating = {}
        )
    }
}
