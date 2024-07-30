package com.id.shuttershop.ui.screen.onboarding

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.id.shuttershop.R

/**
 * Created by: andreputras.
 * Date: 30/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
data class OnboardingItem(
    @DrawableRes val imageRes: Int,
    @StringRes val titleRes: Int,
    @StringRes val subTitleRes: Int,
) {
    companion object {
        val onBoardingItems = listOf(
            OnboardingItem(
                R.drawable.onboard_first_img,
                R.string.onboard_title_first,
                R.string.onboard_subtitle_first
            ),
            OnboardingItem(
                R.drawable.onboard_second_img,
                R.string.onboard_title_second,
                R.string.onboard_subtitle_second
            ),
            OnboardingItem(
                R.drawable.onboard_third_img,
                R.string.onboard_title_third,
                R.string.onboard_subtitle_third
            ),
        )
    }
}