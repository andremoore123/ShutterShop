package com.id.shuttershop.ui.screen.search

import androidx.lifecycle.SavedStateHandle
import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import app.cash.turbine.test
import com.id.domain.product.IProductRepository
import com.id.domain.product.ProductModel
import com.id.shuttershop.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Ignore
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


    @Ignore("Currently Not Understand How to Test Paging")
    @Test
    fun `on fetch search success`() = runTest {
        val viewModel = createSearchViewModel()

        val expectedList = listOf(
            ProductModel(
                id = "quis",
                itemName = "Keisha Perkins",
                itemSold = 8584,
                itemPrice = 5006,
                itemRating = "noluisse",
                itemSeller = "fringilla",
                imageUrl = "https://www.google.com/#q=postulant"
            ),
            ProductModel(
                id = "quis2",
                itemName = "Keisha Perkins 2",
                itemSold = 8584,
                itemPrice = 5006,
                itemRating = "noluisse",
                itemSeller = "fringilla",
                imageUrl = "https://www.google.com/#q=postulant"
            )
        )
        val expectedData = PagingData.from(expectedList)
        val expectedFlow = flowOf(expectedData)

        val searchQuery = "Test 123"
        Mockito.`when`(productRepository.searchProduct(searchQuery))
            .thenReturn(expectedFlow)

        viewModel.fetchSearch(searchQuery)
        advanceUntilIdle()

        assertEquals(expectedList, viewModel.searchData.asSnapshot { scrollTo(3) })

    }


    @Ignore("Currently Not Understand How to Test Paging")
    @Test
    fun `on fetch search empty`() = runTest {
        val viewModel = createSearchViewModel()

        val expectedData = PagingData.from(emptyList<ProductModel>())
        val expectedFlow = flowOf(expectedData)

        val searchQuery = "Test 123"
        Mockito.`when`(productRepository.searchProduct(searchQuery))
            .thenReturn(expectedFlow)

        viewModel.fetchSearch(searchQuery)
        advanceUntilIdle()

        assertEquals(expectedFlow.asSnapshot(), viewModel.searchData.asSnapshot())

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