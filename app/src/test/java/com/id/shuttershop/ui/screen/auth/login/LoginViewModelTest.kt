package com.id.shuttershop.ui.screen.auth.login

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.id.domain.analytic.IAnalyticRepository
import com.id.domain.auth.IAuthRepository
import com.id.domain.auth.LoginUseCase
import com.id.domain.auth.model.AuthDataModel
import com.id.domain.session.ISessionRepository
import com.id.domain.session.UserModel
import com.id.domain.utils.ErrorType
import com.id.domain.utils.network_response.NetworkResponse
import com.id.shuttershop.utils.MainDispatcherRule
import com.id.shuttershop.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner


/**
 * Created by: andre.
 * Date: 17/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {
    private val userModel = UserModel(
        name = "Lucille Day", email = "maura.rhodes@example.com"
    )
    private val userPassword = "Test123@."

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var sessionRepository: ISessionRepository

    @Mock
    private lateinit var authRepository: IAuthRepository

    @Mock
    private lateinit var analyticRepository: IAnalyticRepository

    private lateinit var loginUseCase: LoginUseCase

    @Before
    fun setUp() {
        loginUseCase = LoginUseCase(
            sessionRepository = sessionRepository,
            authRepository = authRepository
        )
    }

    private fun createLoginViewModel(): LoginViewModel = LoginViewModel(
        loginUseCase = loginUseCase,
        savedStateHandle = SavedStateHandle(),
        analyticRepository = analyticRepository,
        coroutineDispatcher = mainDispatcherRule.dispatcherProvider
    )

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `on login success`() = runTest {
        val viewModel = createLoginViewModel()

        val email = userModel.email
        val password = userPassword
        val authDataModel = AuthDataModel(
            userName = "Pam Bowman",
            userImageUrl = "http://www.bing.com/search?q=liber",
            accessToken = "ius",
            refreshToken = "ac"
        )
        val returnSuccess = NetworkResponse.Success(authDataModel)
        val uiStateSuccess = UiState.Success(authDataModel.userName)

        Mockito.`when`(authRepository.login(email, password)).thenReturn(returnSuccess)

        viewModel.login(email, password)
        viewModel.loginUiState.test {
            assertEquals(UiState.Initiate, awaitItem())
            assertEquals(UiState.Loading, awaitItem())
            assertEquals(uiStateSuccess, awaitItem())
        }
    }

    @Test
    fun `on login failure`() = runTest {
        val viewModel = createLoginViewModel()
        val email = userModel.email
        val password = ""

        val uiStateError = UiState.Error(ErrorType.UnknownError("null"))
        val returnError = NetworkResponse.UnknownError(NullPointerException())

        Mockito.`when`(authRepository.login(email, password)).thenReturn(returnError)

        viewModel.login(email, password)
        advanceUntilIdle()

        viewModel.loginUiState.test {
            assertEquals(uiStateError, awaitItem())
        }

    }

    @Test
    fun `on update email`() = runTest {
        val viewModel = createLoginViewModel()
        val email = userModel.email

        viewModel.onEmailValueChange(email)
        viewModel.emailValue.test {
            assertEquals(email, awaitItem())
        }

    }

    @Test
    fun `on update password`() = runTest {
        val viewModel = createLoginViewModel()
        val password = userPassword

        viewModel.onPasswordChange(password)
        viewModel.passwordValue.test {
            assertEquals(password, awaitItem())
        }

    }

    @Test
    fun `on update message`() = runTest {
        val viewModel = createLoginViewModel()
        val message = "Test 123"

        viewModel.onMessageValueChange(message)

        viewModel.messageValue.test {
            assertEquals(message, awaitItem())
        }

    }
}