package com.id.shuttershop.ui.screen.onboarding

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.domain.analytic.IAnalyticRepository
import com.id.domain.preference.IPreferenceRepository
import com.id.shuttershop.utils.analytics.AnalyticsConstants
import com.id.shuttershop.utils.analytics.ScreenConstants.SCREEN_ONBOARDING
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by: andreputras.
 * Date: 30/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@HiltViewModel
class OnboardViewModel @Inject constructor(
    private val preferenceRepository: IPreferenceRepository,
    private val analyticRepository: IAnalyticRepository,
) : ViewModel() {
    private val _isShowOnboardState = MutableStateFlow(false)
    val isShowOnboardState = _isShowOnboardState.asStateFlow()

    fun fetchShowOnboard() {
        viewModelScope.launch(Dispatchers.IO) {
            val isShowed = preferenceRepository.isOnboardShow()
            Log.d("ONBARDVM", isShowed.toString())
            _isShowOnboardState.getAndUpdate {
                isShowed
            }
        }
    }

    fun onBoardShowed() {
        viewModelScope.launch(Dispatchers.IO) {
            preferenceRepository.showOnBoard()
        }
    }

    fun logOnboardSkipEvent() {
        val params = Bundle().apply {
            putString(AnalyticsConstants.PARAM_SCREEN_NAME, SCREEN_ONBOARDING)
        }
        analyticRepository.logEvent(AnalyticsConstants.EVENT_ONBOARD_SKIP, params)
    }

    fun logOnboardNextEvent(screenIndex: Int) {
        val params = Bundle().apply {
            putString(AnalyticsConstants.PARAM_SCREEN_NAME, SCREEN_ONBOARDING)
            putInt(AnalyticsConstants.PARAM_SCREEN_INDEX, screenIndex)
        }
        analyticRepository.logEvent(AnalyticsConstants.EVENT_ONBOARD_NEXT, params)
    }


}
