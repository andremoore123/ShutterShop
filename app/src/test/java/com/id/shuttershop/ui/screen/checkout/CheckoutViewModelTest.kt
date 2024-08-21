package com.id.shuttershop.ui.screen.checkout

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.id.domain.cart.CartModel
import com.id.domain.payment.IPaymentRepository
import com.id.domain.payment.PaymentModel
import com.id.domain.payment.PaymentType
import com.id.domain.transaction.CheckoutModel
import com.id.domain.transaction.PayUseCase
import com.id.domain.utils.ErrorType
import com.id.domain.utils.formatToRupiah
import com.id.domain.utils.resource.Resource
import com.id.shuttershop.utils.MainDispatcherRule
import com.id.shuttershop.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
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
 * Date: 19/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class CheckoutViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var payUseCase: PayUseCase

    private val cartListDummy = listOf(
        CartModel(
            cartId = null,
            itemId = "nonumes",
            itemName = "Loretta Walton",
            itemPrice = 9731,
            itemStock = 1740,
            itemCount = 6751,
            itemVariantName = "Delores Wilkinson",
            imageUrl = "http://www.bing.com/search?q=falli"
        ),
        CartModel(
            cartId = null,
            itemId = "nonumes",
            itemName = "Loretta Walton",
            itemPrice = 9731,
            itemStock = 1740,
            itemCount = 6751,
            itemVariantName = "Delores Wilkinson",
            imageUrl = "http://www.bing.com/search?q=falli"
        )
    )

    private val paymentModel = PaymentModel(
        idPayment = 5500,
        paymentName = "Bert Emerson",
        paymentImageUrl = "https://www.google.com/#q=tantas",
        paymentType = PaymentType.INSTANT_PAYMENT,
        paymentStatus = false
    )

    @Mock
    private lateinit var paymentRepository: IPaymentRepository

    @Before
    fun setUp() {
        payUseCase = Mockito.mock(PayUseCase::class.java)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createCheckoutVieWModel() = CheckoutViewModel(
        paymentRepository = paymentRepository,
        savedStateHandle = SavedStateHandle(),
        payUseCase = payUseCase,
        dispatcherProvider = mainDispatcherRule.dispatcherProvider
    )

    @Test
    fun `fetch payment methods`() = runTest {
        Mockito.`when`(paymentRepository.fetchPaymentMethods())
            .thenReturn(flowOf(listOf(paymentModel)))
        val viewModel = createCheckoutVieWModel()
        advanceUntilIdle()

        viewModel.paymentMethods.test {
            assertEquals(emptyList<PaymentModel>(), awaitItem())
            assertEquals(listOf(paymentModel), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

    }

    @Test
    fun `on payment success`() = runTest {
        val viewModel = createCheckoutVieWModel()
        advanceUntilIdle()

        val checkoutModel = CheckoutModel(
            invoiceId = "sea",
            date = "adolescens",
            time = "disputationi",
            paymentName = "Neil Casey",
            total = 8887
        )

        Mockito.`when`(payUseCase.invoke(cartListDummy, paymentModel))
            .thenReturn(Resource.Success(checkoutModel))

        viewModel.payItem(cartListDummy, paymentModel)
        advanceUntilIdle()
        viewModel.paymentState.test {
            assertEquals(UiState.Success(checkoutModel), awaitItem())
        }
    }

    @Test
    fun `on payment error`() = runTest {
        val viewModel = createCheckoutVieWModel()
        advanceUntilIdle()

        val expectedReturn = Resource.Error(ErrorType.EmptyDataError)

        Mockito.`when`(payUseCase.invoke(cartListDummy, paymentModel)).thenReturn(expectedReturn)

        viewModel.payItem(cartListDummy, paymentModel)
        advanceUntilIdle()

        viewModel.paymentState.test {
            assertEquals(UiState.Error(expectedReturn.errorType), awaitItem())
        }
    }

    @Test
    fun `calculate price`() = runTest {
        val viewModel = createCheckoutVieWModel()
        advanceUntilIdle()

        val totalValue = cartListDummy.sumOf { it.itemCount * it.itemPrice }

        val expectedReturn = totalValue.formatToRupiah()

        val functionReturn = viewModel.calculateTotalPrice(cartListDummy)

        assertEquals(expectedReturn, functionReturn)
    }

    @Test
    fun `on modify sheet value`() = runTest {
        val viewModel = createCheckoutVieWModel()
        advanceUntilIdle()

        val sheetValue = false

        viewModel.modifySheetValue(sheetValue)
        viewModel.isBottomShowValue.test {
            assertEquals(sheetValue, awaitItem())
        }
    }

    @Test
    fun `on modify select payment`() = runTest {
        val viewModel = createCheckoutVieWModel()
        advanceUntilIdle()

        viewModel.modifySelectPayment(paymentModel)

        viewModel.selectedPaymentValue.test {
            assertEquals(paymentModel, awaitItem())
        }
    }
}