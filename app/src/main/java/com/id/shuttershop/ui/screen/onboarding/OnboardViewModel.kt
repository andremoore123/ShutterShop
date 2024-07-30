package com.id.shuttershop.ui.screen.onboarding

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.domain.preference.IPreferenceRepository
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
}