package com.id.shuttershop.ui.screen.wishlist

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.id.domain.analytic.IAnalyticRepository
import com.id.domain.cart.AddToCartUseCase
import com.id.domain.wishlist.IWishlistRepository
import com.id.domain.wishlist.WishlistModel
import com.id.shuttershop.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
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
 * Date: 19/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class WishlistViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()
    private lateinit var addToCartUseCase: AddToCartUseCase
    private val savedStateHandle = SavedStateHandle()

    private val dummyWishlist = WishlistModel(
        wishlistId = null,
        itemId = "ex",
        itemName = "Jeanne Garrison",
        itemSold = "fermentum",
        itemVariantName = "Marylou Shaw",
        itemPrice = 8488,
        itemRating = "posidonium",
        itemSeller = "discere",
        itemImageUrl = "https://www.google.com/#q=solum"
    )

    @Mock
    private lateinit var wishlistRepository: IWishlistRepository

    @Mock
    private lateinit var analyticRepository: IAnalyticRepository

    @Before
    fun setUp() {
        addToCartUseCase = Mockito.mock(AddToCartUseCase::class.java)
    }

    private fun createViewModel() = WishlistViewModel(
        wishlistRepository = wishlistRepository,
        analyticRepository = analyticRepository,
        addToCartUseCase = addToCartUseCase,
        savedStateHandle = savedStateHandle,
        dispatcherProvider = mainDispatcherRule.dispatcherProvider
    )

    @Test
    fun `on fetch wishlist`() = runTest {
        // Given
        val expectedValue = listOf(
            WishlistModel(
                wishlistId = null,
                itemId = "utroque",
                itemName = "Lamont Solis",
                itemSold = "sed",
                itemVariantName = "Micheal Morris",
                itemPrice = 5559,
                itemRating = "nostra",
                itemSeller = "tamquam",
                itemImageUrl = "http://www.bing.com/search?q=sanctus"
            ),
            WishlistModel(
                wishlistId = null,
                itemId = "utroque",
                itemName = "Lamont Solis",
                itemSold = "sed",
                itemVariantName = "Micheal Morris",
                itemPrice = 5559,
                itemRating = "nostra",
                itemSeller = "tamquam",
                itemImageUrl = "http://www.bing.com/search?q=sanctus"
            )
        )

        // When
        Mockito.`when`(wishlistRepository.fetchWishlists()).thenReturn(flowOf(expectedValue))

        // Create ViewModel
        val viewModel = createViewModel()

        // Then
        viewModel.wishlists.test {
            // First item will be the initialValue
            assertEquals(emptyList<WishlistModel>(), awaitItem())
            // Second item will be the expected value
            assertEquals(expectedValue, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `on delete wishlist`() = runTest {


        val viewModel = createViewModel()

        viewModel.deleteWishlist(dummyWishlist)
        advanceUntilIdle()
        Mockito.verify(wishlistRepository).removeWishlist(data = dummyWishlist)
    }

    @Test
    fun `on add to cart`() = runTest {
        val viewModel = createViewModel()

        viewModel.addToCart(dummyWishlist)
        advanceUntilIdle()
        Mockito.verify(addToCartUseCase).invoke(dummyWishlist)
    }

    @Test
    fun `on update message`() = runTest {
        val viewModel = createViewModel()

        val expectedValue = "Test123"
        viewModel.updateMessage(expectedValue)
        advanceUntilIdle()

        viewModel.message.test {
            assertEquals(null, awaitItem())
        }
    }
}