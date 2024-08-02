package com.id.shuttershop.ui.screen.wishlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.domain.wishlist.IWishlistRepository
import com.id.domain.wishlist.WishlistModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
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
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val isColumnLayout = savedStateHandle.getStateFlow(LAYOUT_TYPE, COLUMN_LAYOUT)

    val wishlists = wishlistRepository.fetchWishlists().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = listOf()
    )

    fun deleteWishlist(data: WishlistModel) {
        viewModelScope.launch(Dispatchers.IO) {
            wishlistRepository.remoteWishlist(data)
        }
    }

    fun addToCart(data: WishlistModel) {
        viewModelScope.launch(Dispatchers.IO) {
            // TODO(): Implement This Function when Cart Feature is Available
        }
    }

    fun setLayoutType(currentLayout: String) {
        if (currentLayout == GRID_LAYOUT) {
            savedStateHandle[LAYOUT_TYPE] = COLUMN_LAYOUT
        } else {
            savedStateHandle[LAYOUT_TYPE] = GRID_LAYOUT
        }
    }

    companion object {
        const val GRID_LAYOUT = "gridLayout"
        const val COLUMN_LAYOUT = "columnLayout"
        private const val LAYOUT_TYPE = "layoutType"
    }
}