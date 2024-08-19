package com.id.shuttershop

import app.cash.turbine.test
import com.id.domain.preference.IPreferenceRepository
import com.id.shuttershop.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
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
class MainViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var preferenceRepository: IPreferenceRepository

    private fun createViewModel() = MainViewModel(preferenceRepository = preferenceRepository)

    @Test
    fun `fetch dark mode status`() = runTest {
        Mockito.`when`(preferenceRepository.isDarkMode).thenReturn(flowOf(true))

        val viewModel = createViewModel()

        viewModel.isDarkMode.test {
            assertEquals(false, awaitItem())
            assertEquals(true, awaitItem())
        }
    }

    @Test
    fun `fetch indonesian language status`() = runTest {
        Mockito.`when`(preferenceRepository.isIndonesiaLanguage).thenReturn(flowOf(true))

        val viewModel = createViewModel()

        viewModel.isIndonesia.test {
            assertEquals(false, awaitItem())
            assertEquals(true, awaitItem())
        }
    }
}