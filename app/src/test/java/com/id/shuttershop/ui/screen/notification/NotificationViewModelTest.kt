package com.id.shuttershop.ui.screen.notification

import app.cash.turbine.test
import com.id.domain.notification.FetchNotificationUseCase
import com.id.domain.notification.NotificationModel
import com.id.domain.transaction.ITransactionRepository
import com.id.domain.transaction.ItemStatus
import com.id.domain.transaction.TransactionModel
import com.id.domain.transaction.TransactionStatus
import com.id.domain.utils.ErrorType
import com.id.domain.utils.network_response.NetworkResponse
import com.id.shuttershop.utils.MainDispatcherRule
import com.id.shuttershop.utils.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
 * Date: 17/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class NotificationViewModelTest {

    private lateinit var fetchNotificationUseCase: FetchNotificationUseCase

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val notificationModels = listOf(
        NotificationModel(
            id = "0",
            title = "Transaksi Berhasil",
            subTitle = "Transaksi anda dengan invoice id  sedang di proses oleh penjual. Mohon ditunggu untuk update selanjutnya \uD83E\uDD29!"
        )
    )

    @Mock
    private lateinit var transactionRepository: ITransactionRepository

    @Before
    fun setUp() {
        fetchNotificationUseCase = FetchNotificationUseCase(
            transactionRepository = transactionRepository
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createNotificationVieWModel(): NotificationViewModel =
        NotificationViewModel(
            fetchNotificationUseCase = fetchNotificationUseCase,
            mainDispatcherRule.dispatcherProvider
        )

    @Test
    fun `on fetch notifications success`() = runTest {
        val viewModel = createNotificationVieWModel()

        val returnSuccess = UiState.Success(notificationModels)
        val networkResponse = NetworkResponse.Success(
            listOf(
                TransactionModel(
                    itemName = "Shawn Russo",
                    itemTotal = 5668,
                    itemImageUrl = "https://duckduckgo.com/?q=perpetua",
                    transactionTotal = 7063,
                    transactionStatus = TransactionStatus.FAILED,
                    transactionDate = "sem",
                    itemStatus = ItemStatus.ONE_TYPE_ITEM
                ),
            )
        )

        Mockito.`when`(transactionRepository.fetchTransaction()).thenReturn(networkResponse)
        viewModel.fetchNotifications()
        advanceUntilIdle()

        viewModel.notificationState.test {
            assertEquals(returnSuccess, awaitItem())
        }
    }

    @Test
    fun `on fetch notifications empty error`() = runTest {
        val viewModel = createNotificationVieWModel()

        val returnError = UiState.Error(ErrorType.EmptyDataError)
        val networkResponse = NetworkResponse.EmptyValueError

        Mockito.`when`(transactionRepository.fetchTransaction()).thenReturn(networkResponse)

        viewModel.fetchNotifications()
        advanceUntilIdle()

        viewModel.notificationState.test {
            assertEquals(returnError, awaitItem())
        }
    }

    @Test
    fun `on fetch notification http error`() = runTest {
        val viewModel = createNotificationVieWModel()
        advanceUntilIdle()

        val code = 404
        val errorMessage = "Not Found"

        val returnError = UiState.Error(ErrorType.HTTPError(code, errorMessage))
        val networkResponse = NetworkResponse.HttpError(code, errorMessage)

        Mockito.`when`(transactionRepository.fetchTransaction()).thenReturn(networkResponse)

        viewModel.fetchNotifications()
        advanceUntilIdle()

        viewModel.notificationState.test {
            assertEquals(returnError, awaitItem())
        }
    }

    @Test
    fun `on fetch notification unknown error`() = runTest {
        val viewModel = createNotificationVieWModel()

        val returnError = UiState.Error(ErrorType.UnknownError("null"))
        val networkResponse = NetworkResponse.UnknownError(NullPointerException())

        Mockito.`when`(transactionRepository.fetchTransaction()).thenReturn(networkResponse)

        viewModel.fetchNotifications()
        advanceUntilIdle()

        viewModel.notificationState.test {
            assertEquals(returnError, awaitItem())
        }
    }
}