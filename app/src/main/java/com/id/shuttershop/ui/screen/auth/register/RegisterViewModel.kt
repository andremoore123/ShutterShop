package com.id.shuttershop.ui.screen.auth.register

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.domain.analytic.IAnalyticRepository
import com.id.domain.auth.RegisterUseCase
import com.id.domain.utils.resource.onError
import com.id.domain.utils.resource.onSuccess
import com.id.shuttershop.utils.DispatcherProvider
import com.id.shuttershop.utils.UiState
import com.id.shuttershop.utils.analytics.AnalyticsConstants
import com.id.shuttershop.utils.analytics.ScreenConstants.SCREEN_REGISTER
import com.id.shuttershop.utils.handleUpdateUiState
import com.id.shuttershop.utils.validation.ErrorValidation
import com.id.shuttershop.utils.validation.emailValidation
import com.id.shuttershop.utils.validation.isError
import com.id.shuttershop.utils.validation.nameValidation
import com.id.shuttershop.utils.validation.passwordValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by: andreputras.
 * Date: 31/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val analyticRepository: IAnalyticRepository,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    val emailValue: StateFlow<String?> = savedStateHandle.getStateFlow(EMAIL, null)
    val nameValue: StateFlow<String?> = savedStateHandle.getStateFlow(NAME, null)
    val passwordValue: StateFlow<String?> = savedStateHandle.getStateFlow(PASSWORD, null)
    val messageValue = savedStateHandle.getStateFlow(MESSAGE, "")

    private val _registerUiState = MutableStateFlow<UiState<String>>(UiState.Initiate)
    val registerUiState = _registerUiState.asStateFlow()

    val nameValidation: StateFlow<ErrorValidation?> = nameValue.mapLatest {
        it?.nameValidation()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    val emailValidation: StateFlow<ErrorValidation?> = emailValue.mapLatest {
        it?.emailValidation()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    val passwordValidation: StateFlow<ErrorValidation?> = passwordValue.mapLatest {
        it?.passwordValidation()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            with(_registerUiState) {
                handleUpdateUiState(UiState.Loading)
                logRegisterAttempt(email)
                registerUseCase(name, email, password).onSuccess {
                    logRegisterSuccess(email)
                    handleUpdateUiState(UiState.Success(it))
                }.onError { error ->
                    logRegisterFailure(email, error.errorMessage)
                    handleUpdateUiState(UiState.Error(error))
                }
            }
        }
    }
    fun isValidEntries(name: String, email: String, password: String): Boolean {
        val nameValidation = name.nameValidation()
        val emailValidation = email.emailValidation()
        val passwordValidation = password.passwordValidation()
        return emailValidation.isError().not() && passwordValidation.isError().not() && nameValidation.isError().not()
    }

    private fun logRegisterAttempt(email: String) {
        val params = Bundle().apply {
            putString(AnalyticsConstants.PARAM_SCREEN_NAME, SCREEN_REGISTER)
            putString(AnalyticsConstants.PARAM_EMAIL, email)
        }
        analyticRepository.logEvent(AnalyticsConstants.EVENT_REGISTER_ATTEMPT, params)
    }

    private fun logRegisterSuccess(email: String) {
        val params = Bundle().apply {
            putString(AnalyticsConstants.PARAM_SCREEN_NAME, SCREEN_REGISTER)
            putString(AnalyticsConstants.PARAM_EMAIL, email)
        }
        analyticRepository.logEvent(AnalyticsConstants.EVENT_REGISTER_SUCCESS, params)
    }

    private fun logRegisterFailure(email: String, errorMessage: String) {
        val params = Bundle().apply {
            putString(AnalyticsConstants.PARAM_SCREEN_NAME, SCREEN_REGISTER)
            putString(AnalyticsConstants.PARAM_EMAIL, email)
            putString(AnalyticsConstants.PARAM_ERROR_MESSAGE, errorMessage)
        }
        analyticRepository.logEvent(AnalyticsConstants.EVENT_REGISTER_FAILURE, params)
    }

    fun onMessageValueChange(value: String) {
        savedStateHandle[MESSAGE] = value
    }

    fun onEmailValueChange(value: String) {
        savedStateHandle[EMAIL] = value
    }

    fun resetUiState() {
        viewModelScope.launch(dispatcherProvider.io) {
            delay(1_500)
            _registerUiState.handleUpdateUiState(UiState.Initiate)
            savedStateHandle[MESSAGE] = ""
        }
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
