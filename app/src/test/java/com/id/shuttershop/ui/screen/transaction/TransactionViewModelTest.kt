package com.id.shuttershop.ui.screen.transaction

import app.cash.turbine.test
import com.id.domain.analytic.IAnalyticRepository
import com.id.domain.transaction.FetchTransactionsUseCase
import com.id.domain.transaction.ItemStatus
import com.id.domain.transaction.TransactionModel
import com.id.domain.transaction.TransactionStatus
import com.id.domain.utils.ErrorType
import com.id.domain.utils.resource.Resource
import com.id.shuttershop.utils.MainDispatcherRule
import com.id.shuttershop.utils.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
 * Date: 20/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class TransactionViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var analyticRepository: IAnalyticRepository

    private lateinit var fetchTransactionsUseCase: FetchTransactionsUseCase

    @Before
    fun setUp() {
        fetchTransactionsUseCase = Mockito.mock(FetchTransactionsUseCase::class.java)
    }

    private fun createViewModel() = TransactionViewModel(
        fetchTransactionsUseCase = fetchTransactionsUseCase,
        analyticRepository = analyticRepository,
        dispatcherProvider = mainDispatcherRule.dispatcherProvider
    )

    @Test
    fun `on fetch transaction success`() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        val expectValue = listOf(
            TransactionModel(
                transactionId = "autem",
                itemName = "Toni Carver",
                itemTotal = 8048,
                itemImageUrl = "https://www.google.com/#q=euripidis",
                transactionTotal = 8611,
                transactionStatus = TransactionStatus.FAILED,
                transactionDate = "veritus",
                itemStatus = ItemStatus.ONE_TYPE_ITEM,
                time = "ante",
                review = "senectus",
                rating = 8071,
                paymentName = "Kristi Dunlap"
            ),
            TransactionModel(
                transactionId = "autem",
                itemName = "Toni Carver",
                itemTotal = 8048,
                itemImageUrl = "https://www.google.com/#q=euripidis",
                transactionTotal = 8611,
                transactionStatus = TransactionStatus.FAILED,
                transactionDate = "veritus",
                itemStatus = ItemStatus.ONE_TYPE_ITEM,
                time = "ante",
                review = "senectus",
                rating = 8071,
                paymentName = "Kristi Dunlap"
            ),
        )

        Mockito.`when`(fetchTransactionsUseCase.invoke()).thenReturn(Resource.Success(expectValue))
        viewModel.fetchTransaction()
        advanceUntilIdle()

        viewModel.transactionState.test {
            assertEquals(UiState.Success(expectValue), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `on sort transaction list`() = runTest {
        val viewModel = createViewModel()
        val expectValue = listOf(
            TransactionModel(
                transactionId = "autem",
                itemName = "Toni Carver",
                itemTotal = 8048,
                itemImageUrl = "https://www.google.com/#q=euripidis",
                transactionTotal = 8611,
                transactionStatus = TransactionStatus.FAILED,
                transactionDate = "veritus",
                itemStatus = ItemStatus.ONE_TYPE_ITEM,
                time = "ante",
                review = "senectus",
                rating = 8071,
                paymentName = "Kristi Dunlap"
            ),
            TransactionModel(
                transactionId = "autem",
                itemName = "Toni Carver",
                itemTotal = 8048,
                itemImageUrl = "https://www.google.com/#q=euripidis",
                transactionTotal = 8611,
                transactionStatus = TransactionStatus.FAILED,
                transactionDate = "veritus",
                itemStatus = ItemStatus.ONE_TYPE_ITEM,
                time = "ante",
                review = "senectus",
                rating = 8071,
                paymentName = "Kristi Dunlap"
            ),
        )
        val expectResult = expectValue.sortedBy { it.rating != 0 && it.review.isNotEmpty() }

        val actualResult = viewModel.sortHistoryTransaction(expectValue)
        assertEquals(expectResult, actualResult)
    }

    @Test
    fun `on fetch transaction error`() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        val expectValue = ErrorType.EmptyDataError

        Mockito.`when`(fetchTransactionsUseCase.invoke()).thenReturn(Resource.Error(expectValue))
        viewModel.fetchTransaction()
        advanceUntilIdle()

        viewModel.transactionState.test {
            assertEquals(UiState.Error(expectValue), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

}