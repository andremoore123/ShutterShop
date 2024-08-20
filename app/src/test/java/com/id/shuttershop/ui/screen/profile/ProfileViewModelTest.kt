package com.id.shuttershop.ui.screen.profile

import app.cash.turbine.test
import com.id.domain.analytic.IAnalyticRepository
import com.id.domain.auth.LogoutUseCase
import com.id.domain.preference.IPreferenceRepository
import com.id.domain.session.ISessionRepository
import com.id.domain.session.UserModel
import com.id.shuttershop.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by: andre.
 * Date: 19/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class ProfileViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var logoutUseCase: LogoutUseCase

    private val userModel = UserModel(
        name = "Howard Fuller",
        email = "paulette.cannon@example.com"
    )

    @Mock
    private lateinit var preferenceRepository: IPreferenceRepository

    @Mock
    private lateinit var sessionRepository: ISessionRepository

    @Mock
    private lateinit var analyticRepository: IAnalyticRepository

    @Before
    fun setUp() {
        logoutUseCase = Mockito.mock(LogoutUseCase::class.java)
    }

    private fun createVieWModel() = ProfileViewModel(
        preferenceRepository = preferenceRepository,
        sessionRepository = sessionRepository,
        analyticRepository = analyticRepository,
        logoutUseCase = logoutUseCase,
        dispatcherProvider = mainDispatcherRule.dispatcherProvider
    )

    @Test
    fun `fetch is dark mode value`() = runTest {
        Mockito.`when`(preferenceRepository.isDarkMode).thenReturn(flowOf(true))

        val viewModel = createVieWModel()

        viewModel.isDarkMode.test {
            assertEquals(false, awaitItem())
            assertEquals(true, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetch is indonesian language`() = runTest {
        Mockito.`when`(preferenceRepository.isIndonesiaLanguage).thenReturn(flowOf(true))

        val viewModel = createVieWModel()
        viewModel.isIndonesiaLanguage.test {
            assertEquals(false, awaitItem())
            assertEquals(true, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetch user data`() = runTest {
        val viewModel = createVieWModel()
        advanceUntilIdle()

        Mockito.`when`(sessionRepository.fetchUserData()).thenReturn(userModel)

        viewModel.fetchUserData()
        advanceUntilIdle()

        viewModel.userData.test {
            assertEquals(userModel, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `on logout`() = runTest {
        val viewModel = createVieWModel()

        viewModel.logout("test@gmail.com")
        advanceUntilIdle()
        Mockito.verify(logoutUseCase).invoke()
    }

    @Test
    fun `set dark mode`() = runTest {
        val viewModel = createVieWModel()

        viewModel.setDarkMode(true)
        advanceUntilIdle()

        Mockito.verify(preferenceRepository).setDarkMode()
    }

    @Test
    fun `set indonesian language`() = runTest {
        val viewModel = createVieWModel()

        viewModel.setIndonesiaLanguage(true)
        advanceUntilIdle()

        Mockito.verify(preferenceRepository).setLanguage()
    }
}