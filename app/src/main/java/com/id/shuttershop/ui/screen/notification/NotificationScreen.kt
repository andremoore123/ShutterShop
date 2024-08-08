package com.id.shuttershop.ui.screen.notification

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import com.id.domain.history.HistoryModel
import com.id.shuttershop.R
import com.id.shuttershop.ui.components.card.NotificationCard
import com.id.shuttershop.ui.components.topbar.TitleTopBar
import com.id.shuttershop.ui.theme.ShutterShopTheme
import com.id.shuttershop.utils.UiState
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
        onBackClick = onBackClick
    )
}

@Composable
internal fun NotificationContent(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    notificationState: UiState<List<HistoryModel>>
) {
    Column(
        modifier = modifier
    ) {
        TitleTopBar(
            title = stringResource(R.string.text_notification),
            onBackClickListener = onBackClick,
            showNavigation = true
        )
        notificationState.onSuccess {
            LazyColumn(
                modifier = Modifier.padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
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
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
internal fun NotificationScreenPreview() {
    ShutterShopTheme {
        val dummyData = listOf(
            HistoryModel(id = "postea", title = "elaboraret", subTitle = "signiferumque"),
            HistoryModel(id = "postea", title = "elaboraret", subTitle = "signiferumque"),
            HistoryModel(id = "postea", title = "elaboraret", subTitle = "signiferumque"),
        )
        NotificationContent(
            notificationState = UiState.Success(
                dummyData
            )
        )
    }
}
