package com.id.shuttershop.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.domain.preference.IPreferenceRepository
import com.id.domain.session.ISessionRepository
import com.id.shuttershop.utils.DispatcherProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by: andreputras.
 * Date: 30/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@HiltViewModel
class MainContainerViewModel @Inject constructor(
    private val preferenceRepository: IPreferenceRepository,
    sessionRepository: ISessionRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    private val _isOnboardShowed = MutableStateFlow(false)
    val isOnboardShowed = _isOnboardShowed.asStateFlow()

    val isUserLogin = sessionRepository.isUserLogin().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = false
    )

    fun fetchIsOnboardShowed() {
        viewModelScope.launch(dispatcherProvider.io) {
            val result = preferenceRepository.isOnboardShow()
            _isOnboardShowed.getAndUpdate {
                result
            }
        }
    }
}
