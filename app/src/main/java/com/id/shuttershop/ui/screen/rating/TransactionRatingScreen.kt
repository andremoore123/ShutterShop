package com.id.shuttershop.ui.screen.rating

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.id.domain.transaction.CheckoutModel
import com.id.shuttershop.R
import com.id.shuttershop.ui.components.card.transaction.PaymentDetail
import com.id.shuttershop.ui.components.card.transaction.PaymentRatingCard
import com.id.shuttershop.ui.components.state.LoadingState
import com.id.shuttershop.ui.components.topbar.TitleTopBar
import com.id.shuttershop.ui.theme.ShutterShopTheme
import com.id.shuttershop.utils.onError
import com.id.shuttershop.utils.onLoading
import com.id.shuttershop.utils.onSuccess
import kotlinx.coroutines.launch

/**
 * Created by: andre.
 * Date: 14/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@Composable
fun TransactionRatingScreen(
    modifier: Modifier = Modifier,
    viewModel: TransactionRatingViewModel = hiltViewModel(),
    checkoutModel: CheckoutModel,
    navigateToHome: () -> Unit,
) {
    val snackBarHostState = remember {
        SnackbarHostState()
    }


    val coroutine = rememberCoroutineScope()
    val message by viewModel.message.collectAsState()
    val ratingValue by viewModel.rating.collectAsState()
    val reviewValue by viewModel.review.collectAsState()
    val ratingState by viewModel.reviewState.collectAsState()
    val localContext = LocalContext.current

    val transactionRatingEvent = TransactionRatingEvent(
        onRatingChange = viewModel::updateRating,
        onReviewChange = viewModel::updateReview,
        onDoneClick = {
            viewModel.sendRating(
                invoiceId = checkoutModel.invoiceId,
                rating = ratingValue ?: 0,
                review = reviewValue
            )
        },
        onBackToHomeClick = navigateToHome
    )

    LaunchedEffect(key1 = message) {
        if (message.isNotEmpty()) {
            coroutine.launch {
                snackBarHostState.showSnackbar(message)
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) { innerPadding ->
        ratingState.onSuccess {
            LaunchedEffect(key1 = Unit) {
                navigateToHome()
            }
        }.onLoading {
            LoadingState()
        }.onError {
            LaunchedEffect(key1 = Unit) {
                viewModel.updateMessage(localContext.getString(R.string.text_unknown_error))
                viewModel.updateRating(null)
            }
        }
        TransactionRatingContent(
            modifier = Modifier.padding(innerPadding),
            checkoutModel = checkoutModel,
            ratingValue = ratingValue,
            reviewValue = reviewValue,
            ratingEvent = transactionRatingEvent,
        )
    }


}

@Composable
internal fun TransactionRatingContent(
    modifier: Modifier = Modifier,
    checkoutModel: CheckoutModel,
    ratingValue: Int?,
    reviewValue: String,
    ratingEvent: TransactionRatingEvent
) {
    val doneEnabled = ratingValue != null

    Column(modifier = modifier) {
        TitleTopBar(title = stringResource(id = R.string.text_transaction_status))
        Column(
            modifier = Modifier
                .padding(top = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PaymentRatingCard(
                modifier = Modifier.padding(16.dp),
                reviewValue = reviewValue,
                ratingValue = ratingValue ?: 0,
                onReviewChange = ratingEvent.onReviewChange,
                onRatingChange = ratingEvent.onRatingChange
            )
            HorizontalDivider()
            PaymentDetail(
                modifier = Modifier.padding(16.dp),
                transactionId = checkoutModel.invoiceId,
                transactionStatus = stringResource(R.string.text_success),
                transactionDate = checkoutModel.date,
                transactionTime = checkoutModel.time,
                paymentMethod = checkoutModel.paymentName,
                nominalTransaction = checkoutModel.total,
                onDoneClick = ratingEvent.onDoneClick,
                doneEnabled = doneEnabled,
                onBackToHomeClick = ratingEvent.onBackToHomeClick
            )
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
internal fun TransactionRatingScreenPreview() {
    ShutterShopTheme {
        TransactionRatingContent(

            checkoutModel = CheckoutModel(
                invoiceId = "indoctum",
                date = "justo",
                time = "nonumy",
                paymentName = "Helena Baker",
                total = 7852
            ),
            ratingValue = null,
            reviewValue = "",
            ratingEvent = TransactionRatingEvent(
                onRatingChange = {},
                onReviewChange = {},
                onDoneClick = {},
                onBackToHomeClick = {}
            ),
        )
    }
}