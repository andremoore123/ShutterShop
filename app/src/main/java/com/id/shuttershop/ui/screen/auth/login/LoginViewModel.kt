package com.id.shuttershop.ui.screen.auth.login

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.domain.analytic.IAnalyticRepository
import com.id.domain.auth.LoginUseCase
import com.id.domain.utils.resource.onError
import com.id.domain.utils.resource.onSuccess
import com.id.shuttershop.utils.DispatcherProvider
import com.id.shuttershop.utils.UiState
import com.id.shuttershop.utils.analytics.AnalyticsConstants
import com.id.shuttershop.utils.analytics.ScreenConstants.SCREEN_LOGIN
import com.id.shuttershop.utils.handleUpdateUiState
import com.id.shuttershop.utils.validation.ErrorValidation
import com.id.shuttershop.utils.validation.emailValidation
import com.id.shuttershop.utils.validation.isError
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
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val analyticRepository: IAnalyticRepository,
    private val coroutineDispatcher: DispatcherProvider
) : ViewModel() {
    val emailValue: StateFlow<String?> = savedStateHandle.getStateFlow(EMAIL, null)
    val passwordValue: StateFlow<String?> = savedStateHandle.getStateFlow(PASSWORD, null)
    val messageValue = savedStateHandle.getStateFlow(MESSAGE, "")

    val emailValidation: StateFlow<ErrorValidation?> = emailValue.mapLatest {
        it?.emailValidation()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    val passwordValidation: StateFlow<ErrorValidation?> = passwordValue.mapLatest {
        it?.passwordValidation()
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)

    private val _loginUiState = MutableStateFlow<UiState<String>>(UiState.Initiate)
    val loginUiState = _loginUiState.asStateFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch(coroutineDispatcher.io) {
            with(_loginUiState) {
                handleUpdateUiState(UiState.Loading)
                logLoginAttempt(email)
                loginUseCase(email, password).onSuccess { result ->
                    handleUpdateUiState(UiState.Success(result))
                    logLoginSuccess(email)
                }.onError { error ->
                    handleUpdateUiState(UiState.Error(error))
                    logLoginFailure(email, error.errorMessage)
                }
            }
        }
    }

    fun isValidEntries(email: String, password: String): Boolean {
        val emailValidation = email.emailValidation()
        val passwordValidation = password.passwordValidation()
        return emailValidation.isError().not() && passwordValidation.isError().not()
    }

    private fun logLoginAttempt(email: String) {
        val params = Bundle().apply {
            putString(AnalyticsConstants.PARAM_SCREEN_NAME, SCREEN_LOGIN)
            putString(AnalyticsConstants.PARAM_EMAIL, email)
        }
        analyticRepository.logEvent(AnalyticsConstants.EVENT_LOGIN_ATTEMPT, params)
    }

    private fun logLoginSuccess(email: String) {
        val params = Bundle().apply {
            putString(AnalyticsConstants.PARAM_SCREEN_NAME, SCREEN_LOGIN)
            putString(AnalyticsConstants.PARAM_EMAIL, email)
        }
        analyticRepository.logEvent(AnalyticsConstants.EVENT_LOGIN_SUCCESS, params)
    }

    private fun logLoginFailure(email: String, errorMessage: String) {
        val params = Bundle().apply {
            putString(AnalyticsConstants.PARAM_SCREEN_NAME, SCREEN_LOGIN)
            putString(AnalyticsConstants.PARAM_EMAIL, email)
            putString(AnalyticsConstants.PARAM_ERROR_MESSAGE, errorMessage)
        }
        analyticRepository.logEvent(AnalyticsConstants.EVENT_LOGIN_FAILURE, params)
    }

    fun onMessageValueChange(value: String) {
        savedStateHandle[MESSAGE] = value
    }

    fun resetUiState() {
        viewModelScope.launch(coroutineDispatcher.io) {
            delay(1_500)
            _loginUiState.handleUpdateUiState(UiState.Initiate)
            savedStateHandle[MESSAGE] = ""
        }
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
