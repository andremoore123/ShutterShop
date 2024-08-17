package com.id.shuttershop.ui.screen.auth.register

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import app.cash.turbine.turbineScope
import com.id.domain.analytic.IAnalyticRepository
import com.id.domain.auth.IAuthRepository
import com.id.domain.auth.RegisterUseCase
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
import org.junit.Assert.*
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
class RegisterViewModelTest {
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

    private lateinit var vieWModel: RegisterViewModel
    
    @Before
    fun setUp() {
        Dispatchers.setMain(Dispatchers.Unconfined)
        val registerUseCase = RegisterUseCase(
            authRepository = authRepository, 
            sessionRepository = sessionRepository
        )
        
        vieWModel = RegisterViewModel(
            registerUseCase = registerUseCase,
            savedStateHandle = SavedStateHandle(),
            analyticRepository = analyticRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `on register success`() = runTest {
        turbineScope {
            val name = userModel.name
            val email = userModel.email
            val password = userPassword

            val authDataModel = AuthDataModel(
                userName = name,
                userImageUrl = "http://www.bing.com/search?q=liber",
                accessToken = "ius",
                refreshToken = "ac"
            )

            val returnSuccess = NetworkResponse.Success(authDataModel)
            val uiStateSuccess = UiState.Success(authDataModel.userName)

            Mockito.`when`(authRepository.register(name, email, password)).thenReturn(returnSuccess)

            vieWModel.register(name, email, password)
            vieWModel.registerUiState.test {
                assertEquals(UiState.Loading, awaitItem())
                assertEquals(uiStateSuccess, awaitItem())
            }
        }
    }

    @Test
    fun `on register error`() = runTest {
        turbineScope {
            val name = userModel.name
            val email = userModel.email
            val password = userPassword

            val returnError = NetworkResponse.UnknownError(NullPointerException())
            val uiStateError = UiState.Error(ErrorType.UnknownError("null"))

            Mockito.`when`(authRepository.register(name, email, password)).thenReturn(returnError)

            vieWModel.register(name, email, password)
            vieWModel.registerUiState.test {
                assertEquals(UiState.Initiate, awaitItem())
                assertEquals(UiState.Loading, awaitItem())
                assertEquals(uiStateError, awaitItem())
            }
        }
    }

    @Test
    fun `on update email`() = runTest {
        turbineScope {
            val email = userModel.email

            vieWModel.onEmailValueChange(email)
            vieWModel.emailValue.test {
                assertEquals(email, awaitItem())
            }
        }
    }

    @Test
    fun `on update password`() = runTest {
        turbineScope {
            val password = userPassword

            vieWModel.onPasswordChange(password)
            vieWModel.passwordValue.test {
                assertEquals(password, awaitItem())
            }
        }
    }

    @Test
    fun `on update name`() = runTest {
        turbineScope {
            val name = userModel.name

            vieWModel.onNameChange(name)
            vieWModel.nameValue.test {
                assertEquals(name, awaitItem())
            }
        }
    }

    @Test
    fun `on update message`() = runTest {
        turbineScope {
            val message = "Test 123"

            vieWModel.onMessageValueChange(message)

            vieWModel.messageValue.test {
                assertEquals(message, awaitItem())
            }
        }
    }
}