package com.id.shuttershop.ui.screen.home

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.id.domain.analytic.IAnalyticRepository
import com.id.domain.product.IProductRepository
import com.id.domain.product.ProductFilterParams
import com.id.domain.session.ISessionRepository
import com.id.domain.session.UserModel
import com.id.shuttershop.ui.screen.home.HomeViewModel.Companion.COLUMN_LAYOUT
import com.id.shuttershop.ui.screen.home.HomeViewModel.Companion.GRID_LAYOUT
import com.id.shuttershop.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
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
class HomeViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val userData = UserModel(name = "Phil Cannon", email = "sonja.richardson@example.com")

    @Mock
    private lateinit var productRepository: IProductRepository

    @Mock
    private lateinit var sessionRepository: ISessionRepository

    @Mock
    private lateinit var analyticRepository: IAnalyticRepository

    private fun createVieWModel() = HomeViewModel(
        productRepository = productRepository,
        sessionRepository = sessionRepository,
        analyticRepository = analyticRepository,
        savedStateHandle = SavedStateHandle(),
        dispatcherProvider = mainDispatcherRule.dispatcherProvider
    )

    @Test
    fun `on set layout type grid`() = runTest {
        val viewModel = createVieWModel()
        advanceUntilIdle()

        val expectValue = COLUMN_LAYOUT
        viewModel.setLayoutType(GRID_LAYOUT)
        advanceUntilIdle()

        viewModel.isColumnLayout.test {
            assertEquals(expectValue, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `on set layout type column`() = runTest {
        val viewModel = createVieWModel()
        advanceUntilIdle()

        val expectValue = GRID_LAYOUT
        viewModel.setLayoutType(COLUMN_LAYOUT)
        advanceUntilIdle()

        viewModel.isColumnLayout.test {
            assertEquals(expectValue, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `on modify filter params`() = runTest {
        val viewModel = createVieWModel()
        advanceUntilIdle()

        val expectValue = ProductFilterParams(
            sortBy = null,
            productCategory = null,
            lowestPrice = null,
            highestPrice = null
        )

        viewModel.onFilterChange(expectValue)
        advanceUntilIdle()

        viewModel.productFilter.test {
            assertEquals(expectValue, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `on modify sheet value false`() = runTest {
        val viewModel = createVieWModel()
        advanceUntilIdle()

        val expectValue = false
        viewModel.modifySheetValue(expectValue)
        advanceUntilIdle()

        viewModel.isBottomShowValue.test {
            assertEquals(expectValue, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `on modify sheet value true`() = runTest {
        val viewModel = createVieWModel()
        advanceUntilIdle()

        val expectValue = true
        viewModel.modifySheetValue(expectValue)
        advanceUntilIdle()

        viewModel.isBottomShowValue.test {
            assertEquals(expectValue, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `on fetch user data`() = runTest {
        val viewModel = createVieWModel()
        advanceUntilIdle()
        val expectReturn = userData
        Mockito.`when`(sessionRepository.fetchUserData()).thenReturn(expectReturn)

        viewModel.fetchUserData()
        advanceUntilIdle()

        viewModel.userData.test {
            assertEquals(expectReturn, awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `on product filter initiate`() = runTest {
        val viewModel = createVieWModel()
        advanceUntilIdle()

        viewModel.productFilter.test {
            assertEquals(ProductFilterParams(), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `on user data initiate`() = runTest {
        val viewModel = createVieWModel()
        advanceUntilIdle()

        viewModel.userData.test {
            assertEquals(UserModel.emptyModel, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `on show bottom sheet value initiate`() = runTest {
        val viewModel = createVieWModel()
        advanceUntilIdle()

        viewModel.isBottomShowValue.test {
            assertEquals(false, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `on column layout type initiate`() = runTest {
        val viewModel = createVieWModel()
        advanceUntilIdle()

        viewModel.isColumnLayout.test {
            assertEquals(COLUMN_LAYOUT, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }
}