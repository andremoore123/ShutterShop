package com.id.shuttershop.ui.screen.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.domain.auth.LogoutUseCase
import com.id.domain.preference.IPreferenceRepository
import com.id.domain.session.ISessionRepository
import com.id.domain.session.UserModel
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

    fun logout() {
        viewModelScope.launch(Dispatchers.IO) {
            logoutUseCase.invoke()
        }
    }

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