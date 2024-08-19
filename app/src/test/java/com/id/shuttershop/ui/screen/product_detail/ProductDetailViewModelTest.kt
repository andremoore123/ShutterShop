package com.id.shuttershop.ui.screen.product_detail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import com.id.domain.cart.AddToCartUseCase
import com.id.domain.cart.CartModel
import com.id.domain.product.FetchProductDetailUseCase
import com.id.domain.product.ProductDetailModel
import com.id.domain.product.VarianceModel
import com.id.domain.product.toWishlist
import com.id.domain.rating.FetchRatingUseCase
import com.id.domain.rating.RatingModel
import com.id.domain.utils.ErrorType
import com.id.domain.utils.resource.Resource
import com.id.domain.wishlist.AddToWishlistUseCase
import com.id.domain.wishlist.CheckInWishlistUseCase
import com.id.domain.wishlist.RemoveFromWishlistUseCase
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
class ProductDetailViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var fetchProductDetailUseCase: FetchProductDetailUseCase

    private lateinit var fetchRatingUseCase: FetchRatingUseCase

    private lateinit var addToCartUseCase: AddToCartUseCase

    private lateinit var addToWishlistUseCase: AddToWishlistUseCase

    private lateinit var removeFromWishlistUseCase: RemoveFromWishlistUseCase

    private lateinit var checkInWishlistUseCase: CheckInWishlistUseCase

    private val productDetailModel = ProductDetailModel(
        id = "lorem",
        productName = "Deandre Vaughn",
        productDesc = "splendide",
        productVariance = listOf(),
        productPrice = 8196,
        productSold = "commune",
        productRating = "evertitur",
        totalRating = "omittam",
        imageUrl = listOf(),
        productStore = "legimus",
        productStock = 4724,
        totalSatisfaction = 8585
    )

    private val varianceModel = VarianceModel(id = 4220, title = "has", additionalPrice = 6877)

    private inline fun <reified T> mockCreation() = Mockito.mock(T::class.java)
    private fun createViewModel() = ProductDetailViewModel(
        fetchProductDetailUseCase = fetchProductDetailUseCase,
        fetchRatingUseCase = fetchRatingUseCase,
        addToCartUseCase = addToCartUseCase,
        addToWishlistUseCase = addToWishlistUseCase,
        removeFromWishlistUseCase = removeFromWishlistUseCase,
        checkInWishlistUseCase = checkInWishlistUseCase,
        savedStateHandle = SavedStateHandle(),
        dispatcherProvider = mainDispatcherRule.dispatcherProvider
    )

    @Before
    fun setUp() {
        fetchProductDetailUseCase = mockCreation()
        fetchRatingUseCase = mockCreation()
        addToWishlistUseCase = mockCreation()
        addToCartUseCase = mockCreation()
        removeFromWishlistUseCase = mockCreation()
        checkInWishlistUseCase = mockCreation()
    }

    @Test
    fun `on fetch product detail success`() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        val expectValue = ProductDetailModel(
            id = "lorem",
            productName = "Troy Coleman",
            productDesc = "urbanitas",
            productVariance = listOf(),
            productPrice = 4755,
            productSold = "non",
            productRating = "euismod",
            totalRating = "putent",
            imageUrl = listOf(),
            productStore = "iriure",
            productStock = 8784,
            totalSatisfaction = 9202
        )

        Mockito.`when`(fetchProductDetailUseCase("123")).thenReturn(Resource.Success(expectValue))

        viewModel.fetchProduct("123")
        advanceUntilIdle()

        viewModel.productState.test {
            assertEquals(UiState.Success(expectValue), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `on fetch product detail error`() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        val expectValue = ErrorType.EmptyDataError

        Mockito.`when`(fetchProductDetailUseCase.invoke("123"))
            .thenReturn(Resource.Error(expectValue))

        viewModel.fetchProduct("123")
        advanceUntilIdle()

        viewModel.productState.test {
            assertEquals(UiState.Error(expectValue), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `on fetch detail product rating success`() = runTest {
        val productId = "123"

        val expectValue = listOf(
            RatingModel(
                id = 2528,
                userName = "Chrystal Cherry",
                userImageUrl = "https://duckduckgo.com/?q=netus",
                ratingProduct = 2903,
                ratingDescription = "nullam"
            )
        )
        val viewModel = createViewModel()
        advanceUntilIdle()

        Mockito.`when`(fetchRatingUseCase.invoke(productId))
            .thenReturn(Resource.Success(expectValue))

        viewModel.fetchProductRating(productId)
        advanceUntilIdle()

        viewModel.ratingState.test {
            assertEquals(UiState.Success(expectValue), awaitItem())
        }
    }

    @Test
    fun `on fetch detail product rating error`() = runTest {
        val productId = "123"

        val expectValue = ErrorType.EmptyDataError

        val viewModel = createViewModel()
        advanceUntilIdle()

        Mockito.`when`(fetchRatingUseCase.invoke(productId)).thenReturn(Resource.Error(expectValue))

        viewModel.fetchProductRating(productId)
        advanceUntilIdle()

        viewModel.ratingState.test {
            assertEquals(UiState.Error(expectValue), awaitItem())
        }
    }

    @Test
    fun `convert detail to cart variant model exist`() = runTest {
        val expectValueDetail = productDetailModel

        val expectValueVariance = varianceModel
        val additionalPrice = expectValueVariance.additionalPrice

        val expectResult = CartModel(
            itemId = expectValueDetail.id,
            itemName = expectValueDetail.productName,
            itemVariantName = expectValueVariance.title,
            itemPrice = expectValueDetail.productPrice + additionalPrice,
            itemStock = expectValueDetail.productStock,
            imageUrl = expectValueDetail.imageUrl.firstOrNull().orEmpty()
        )
        val viewModel = createViewModel()
        advanceUntilIdle()

        val actualResult = viewModel.convertDetailToCart(expectValueDetail, expectValueVariance)

        assertEquals(expectResult, actualResult)
    }

    @Test
    fun `convert detail to cart variant model null`() = runTest {
        val expectValueDetail = productDetailModel

        val expectValueVariance: VarianceModel? = null
        val additionalPrice = 0

        val expectResult = CartModel(
            itemId = expectValueDetail.id,
            itemName = expectValueDetail.productName,
            itemVariantName = expectValueDetail.productVariance.firstOrNull()?.title.orEmpty(),
            itemPrice = expectValueDetail.productPrice + additionalPrice,
            itemStock = expectValueDetail.productStock,
            imageUrl = expectValueDetail.imageUrl.firstOrNull().orEmpty()
        )
        val viewModel = createViewModel()
        advanceUntilIdle()

        val actualResult = viewModel.convertDetailToCart(expectValueDetail, expectValueVariance)

        assertEquals(expectResult, actualResult)
    }


    @Test
    fun `check on wishlist true then remove`() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        val expectDetailModel = productDetailModel
        val expectVariance = varianceModel
        val expectWishlistModel = productDetailModel.toWishlist(expectVariance)

        Mockito.`when`(checkInWishlistUseCase.invoke(expectWishlistModel))
            .thenReturn(expectWishlistModel)

        viewModel.checkOnWishlist(expectDetailModel, expectVariance)
        advanceUntilIdle()

        Mockito.verify(removeFromWishlistUseCase).invoke(expectWishlistModel)
    }

    @Test
    fun `check on wishlist false then add`() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        val expectDetailModel = productDetailModel
        val expectVariance = varianceModel
        val expectWishlistModel = productDetailModel.toWishlist(expectVariance)

        Mockito.`when`(checkInWishlistUseCase.invoke(expectWishlistModel)).thenReturn(null)

        viewModel.checkOnWishlist(expectDetailModel, expectVariance)
        advanceUntilIdle()

        Mockito.verify(addToWishlistUseCase).invoke(expectWishlistModel)
    }

    @Test
    fun `set selected variant`() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        viewModel.setSelectedVariant(productDetailModel, varianceModel)
        advanceUntilIdle()

        viewModel.selectedVariant.test {
            assertEquals(varianceModel, awaitItem())
        }
    }

    @Test
    fun `add item to cart`() = runTest {
        val viewModel = createViewModel()
        advanceUntilIdle()

        val expectDetailModel = productDetailModel
        val expectVarianceModel = varianceModel
        val expectDataCart = CartModel(
            itemId = expectDetailModel.id,
            itemName = expectDetailModel.productName,
            itemVariantName = expectVarianceModel.title,
            itemPrice = expectDetailModel.productPrice + expectVarianceModel.additionalPrice,
            imageUrl = expectDetailModel.imageUrl.firstOrNull().orEmpty()
        )
        viewModel.addItemToCart(productDetailModel, expectVarianceModel)
        advanceUntilIdle()

        Mockito.verify(addToCartUseCase).invoke(expectDataCart)
    }

    @Test
    fun `check is data in wishlist true`() = runTest {
        val expectDetailModel = productDetailModel
        val expectVarianceModel = varianceModel
        val expectWishlistModel = expectDetailModel.toWishlist(expectVarianceModel)

        val viewModel = createViewModel()
        advanceUntilIdle()

        Mockito.`when`(checkInWishlistUseCase.invoke(expectWishlistModel))
            .thenReturn(expectWishlistModel)

        viewModel.checkIsInWishlist(expectDetailModel, varianceModel)
        advanceUntilIdle()

        viewModel.isInWishlist.test {
            assertEquals(true, awaitItem())
        }
    }

    @Test
    fun `check is data in wishlist false`() = runTest {
        val expectDetailModel = productDetailModel
        val expectVarianceModel = varianceModel
        val expectWishlistModel = expectDetailModel.toWishlist(expectVarianceModel)

        val viewModel = createViewModel()
        advanceUntilIdle()

        Mockito.`when`(checkInWishlistUseCase.invoke(expectWishlistModel)).thenReturn(null)

        viewModel.checkIsInWishlist(expectDetailModel, varianceModel)
        advanceUntilIdle()

        viewModel.isInWishlist.test {
            assertEquals(false, awaitItem())
        }
    }

    @Test
    fun `update message then null`() = runTest {
        val viewModel = createViewModel()
        viewModel.updateMessage("123")
        advanceUntilIdle()

        viewModel.message.test {
            assertEquals(null, awaitItem())
        }
    }

    @Test
    fun `modify sheet value true`() = runTest {
        val viewModel = createViewModel()
        viewModel.modifySheetValue(true)
        advanceUntilIdle()

        viewModel.isBottomShowValue.test {
            assertEquals(true, awaitItem())
        }
    }

    @Test
    fun `modify sheet value false`() = runTest {
        val viewModel = createViewModel()
        viewModel.modifySheetValue(false)
        advanceUntilIdle()

        viewModel.isBottomShowValue.test {
            assertEquals(false, awaitItem())
        }
    }
}