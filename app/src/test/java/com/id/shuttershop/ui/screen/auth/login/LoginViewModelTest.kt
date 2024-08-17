package com.id.shuttershop.ui.screen.auth.login

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import app.cash.turbine.turbineScope
import com.id.domain.analytic.IAnalyticRepository
import com.id.domain.auth.IAuthRepository
import com.id.domain.auth.LoginUseCase
import com.id.domain.auth.model.AuthDataModel
import com.id.domain.session.ISessionRepository
import com.id.domain.session.UserModel
import com.id.domain.utils.ErrorType
import com.id.domain.utils.network_response.NetworkResponse
import com.id.shuttershop.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
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

    @Mock
    private lateinit var sessionRepository: ISessionRepository

    @Mock
    private lateinit var authRepository: IAuthRepository

    @Mock
    private lateinit var analyticRepository: IAnalyticRepository

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        val loginUseCase = LoginUseCase(
            sessionRepository = sessionRepository,
            authRepository = authRepository
        )
        viewModel = LoginViewModel(
            loginUseCase = loginUseCase,
            savedStateHandle = SavedStateHandle(),
            analyticRepository = analyticRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `on login success`() = runTest {
        turbineScope {
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
    }

    @Test
    fun `on login failure`() = runTest {
        turbineScope {
            val email = userModel.email
            val password = ""

            val uiStateError = UiState.Error(ErrorType.UnknownError("null"))
            val returnError = NetworkResponse.UnknownError(NullPointerException())

            Mockito.`when`(authRepository.login(email, password)).thenReturn(returnError)

            viewModel.login(email, password)
            viewModel.loginUiState.test {
                assertEquals(uiStateError, awaitItem())
            }
        }
    }

    @Test
    fun `on update email`() = runTest {
        turbineScope {
            val email = userModel.email

            viewModel.onEmailValueChange(email)
            viewModel.emailValue.test {
                assertEquals(email, awaitItem())
            }
        }
    }

    @Test
    fun `on update password`() = runTest {
        turbineScope {
            val password = userPassword

            viewModel.onPasswordChange(password)
            viewModel.passwordValue.test {
                assertEquals(password, awaitItem())
            }
        }
    }

    @Test
    fun `on update message`() = runTest {
        turbineScope {
            val message = "Test 123"

            viewModel.onMessageValueChange(message)

            viewModel.messageValue.test {
                assertEquals(message, awaitItem())
            }
        }
    }
}