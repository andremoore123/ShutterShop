package com.id.shuttershop.ui.screen.search

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.id.domain.product.IProductRepository
import com.id.shuttershop.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

/**
 * Created by: andre.
 * Date: 19/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var productRepository: IProductRepository

    private fun createSearchViewModel() = SearchViewModel(
        productRepository = productRepository,
        savedStateHandle = SavedStateHandle(),
        dispatcherProvider = mainDispatcherRule.dispatcherProvider
    )

    @Test
    fun `set message value`() = runTest() {
        val viewModel = createSearchViewModel()

        val expectedMessage = "Test 123"

        viewModel.setMessageValue(expectedMessage)
        viewModel.messageValue.test {
            assertEquals("", awaitItem())
        }
    }


    @Test
    fun `on search value change`() = runTest {
        val viewModel = createSearchViewModel()
        advanceUntilIdle()

        val expectedMessage = "Test 123"

        viewModel.onSearchValueChange(expectedMessage)

        viewModel.searchValue.test {
            assertEquals(expectedMessage, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}