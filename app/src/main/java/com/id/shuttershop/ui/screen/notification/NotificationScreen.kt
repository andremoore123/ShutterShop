package com.id.shuttershop.ui.screen.notification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.id.domain.notification.NotificationModel
import com.id.domain.utils.ErrorType
import com.id.shuttershop.R
import com.id.shuttershop.ui.components.card.NotificationCard
import com.id.shuttershop.ui.components.state.EmptyState
import com.id.shuttershop.ui.components.state.HttpErrorState
import com.id.shuttershop.ui.components.state.UnknownErrorState
import com.id.shuttershop.ui.components.state.shimmer.NotificationCardLoading
import com.id.shuttershop.ui.components.topbar.TitleTopBar
import com.id.shuttershop.ui.theme.ShutterShopTheme
import com.id.shuttershop.utils.OnEmptyError
import com.id.shuttershop.utils.OnHttpError
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

@Composable
fun NotificationScreen(
    modifier: Modifier = Modifier,
    viewModel: NotificationViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val notificationState by viewModel.notificationState.collectAsState()

    LaunchedEffect(key1 = Unit) {
        viewModel.fetchNotifications()
    }

    NotificationContent(
        modifier = modifier,
        notificationState = notificationState,
        onBackClick = onBackClick,
        onRetry = { viewModel.fetchNotifications() }
    )
}

@Composable
internal fun NotificationContent(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onRetry: () -> Unit = {},
    notificationState: UiState<List<NotificationModel>>
) {
    Column(
        modifier = modifier
    ) {
        TitleTopBar(
            title = stringResource(R.string.text_notification),
            onBackClickListener = onBackClick,
            showNavigation = true
        )
        notificationState.run {
            onSuccess {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    items(it) {
                        NotificationCard(
                            modifier = Modifier.padding(horizontal = 16.dp),
                            title = it.title,
                            subTitle = it.subTitle
                        )
                        HorizontalDivider(
                            modifier = Modifier.padding(top = 10.dp)
                        )
                    }
                }
            }
            onLoading {
                NotificationScreenLoading()
            }
            onError { errorType: ErrorType ->
                errorType.run {
                    OnHttpError {
                        HttpErrorState(errorCode = it, onRetryClick = onRetry)
                    }
                    OnEmptyError {
                        EmptyState(
                            title = stringResource(R.string.text_title_empty_notification),
                            desc = stringResource(R.string.text_desc_empty_notification)
                        )
                    }
                    OnEmptyError {
                        UnknownErrorState(onRetryClick = onRetry)

                    }
                }
            }
        }
    }
}

@Composable
internal fun NotificationScreenLoading(modifier: Modifier = Modifier) {
    LazyColumn(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(vertical = 16.dp)
    ) {
        items(4) {
            NotificationCardLoading(modifier = Modifier.padding(horizontal = 16.dp))
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
internal fun NotificationScreenPreview() {
    ShutterShopTheme {
        val dummyData = listOf(
            NotificationModel(id = "postea", title = "elaboraret", subTitle = "signiferumque"),
            NotificationModel(id = "postea", title = "elaboraret", subTitle = "signiferumque"),
            NotificationModel(id = "postea", title = "elaboraret", subTitle = "signiferumque"),
        )
        NotificationContent(
            notificationState = UiState.Success(
                dummyData
            )
        )
    }
}
