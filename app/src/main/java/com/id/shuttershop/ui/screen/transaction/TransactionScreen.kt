package com.id.shuttershop.ui.screen.transaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.id.domain.transaction.CheckoutModel
import com.id.domain.transaction.TransactionModel
import com.id.domain.utils.ErrorType
import com.id.shuttershop.R
import com.id.shuttershop.ui.components.card.TransactionCard
import com.id.shuttershop.ui.components.state.EmptyErrorState
import com.id.shuttershop.ui.components.state.UnknownErrorState
import com.id.shuttershop.ui.components.state.shimmer.TransactionCardLoading
import com.id.shuttershop.ui.theme.ShutterShopTheme
import com.id.shuttershop.utils.OnEmptyError
import com.id.shuttershop.utils.OnUnknownError
import com.id.shuttershop.utils.UiState
import com.id.shuttershop.utils.onError
import com.id.shuttershop.utils.onLoading
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
    navigateToRating: (CheckoutModel) -> Unit,
) {
    val transactionState by viewModel.transactionState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchTransaction()
    }

    TransactionContent(
        modifier = modifier.padding(horizontal = 16.dp),
        transactionState = transactionState,
        onRetryClick = { viewModel.fetchTransaction() },
        navigateToRating = {
            navigateToRating(viewModel.transformTransactionToCheckOutModel(it))
            viewModel.rateTransaction(it)
        }
    )
}

@Composable
internal fun TransactionContent(
    modifier: Modifier = Modifier,
    transactionState: UiState<List<TransactionModel>>,
    onRetryClick: () -> Unit,
    navigateToRating: (TransactionModel) -> Unit,
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        transactionState.run {
            onSuccess {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(it) {
                        TransactionCard(transactionModel = it, navigateToRating = navigateToRating)
                    }
                }
            }
            onLoading {
                TransactionScreenLoading()
            }
            onError { errorType: ErrorType ->
                errorType.run {
                    OnEmptyError {
                        EmptyErrorState(
                            title = stringResource(R.string.text_title_empty_transaction),
                            desc = stringResource(
                                R.string.text_desc_empty_transaction
                            )
                        )
                    }
                    OnUnknownError {
                        UnknownErrorState(onRetryClick = onRetryClick)
                    }
                }
            }
        }
    }
}

@Composable
internal fun TransactionScreenLoading(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 16.dp)
    ) {
        items(5) {
            TransactionCardLoading()
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
                listOf()
            ),
            navigateToRating = {},
            onRetryClick = {}
        )
    }
}
