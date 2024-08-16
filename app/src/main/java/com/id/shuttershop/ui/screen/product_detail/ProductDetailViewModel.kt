package com.id.shuttershop.ui.screen.product_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.domain.cart.AddToCartUseCase
import com.id.domain.cart.CartModel
import com.id.domain.product.FetchProductDetailUseCase
import com.id.domain.product.ProductDetailModel
import com.id.domain.product.VarianceModel
import com.id.domain.product.toWishlist
import com.id.domain.rating.FetchRatingUseCase
import com.id.domain.rating.RatingModel
import com.id.domain.utils.resource.onError
import com.id.domain.utils.resource.onSuccess
import com.id.domain.wishlist.AddToWishlistUseCase
import com.id.domain.wishlist.CheckInWishlistUseCase
import com.id.domain.wishlist.RemoveFromWishlistUseCase
import com.id.shuttershop.utils.UiState
import com.id.shuttershop.utils.handleUpdateUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by: andre.
 * Date: 08/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val fetchProductDetailUseCase: FetchProductDetailUseCase,
    private val fetchRatingUseCase: FetchRatingUseCase,
    private val addToCartUseCase: AddToCartUseCase,
    private val addToWishlistUseCase: AddToWishlistUseCase,
    private val removeFromWishlistUseCase: RemoveFromWishlistUseCase,
    private val checkInWishlistUseCase: CheckInWishlistUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _productState = MutableStateFlow<UiState<ProductDetailModel>>(UiState.Initiate)
    val productState = _productState.asStateFlow()

    private val _isInWishlist = MutableStateFlow(false)
    val isInWishlist = _isInWishlist.asStateFlow()

    private val _selectedVariant = MutableStateFlow<VarianceModel?>(null)
    val selectedVariant = _selectedVariant.asStateFlow()

    private val _ratingState = MutableStateFlow<UiState<List<RatingModel>>>(UiState.Initiate)
    val ratingState = _ratingState.asStateFlow()

    val isBottomShowValue = savedStateHandle.getStateFlow(IS_SHEET_SHOW_VALUE, false)

    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()

    fun modifySheetValue(value: Boolean) {
        savedStateHandle[IS_SHEET_SHOW_VALUE] = value
    }

    fun fetchProduct(id: String) {
        viewModelScope.launch(Dispatchers.IO) {
            with(_productState) {
                handleUpdateUiState(UiState.Loading)
                val response = fetchProductDetailUseCase.invoke(id)
                response.onSuccess {
                    handleUpdateUiState(UiState.Success(it))
                }.onError {
                    handleUpdateUiState(UiState.Error(it))
                }
            }
        }
    }

    fun fetchProductRating(productId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _ratingState.run {
                handleUpdateUiState(UiState.Loading)
                val response = fetchRatingUseCase.invoke(productId)
                response.onSuccess {
                    handleUpdateUiState(UiState.Success(it))
                }.onError {
                    handleUpdateUiState(UiState.Error(it))
                }
            }
        }
    }

    fun convertDetailToCart(data: ProductDetailModel, variant: VarianceModel?): CartModel {
        val additionalPrice = variant?.additionalPrice ?: data.productVariance.firstOrNull()?.additionalPrice ?: 0
        val cartModel = CartModel(
            itemId = data.id,
            itemName = data.productName,
            itemVariantName = variant?.title ?: data.productVariance.first().title,
            itemPrice = data.productPrice + additionalPrice,
            itemStock = data.productStock,
            imageUrl = data.imageUrl.firstOrNull().orEmpty()
        )
        return cartModel
    }

    fun checkOnWishlist(data: ProductDetailModel, variant: VarianceModel?) {
        viewModelScope.launch(Dispatchers.IO) {
            val wishlistModel = data.toWishlist(variant ?: data.productVariance.first())
            checkInWishlistUseCase.invoke(wishlistModel)?.let {
                removeFromWishlistUseCase(it)
            } ?: addToWishlistUseCase(wishlistModel)
            checkIsInWishlist(data, variant)
        }
    }

    fun setSelectedVariant(data: ProductDetailModel, variant: VarianceModel?) {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedVariant.getAndUpdate {
                variant
            }
            checkIsInWishlist(data, variant)
        }
    }

    /**
     * This Function Handle on Cart Click, whether it's Add or Remove
     */
    fun addItemToCart(data: ProductDetailModel, variant: VarianceModel?) {
        viewModelScope.launch(Dispatchers.IO) {
            val selectedVariant = variant ?: data.productVariance.first()
            val dataCart = CartModel(
                itemId = data.id,
                itemName = data.productName,
                itemVariantName = selectedVariant.title,
                itemPrice = data.productPrice + selectedVariant.additionalPrice,
                imageUrl = data.imageUrl.firstOrNull().orEmpty()
            )
            addToCartUseCase.invoke(dataCart)
        }
    }

    fun checkIsInWishlist(data: ProductDetailModel, variant: VarianceModel?) {
        viewModelScope.launch(Dispatchers.IO) {
            val inWishlist =
                checkInWishlistUseCase(data.toWishlist(variant ?: data.productVariance.first()))
            _isInWishlist.getAndUpdate { inWishlist != null}
        }
    }

    fun updateMessage(value: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _message.getAndUpdate {
                value
            }
            delay(1_000)
            _message.getAndUpdate {
                null
            }
        }
    }

    companion object {
        private const val IS_SHEET_SHOW_VALUE = "isSheetShow"
    }
}