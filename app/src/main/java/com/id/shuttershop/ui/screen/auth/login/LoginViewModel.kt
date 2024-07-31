package com.id.shuttershop.ui.screen.auth.login

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.domain.auth.LoginUseCase
import com.id.shuttershop.utils.UiState
import com.id.shuttershop.utils.handleUpdateUiState
import com.id.shuttershop.utils.onError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by: andreputras.
 * Date: 31/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val emailValue = savedStateHandle.getStateFlow(EMAIL, "")
    val passwordValue = savedStateHandle.getStateFlow(PASSWORD, "")
    val messageValue = savedStateHandle.getStateFlow(MESSAGE, "")

    private val _loginUiState = MutableStateFlow<UiState<String>>(UiState.Initiate)
    val loginUiState = _loginUiState.asStateFlow()


    fun login(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            with(_loginUiState) {
                handleUpdateUiState(UiState.Loading)
                loginUseCase(email, password).onError { error ->
                    handleUpdateUiState(UiState.Error(error))
                }
            }
        }
    }

    fun onMessageValueChange(value: String) {
        savedStateHandle[MESSAGE] = value
    }

    fun onEmailValueChange(value: String) {
        savedStateHandle[EMAIL] = value
    }

    fun onPasswordChange(value: String) {
        savedStateHandle[PASSWORD] = value
    }

    companion object {
        private const val EMAIL = "emailValue"
        private const val PASSWORD = "passwordValue"
        private const val MESSAGE = "messageValue"
    }
}