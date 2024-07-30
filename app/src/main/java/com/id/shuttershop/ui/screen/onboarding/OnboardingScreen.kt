package com.id.shuttershop.ui.screen.onboarding;

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.id.shuttershop.R
import com.id.shuttershop.ui.components.button.PrimaryButton
import com.id.shuttershop.ui.components.button.PrimaryTextButton
import com.id.shuttershop.ui.components.card.OnboardCard
import com.id.shuttershop.ui.screen.onboarding.OnboardingItem.Companion.onBoardingItems
import com.id.shuttershop.ui.theme.ShutterShopTheme
import kotlinx.coroutines.launch

/**
 * Created by: andreputras.
 * Date: 30/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    viewModel: OnboardViewModel = hiltViewModel(),
    navigateAfterOnboard: () -> Unit = {},
) {
    val isOnboardShowed by viewModel.isShowOnboardState.collectAsState()
    LaunchedEffect(key1 = isOnboardShowed) {
        viewModel.fetchShowOnboard()
        if (isOnboardShowed) {
            navigateAfterOnboard()
        } else {
            viewModel.onBoardShowed()
        }
    }

    val onBoardList = onBoardingItems
    val pagerState = rememberPagerState(pageCount = {
        onBoardList.size
    })
    val coroutineScope = rememberCoroutineScope()

    val nextScreen = {
        val currentScreen = pagerState.currentPage
        if (currentScreen < onBoardList.size - 1) {
            coroutineScope.launch {
                pagerState.animateScrollToPage(currentScreen + 1)
            }
        } else {
            navigateAfterOnboard()
        }
    }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        HorizontalPager(state = pagerState) { page ->
            val onBoardItem = onBoardList[page]
            OnboardCard(onBoardItem = onBoardItem)
        }

        Row(
            Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(top = 40.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pagerState.pageCount) { iteration ->
                val color =
                    if (pagerState.currentPage == iteration) MaterialTheme.colorScheme.primary else Color.LightGray
                Box(
                    modifier = Modifier
                        .padding(2.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(16.dp)
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 60.dp)
        ) {
            PrimaryTextButton(text = stringResource(id = R.string.text_skip)) {
                navigateAfterOnboard()
            }
            PrimaryButton(text = stringResource(id = R.string.text_next)) {
                nextScreen()
            }
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun ShowOnboardingScreenPreview() {
    ShutterShopTheme {
        OnboardingScreen()
    }
}
