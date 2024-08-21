package com.id.shuttershop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.domain.preference.IPreferenceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * Created by: andreputras.
 * Date: 01/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    preferenceRepository: IPreferenceRepository,
) : ViewModel() {
    val isDarkMode = preferenceRepository.isDarkMode.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = false
    )
    val isIndonesia = preferenceRepository.isIndonesiaLanguage.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = false
    )
}