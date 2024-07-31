package com.id.shuttershop.ui.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.domain.preference.IPreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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
) : ViewModel() {
    private val _isOnboardShowed = MutableStateFlow(false)
    val isOnboardShowed = _isOnboardShowed.asStateFlow()

    fun fetchIsOnboardShowed() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = preferenceRepository.isOnboardShow()
            _isOnboardShowed.getAndUpdate {
                result
            }
        }
    }
}