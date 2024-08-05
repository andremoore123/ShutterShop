package com.id.shuttershop.ui.screen.profile

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.domain.analytic.IAnalyticRepository
import com.id.domain.auth.LogoutUseCase
import com.id.domain.preference.IPreferenceRepository
import com.id.domain.session.ISessionRepository
import com.id.domain.session.UserModel
import com.id.shuttershop.utils.analytics.AnalyticsConstants
import com.id.shuttershop.utils.analytics.AnalyticsConstants.LOGOUT_BUTTON
import com.id.shuttershop.utils.analytics.ScreenConstants.SCREEN_PROFILE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by: andreputras.
 * Date: 01/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val preferenceRepository: IPreferenceRepository,
    private val sessionRepository: ISessionRepository,
    private val analyticRepository: IAnalyticRepository,
    private val logoutUseCase: LogoutUseCase,
) : ViewModel() {
    val isDarkMode: StateFlow<Boolean> = preferenceRepository.isDarkMode.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = false
    )

    val isIndonesiaLanguage: StateFlow<Boolean> = preferenceRepository.isIndonesiaLanguage.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = false
    )

    private val _userData = MutableStateFlow<UserModel>(UserModel.emptyModel)
    val userData = _userData.asStateFlow()

    fun fetchUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            val response = sessionRepository.fetchUserData()
            _userData.getAndUpdate {
                response
            }
        }
    }

    fun logout(userEmail: String) {
        viewModelScope.launch(Dispatchers.IO) {
            logLogoutAttempt(userEmail)
            logoutUseCase.invoke()
        }
    }

    fun setDarkMode(value: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            preferenceRepository.setDarkMode()
        }
    }

    private fun logLogoutAttempt(email: String) {
        val params = Bundle().apply {
            putString(AnalyticsConstants.PARAM_SCREEN_NAME, SCREEN_PROFILE)
            putString(AnalyticsConstants.PARAM_EMAIL, email)
            putString(AnalyticsConstants.PARAM_BUTTON, LOGOUT_BUTTON)
        }
        analyticRepository.logEvent(AnalyticsConstants.EVENT_LOGOUT_ATTEMPT, params)
    }

    fun setIndonesiaLanguage(value: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            preferenceRepository.setLanguage()
        }
    }
}