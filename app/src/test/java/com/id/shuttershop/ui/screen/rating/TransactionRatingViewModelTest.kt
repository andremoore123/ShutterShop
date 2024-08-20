package com.id.shuttershop.ui.screen.rating

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.id.domain.rating.IRatingRepository
import com.id.domain.utils.ErrorType
import com.id.domain.utils.resource.Resource
import com.id.shuttershop.utils.MainDispatcherRule
import com.id.shuttershop.utils.UiState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
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
class TransactionRatingViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var ratingRepository: IRatingRepository

    private fun createTransactionRatingViewModel() = TransactionRatingViewModel(
        ratingRepository = ratingRepository,
        savedStateHandle = SavedStateHandle(),
        dispatcherProvider = mainDispatcherRule.dispatcherProvider
    )

    @Test
    fun `on send rating success`() = runTest {
        val viewModel = createTransactionRatingViewModel()
        advanceUntilIdle()

        val invoiceId = "abcd"
        val rating = 3
        val review = "test 123"

        Mockito.`when`(ratingRepository.insertRating(
            invoiceId = invoiceId,
            rating = rating,
            review = review
        )).thenReturn(Resource.Success(true))

        viewModel.sendRating(invoiceId = invoiceId, rating = rating, review = review)
        advanceUntilIdle()

        viewModel.reviewState.test {
            assertEquals(UiState.Success(true), awaitItem())
        }
    }

    @Test
    fun `on send rating error`() = runTest {
        val viewModel = createTransactionRatingViewModel()
        advanceUntilIdle()

        val invoiceId = "abcd"
        val rating = 3
        val review = "test 123"

        Mockito.`when`(ratingRepository.insertRating(
            invoiceId = invoiceId,
            rating = rating,
            review = review
        )).thenReturn(Resource.Error(ErrorType.EmptyDataError))

        viewModel.sendRating(invoiceId = invoiceId, rating = rating, review = review)
        advanceUntilIdle()

        viewModel.reviewState.test {
            assertEquals(UiState.Error(ErrorType.EmptyDataError), awaitItem())
        }
    }

    @Test
    fun `update rating`() = runTest {
        val viewModel = createTransactionRatingViewModel()
        advanceUntilIdle()

        val expectedResult = 4
        viewModel.updateRating(expectedResult)

        viewModel.rating.test {
            assertEquals(expectedResult, awaitItem())
        }
    }

    @Test
    fun `update review`() = runTest {
        val viewModel = createTransactionRatingViewModel()
        advanceUntilIdle()

        val expectedResult = "Test 123"
        viewModel.updateReview(expectedResult)

        viewModel.review.test {
            assertEquals(expectedResult, awaitItem())
        }
    }

    @Test
    fun `update message`() = runTest {
        val viewModel = createTransactionRatingViewModel()
        advanceUntilIdle()

        val expectedResult = "Test 123"
        viewModel.updateMessage(expectedResult)

        viewModel.message.test {
            assertEquals("", awaitItem())
        }
    }
}