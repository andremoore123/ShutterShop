package com.id.shuttershop.ui.screen

import app.cash.turbine.test
import com.id.domain.preference.IPreferenceRepository
import com.id.domain.session.ISessionRepository
import com.id.shuttershop.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import org.junit.Assert.assertEquals

/**
 * Created by: andre.
 * Date: 19/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class MainContainerViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var preferenceRepository: IPreferenceRepository

    @Mock
    private lateinit var sessionRepository: ISessionRepository
    
    private fun createViewModel() = MainContainerViewModel(
        preferenceRepository = preferenceRepository,
        sessionRepository = sessionRepository,
        dispatcherProvider = mainDispatcherRule.dispatcherProvider
    )

    @Test
    fun `fetch is onboard showed`() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        Mockito.`when`(preferenceRepository.isOnboardShow()).thenReturn(true)

        viewModel.fetchIsOnboardShowed()
        advanceUntilIdle()

        viewModel.isOnboardShowed.test {
            assertEquals(true, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetch is user login`() = runTest {
        Mockito.`when`(sessionRepository.isUserLogin()).thenReturn(flowOf(true))

        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.isUserLogin.test {
            assertEquals(false, awaitItem())
            assertEquals(true, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}