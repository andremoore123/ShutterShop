package com.id.shuttershop.ui.components.card;

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.id.shuttershop.ui.screen.onboarding.OnboardingItem
import com.id.shuttershop.ui.theme.AppTypography
import com.id.shuttershop.ui.theme.ShutterShopTheme

/**
 * Created by: andreputras.
 * Date: 30/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@Composable
fun OnBoardCard(
    modifier: Modifier = Modifier,
    onBoardItem: OnboardingItem = OnboardingItem.onboardingItems.first(),
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        Image(
            painter = painterResource(id = onBoardItem.imageRes),
            contentDescription = stringResource(
                id = onBoardItem.titleRes
            ),
            modifier = Modifier
                .width(250.dp)
                .requiredHeight(260.dp)
                .align(Alignment.CenterHorizontally),
            contentScale = ContentScale.FillBounds
        )
        Text(
            modifier = Modifier.padding(top = 50.dp, bottom = 24.dp),
            text = stringResource(id = onBoardItem.titleRes),
            style = AppTypography.headlineMedium.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = stringResource(id = onBoardItem.subTitleRes),
            style = AppTypography.bodyLarge
        )

    }
}

@Composable
@Preview
internal fun ShowOnboardCardPreview() {
    ShutterShopTheme {
        OnBoardCard()
    }
}
