package com.id.shuttershop.ui.screen.product_detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.id.domain.rating.RatingModel
import com.id.shuttershop.ui.components.card.RatingReviewCard
import com.id.shuttershop.ui.components.state.EmptyState
import com.id.shuttershop.ui.components.state.LoadingBar
import com.id.shuttershop.ui.components.state.UnknownErrorState
import com.id.shuttershop.ui.theme.ShutterShopTheme
import com.id.shuttershop.utils.OnEmptyError
import com.id.shuttershop.utils.OnUnknownError
import com.id.shuttershop.utils.UiState
import com.id.shuttershop.utils.onError
import com.id.shuttershop.utils.onLoading
import com.id.shuttershop.utils.onSuccess

/**
 * Created by: andre.
 * Date: 08/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RateBottomSheet(
    modifier: Modifier = Modifier,
    ratingState: UiState<List<RatingModel>>,
    changeBottomSheetValue: (Boolean) -> Unit,
    onRetryError: () -> Unit = {},
) {
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    ModalBottomSheet(
        modifier = modifier.fillMaxSize(),
        sheetState = bottomSheetState,
        onDismissRequest = { changeBottomSheetValue(false) }) {
        RateBottomSheetContent(
            modifier = Modifier.padding(vertical = 16.dp, horizontal = 10.dp),
            ratingState = ratingState,
            onRetryError = onRetryError
        )
    }
}


@Composable
internal fun RateBottomSheetContent(
    modifier: Modifier = Modifier,
    ratingState: UiState<List<RatingModel>>,
    onRetryError: () -> Unit = {},
) {
    ratingState.onSuccess {
        LazyColumn(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(it) {
                RatingReviewCard(ratingModel = it, modifier = Modifier.padding(horizontal = 16.dp))
                HorizontalDivider(modifier = Modifier.padding(top = 10.dp))
            }
        }
    }.onError {
        it.OnEmptyError {
            EmptyState(
                title = "Currently There's No Review",
                desc = "Buy This Product and Make A Review"
            )
        }
        it.OnUnknownError {
            UnknownErrorState(onRetryClick = onRetryError)
        }
    }.onLoading {
        LoadingBar()
    }
}

@Composable
@Preview
internal fun RateBottomSheetPreview() {
    ShutterShopTheme {
        RateBottomSheet(
            ratingState = UiState.Loading,
            changeBottomSheetValue = {}
        )
    }
}