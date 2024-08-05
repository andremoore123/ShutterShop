package com.id.shuttershop.ui.screen.transaction;

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.id.domain.transaction.TransactionModel
import com.id.domain.transaction.TransactionStatus
import com.id.shuttershop.ui.components.card.TransactionCard
import com.id.shuttershop.ui.theme.ShutterShopTheme
import com.id.shuttershop.utils.UiState
import com.id.shuttershop.utils.onSuccess

/**
 * Created by: andreputras.
 * Date: 05/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@Composable
fun TransactionScreen(
    modifier: Modifier = Modifier,
    viewModel: TransactionViewModel = hiltViewModel(),
) {
    val transactionState by viewModel.transactionState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchTransaction()
    }
    TransactionContent(
        modifier = modifier.padding(horizontal = 16.dp),
        transactionState = transactionState,
        onRateClick = viewModel::rateTransaction
    )
}

@Composable
internal fun TransactionContent(
    modifier: Modifier = Modifier,
    transactionState: UiState<List<TransactionModel>>,
    onRateClick: (TransactionModel) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        transactionState.onSuccess {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(it) {
                    TransactionCard(transactionModel = it, onRateClick = { onRateClick(it) })
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
internal fun ShowTransactionScreenPreview() {
    ShutterShopTheme {
        TransactionContent(
            modifier = Modifier.padding(horizontal = 16.dp),
            transactionState = UiState.Success(
                listOf(
                    TransactionModel(
                        itemName = "Ruthie Gibbs",
                        itemTotal = "nihil",
                        itemPrice = "sanctus",
                        itemImageUrl = "https://duckduckgo.com/?q=mnesarchum",
                        transactionTotal = "voluptaria",
                        transactionStatus = TransactionStatus.FAILED,
                        transactionDate = "augue"
                    ),
                    TransactionModel(
                        itemName = "Ruthie Gibbs",
                        itemTotal = "nihil",
                        itemPrice = "sanctus",
                        itemImageUrl = "https://duckduckgo.com/?q=mnesarchum",
                        transactionTotal = "voluptaria",
                        transactionStatus = TransactionStatus.FAILED,
                        transactionDate = "augue"
                    ),
                )
            ), {}
        )
    }
}
