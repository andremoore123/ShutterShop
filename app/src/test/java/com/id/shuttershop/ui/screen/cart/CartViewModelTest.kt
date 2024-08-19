package com.id.shuttershop.ui.screen.cart

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import app.cash.turbine.test
import app.cash.turbine.testIn
import app.cash.turbine.turbineScope
import com.id.domain.cart.CartModel
import com.id.domain.cart.ICartRepository
import com.id.domain.cart.UpdateCartStockUseCase
import com.id.domain.product.IProductRepository
import com.id.domain.product.ProductDetailModel
import com.id.domain.utils.network_response.NetworkResponse
import com.id.domain.utils.resource.Resource
import com.id.shuttershop.utils.MainDispatcherRule
import com.id.shuttershop.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
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
class CartViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var updateCartStockUseCase: UpdateCartStockUseCase

    @Mock
    private lateinit var cartRepository: ICartRepository

    @Mock
    private lateinit var productRepository: IProductRepository

    @Before
    fun setUp() {
        updateCartStockUseCase = UpdateCartStockUseCase(
            cartRepository = cartRepository,
            productRepository = productRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createCartViewModel() = CartViewModel(
        cartRepository = cartRepository,
        updateCartStockUseCase = updateCartStockUseCase,
        savedStateHandle = SavedStateHandle(),
        dispatcherProvider = mainDispatcherRule.dispatcherProvider
    )

    @Test
    fun onAddCartQuantity() = runTest {
        val viewModel = createCartViewModel()

        val cartModel = CartModel(
            cartId = null,
            itemId = "affert",
            itemName = "Lois Everett",
            itemPrice = 1344,
            itemStock = 2344,
            itemCount = 1,
            itemVariantName = "Michael Henson",
            imageUrl = "http://www.bing.com/search?q=expetendis"
        )

        // Expected model after increment
        val updatedCartModel = cartModel.copy(itemCount = cartModel.itemCount + 1)


        // Call the method under test
        viewModel.addCartQuantity(cartModel)

        // Ensure all coroutines have completed
        advanceUntilIdle()

        // Verify that updateCart was called with the correct updated model
        Mockito.verify(cartRepository).updateCart(updatedCartModel)

        // Additional assertion to ensure the count was updated as expected
        assertEquals(cartModel.itemCount + 1, updatedCartModel.itemCount)
    }

    @Test
    fun onReduceCartQuantity() = runTest {
        val viewModel = createCartViewModel()

        val cartModel = CartModel(
            cartId = null,
            itemId = "affert",
            itemName = "Lois Everett",
            itemPrice = 1344,
            itemStock = 2344,
            itemCount = 4,
            itemVariantName = "Michael Henson",
            imageUrl = "http://www.bing.com/search?q=expetendis"
        )

        // Expected model after increment
        val updatedCartModel = cartModel.copy(itemCount = cartModel.itemCount - 1)


        // Call the method under test
        viewModel.reduceCartQuantity(cartModel)

        // Ensure all coroutines have completed
        advanceUntilIdle()

        // Verify that updateCart was called with the correct updated model
        Mockito.verify(cartRepository).updateCart(updatedCartModel)

        // Additional assertion to ensure the count was updated as expected
        assertEquals(cartModel.itemCount - 1, updatedCartModel.itemCount)
    }

    @Test
    fun onUpdateTotalPayment() = runTest {
        val viewModel = createCartViewModel()

        val totalPayment = 230000
        viewModel.updateTotalPayment(totalPayment)
        advanceUntilIdle()

        viewModel.totalPaymentValue.test {
            assertEquals(totalPayment, awaitItem())
        }
    }

    @Test
    fun onHandleSelectCart() = runTest {
        val viewModel = createCartViewModel()

        val cartId = 123
        val cartModel = CartModel(
            cartId = 123,
            itemId = "nostra",
            itemName = "Santiago Garner",
            itemPrice = 3104,
            itemStock = 6956,
            itemCount = 2383,
            itemVariantName = "Grace Whitley",
            imageUrl = "https://duckduckgo.com/?q=quas"
        )

        viewModel.onSelectCart(true, cartModel)
        advanceUntilIdle()

        // On Add Triggered
        viewModel.selectedCart.test {
            assert(awaitItem().contains(cartId))
        }

        viewModel.onSelectCart(false, cartModel)
        advanceUntilIdle()

        // On False Triggered
        viewModel.selectedCart.test {
            assert(awaitItem().contains(cartId).not())
        }
    }

    @Test
    fun `is AllCartSelected Return Null`() = runTest {
        val viewModel = createCartViewModel()
        assertEquals(null, viewModel.isAllCartSelected())
    }
}