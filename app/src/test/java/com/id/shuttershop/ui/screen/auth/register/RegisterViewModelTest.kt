package com.id.shuttershop.ui.screen.auth.register

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.id.domain.analytic.IAnalyticRepository
import com.id.domain.auth.RegisterUseCase
import com.id.domain.auth.model.AuthDataModel
import com.id.domain.session.UserModel
import com.id.domain.utils.ErrorType
import com.id.domain.utils.resource.Resource
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
class RegisterViewModelTest {
    private val userModel = UserModel(
        name = "Lucille Day", email = "maura.rhodes@example.com"
    )
    private val userPassword = "Test123@."

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var analyticRepository: IAnalyticRepository

    private lateinit var registerUseCase: RegisterUseCase

    @Before
    fun setUp() {
        registerUseCase = Mockito.mock(RegisterUseCase::class.java)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createRegisterViewModel() = RegisterViewModel(
        registerUseCase = registerUseCase,
        savedStateHandle = SavedStateHandle(),
        analyticRepository = analyticRepository,
        dispatcherProvider = mainDispatcherRule.dispatcherProvider
    )

    @Test
    fun `on register success`() = runTest {
        val viewModel = createRegisterViewModel()
        val name = userModel.name
        val email = userModel.email
        val password = userPassword

        val authDataModel = AuthDataModel(
            userName = name,
            userImageUrl = "http://www.bing.com/search?q=liber",
            accessToken = "ius",
            refreshToken = "ac"
        )

        val returnSuccess = Resource.Success(authDataModel.userName)
        val uiStateSuccess = UiState.Success(authDataModel.userName)

        Mockito.`when`(registerUseCase.invoke(name, email, password)).thenReturn(returnSuccess)

        viewModel.register(name, email, password)
        advanceUntilIdle()
        viewModel.registerUiState.test {
            assertEquals(uiStateSuccess, awaitItem())
        }
    }

    @Test
    fun `on register error`() = runTest {
        val viewModel = createRegisterViewModel()

        val name = userModel.name
        val email = userModel.email
        val password = userPassword

        val returnError = Resource.Error(ErrorType.UnknownError("null"))
        val uiStateError = UiState.Error(ErrorType.UnknownError("null"))

        Mockito.`when`(registerUseCase.invoke(name, email, password)).thenReturn(returnError)

        viewModel.register(name, email, password)
        advanceUntilIdle()
        viewModel.registerUiState.test {
            assertEquals(uiStateError, awaitItem())
        }
    }

    @Test
    fun `on update email`() = runTest {
        val viewModel = createRegisterViewModel()

        val email = userModel.email

        viewModel.onEmailValueChange(email)
        viewModel.emailValue.test {
            assertEquals(email, awaitItem())
        }
    }

    @Test
    fun `on update password`() = runTest {
        val viewModel = createRegisterViewModel()

        val password = userPassword

        viewModel.onPasswordChange(password)
        viewModel.passwordValue.test {
            assertEquals(password, awaitItem())
        }
    }

    @Test
    fun `on update name`() = runTest {
        val viewModel = createRegisterViewModel()
        val name = userModel.name

        viewModel.onNameChange(name)
        viewModel.nameValue.test {
            assertEquals(name, awaitItem())
        }
    }

    @Test
    fun `on update message`() = runTest {
        val viewModel = createRegisterViewModel()
        val message = "Test 123"

        viewModel.onMessageValueChange(message)

        viewModel.messageValue.test {
            assertEquals(message, awaitItem())
        }
    }

    @Test
    fun `reset ui state after register error`() = runTest {
        val viewModel = createRegisterViewModel()

        val name = userModel.name
        val email = userModel.email
        val password = userPassword

        val returnError = Resource.Error(ErrorType.UnknownError("null"))
        val uiStateError = UiState.Error(ErrorType.UnknownError("null"))

        Mockito.`when`(registerUseCase.invoke(name, email, password)).thenReturn(returnError)

        viewModel.register(name, email, password)
        advanceUntilIdle()
        viewModel.registerUiState.test {
            assertEquals(uiStateError, awaitItem())
            viewModel.resetUiState()
            assertEquals(UiState.Initiate, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}