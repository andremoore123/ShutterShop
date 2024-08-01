package com.id.shuttershop.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.domain.preference.IPreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
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

    fun setDarkMode(value: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            preferenceRepository.setDarkMode()
        }
    }

    fun setIndonesiaLanguage(value: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            preferenceRepository.setLanguage()
        }
    }
}