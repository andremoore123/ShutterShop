package com.id.shuttershop.ui.screen.wishlist

import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.domain.analytic.IAnalyticRepository
import com.id.domain.cart.AddToCartUseCase
import com.id.domain.wishlist.IWishlistRepository
import com.id.domain.wishlist.WishlistModel
import com.id.shuttershop.utils.DispatcherProvider
import com.id.shuttershop.utils.analytics.AnalyticsConstants.EVENT_WISHLIST_ADD_TO_CART
import com.id.shuttershop.utils.analytics.AnalyticsConstants.EVENT_WISHLIST_LAYOUT
import com.id.shuttershop.utils.analytics.AnalyticsConstants.EVENT_WISHLIST_REMOVE
import com.id.shuttershop.utils.analytics.AnalyticsConstants.PARAM_LAYOUT
import com.id.shuttershop.utils.analytics.AnalyticsConstants.PARAM_SCREEN_NAME
import com.id.shuttershop.utils.analytics.AnalyticsConstants.WISHLIST_BUTTON
import com.id.shuttershop.utils.analytics.ScreenConstants.SCREEN_WISHLIST
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by: andreputras.
 * Date: 02/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@HiltViewModel
class WishlistViewModel @Inject constructor(
    private val wishlistRepository: IWishlistRepository,
    private val analyticRepository: IAnalyticRepository,
    private val addToCartUseCase: AddToCartUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    val isColumnLayout = savedStateHandle.getStateFlow(LAYOUT_TYPE, COLUMN_LAYOUT)

    val wishlists = wishlistRepository.fetchWishlists().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = listOf()
    )

    private val _message = MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()

    fun deleteWishlist(data: WishlistModel) {
        logDeleteFromWishlist(data.itemName)
        viewModelScope.launch(dispatcherProvider.io) {
            wishlistRepository.removeWishlist(data)
        }
    }

    fun addToCart(data: WishlistModel) {
        logAddToCart(data.itemName)
        viewModelScope.launch(dispatcherProvider.io) {
            addToCartUseCase.invoke(data)
        }
    }

    fun setLayoutType(currentLayout: String) {
        logLayoutType(currentLayout)
        if (currentLayout == GRID_LAYOUT) {
            savedStateHandle[LAYOUT_TYPE] = COLUMN_LAYOUT
        } else {
            savedStateHandle[LAYOUT_TYPE] = GRID_LAYOUT
        }
    }

    fun updateMessage(value: String) {
        viewModelScope.launch(dispatcherProvider.io) {
            _message.getAndUpdate {
                value
            }
            delay(1_000)
            _message.getAndUpdate {
                null
            }
        }
    }

    private fun logDeleteFromWishlist(productName: String) {
        val param = Bundle().apply {
            putString(PARAM_SCREEN_NAME, SCREEN_WISHLIST)
            putString(WISHLIST_BUTTON, productName)
        }
        analyticRepository.logEvent(EVENT_WISHLIST_REMOVE, param)
    }

    private fun logAddToCart(productName: String) {
        val param = Bundle().apply {
            putString(PARAM_SCREEN_NAME, SCREEN_WISHLIST)
            putString(WISHLIST_BUTTON, productName)
        }
        analyticRepository.logEvent(EVENT_WISHLIST_ADD_TO_CART, param)
    }

    private fun logLayoutType(layoutType: String) {
        val params = Bundle().apply {
            putString(PARAM_SCREEN_NAME, SCREEN_WISHLIST)
            putString(PARAM_LAYOUT, layoutType)
        }
        analyticRepository.logEvent(EVENT_WISHLIST_LAYOUT, params)
    }

    companion object {
        const val GRID_LAYOUT = "gridLayout"
        const val COLUMN_LAYOUT = "columnLayout"
        private const val LAYOUT_TYPE = "layoutType"
    }
}