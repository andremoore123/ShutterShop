package com.id.shuttershop.ui.components.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.id.shuttershop.R
import com.id.shuttershop.ui.theme.ShutterShopTheme

/**
 * Created by: andre.
 * Date: 14/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@Composable
fun AnimatedSuccess(
    modifier: Modifier = Modifier
) {
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.success_animation)
    )
    LottieAnimation(
        composition,
        modifier = modifier,
        iterations = Int.MAX_VALUE,
        reverseOnRepeat = true
    )
}

@Composable
@Preview
internal fun AnimatedSuccessPreview() {
    ShutterShopTheme {
        AnimatedSuccess()
    }
}