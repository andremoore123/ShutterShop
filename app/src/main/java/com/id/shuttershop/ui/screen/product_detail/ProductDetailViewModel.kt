package com.id.shuttershop.ui.screen.product_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.domain.product.IProductRepository
import com.id.domain.product.ProductDetailModel
import com.id.domain.product.VarianceModel
import com.id.domain.product.toWishlist
import com.id.domain.wishlist.IWishlistRepository
import com.id.shuttershop.utils.UiState
import com.id.shuttershop.utils.handleUpdateUiState
import com.id.shuttershop.utils.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    private val productRepository: IProductRepository,
    private val wishlistRepository: IWishlistRepository,
) : ViewModel() {
    private val _productState = MutableStateFlow<UiState<ProductDetailModel>>(UiState.Initiate)
    val productState = _productState.asStateFlow()

    private val _isInWishlist = MutableStateFlow(false)
    val isInWishlist = _isInWishlist.asStateFlow()

    private val _isInCart = MutableStateFlow(false)
    val isInCart = _isInCart.asStateFlow()

    private val _selectedVariant = MutableStateFlow<VarianceModel?>(null)
    val selectedVariant = _selectedVariant.asStateFlow()

    fun fetchProduct(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            with(_productState) {
                handleUpdateUiState(UiState.Loading)
                val response = productRepository.fetchProductDetail(id)
                response.onSuccess {
                    setSelectedVariant(it.productVariance.firstOrNull())
                    handleUpdateUiState(UiState.Success(it))
                }
            }
        }
    }

    fun checkOnWishlist(data: ProductDetailModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val inWishlist = wishlistRepository.findWishlistByName(data.productName)
            inWishlist?.let {
                wishlistRepository.removeWishlist(it)
            } ?: wishlistRepository.addToWishlist(
                data.toWishlist()
            )
            checkIsInWishlist(data)
        }
    }

    fun setSelectedVariant(variant: VarianceModel?) {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedVariant.getAndUpdate {
                variant
            }
        }
    }

    /**
     * This Function Handle on Cart Click, whether it's Add or Remove
     */

    fun onChartClick(data: ProductDetailModel, isOnCart: Boolean) {
        if (isOnCart) {
            removeFromCart(data)
        } else {
            addToCart(data)
        }
        checkIsOnCart(data)
    }

    /**
     * This function Check Product from Local Database
     */
    fun checkIsOnCart(data: ProductDetailModel) {
        viewModelScope.launch(Dispatchers.IO) {
            _isInCart.getAndUpdate {
                it.not()
            }
        }
    }

    private fun addToCart(data: ProductDetailModel) {

    }

    private fun removeFromCart(data: ProductDetailModel) {

    }

    fun checkIsInWishlist(data: ProductDetailModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val inWishlist = wishlistRepository.findWishlistByName(data.productName)
            _isInWishlist.getAndUpdate { inWishlist != null }
        }
    }


}