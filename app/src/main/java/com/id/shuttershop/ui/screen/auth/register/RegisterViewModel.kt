package com.id.shuttershop.ui.screen.auth.register

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.domain.auth.RegisterUseCase
import com.id.shuttershop.utils.UiState
import com.id.shuttershop.utils.handleUpdateUiState
import com.id.shuttershop.utils.onError
import com.id.shuttershop.utils.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by: andreputras.
 * Date: 31/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val emailValue = savedStateHandle.getStateFlow(EMAIL, "")
    val nameValue = savedStateHandle.getStateFlow(NAME, "")
    val passwordValue = savedStateHandle.getStateFlow(PASSWORD, "")
    val messageValue = savedStateHandle.getStateFlow(MESSAGE, "")

    private val _registerUiState = MutableStateFlow<UiState<String>>(UiState.Initiate)
    val registerUiState = _registerUiState.asStateFlow()


    fun login(name: String, email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            with(_registerUiState) {
                handleUpdateUiState(UiState.Loading)
                registerUseCase(name, email, password).onSuccess {
                    handleUpdateUiState(UiState.Success(it))
                }.onError { error ->
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

    fun onNameChange(value: String) {
        savedStateHandle[NAME] = value
    }

    companion object {
        private const val EMAIL = "emailValue"
        private const val NAME = "nameValue"
        private const val PASSWORD = "passwordValue"
        private const val MESSAGE = "messageValue"
    }
}
