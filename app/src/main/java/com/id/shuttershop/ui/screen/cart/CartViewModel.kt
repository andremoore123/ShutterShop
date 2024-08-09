package com.id.shuttershop.ui.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.domain.cart.CartModel
import com.id.domain.cart.ICartRepository
import com.id.domain.cart.UpdateCartStockUseCase
import com.id.shuttershop.utils.UiState
import com.id.shuttershop.utils.handleUpdateUiState
import com.id.shuttershop.utils.onError
import com.id.shuttershop.utils.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by: andre.
 * Date: 09/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: ICartRepository,
    private val updateCartStockUseCase: UpdateCartStockUseCase
) : ViewModel() {
    val cartList = cartRepository.fetchCarts().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = listOf()
    )

    private val _selectedCart = MutableStateFlow<List<CartModel>>(listOf())
    val selectedCart = _selectedCart.asStateFlow()

    private val _screenState = MutableStateFlow<UiState<Boolean>>(UiState.Initiate)
    val screenState = _screenState.asStateFlow()

    fun updateCartStock() {
        viewModelScope.launch(Dispatchers.IO) {
            _screenState.run {
                val response = updateCartStockUseCase.invoke()
                response.onSuccess {
                    handleUpdateUiState(UiState.Success(it))
                }.onError { handleUpdateUiState(UiState.Error(it)) }
            }
        }
    }

    fun calculateTotalPrice(data: List<CartModel>): String {
        val totalPrice = data.map { it.itemStock * it.itemPrice }.sum()
        return totalPrice.toString()
    }

    /**
     * Handle on Select All
     *
     * **ONLY Item with Stocks > 0 AFFECTED**
     *
     * if true -> Add All to Selected
     *
     * else -> Remove from Selected
     */
    fun onSelectAllProducts(state: Boolean, data: List<CartModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            if (state) {
                _selectedCart.getAndUpdate {
                    data.filter { it.itemStock > 0 }
                }
            } else {
                _selectedCart.getAndUpdate {
                    data.filterNot { it.itemStock > 0 }
                }
            }
        }
    }


    /**
     * Handle Cart Selected Status
     */
    fun onSelectCart(status: Boolean, data: CartModel) {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedCart.getAndUpdate {
                if (status.not()) it.filter { model -> model != data } else it + data
            }
        }
    }

    fun removeCarts(data: List<CartModel>) {
        viewModelScope.launch(Dispatchers.IO) {
            cartRepository.deleteCarts(*data.toTypedArray())
        }
    }

    /**
     * Remove Selected Status from Cart
     */
    fun removeCartFromSelected(data: CartModel) {
        viewModelScope.launch(Dispatchers.IO) {
            _selectedCart.getAndUpdate { cartModels ->
                cartModels.filter { it != data }
            }
        }
    }

    /**
     * + 1 Item Cart
     */
    fun addItemCart(data: CartModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val newData = data.copy(
                itemCount = data.itemCount + 1
            )
            cartRepository.updateCart(newData)
        }
    }

    /**
     * - 1 Item Cart
     */
    fun reduceItemCart(data: CartModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val newData = data.copy(
                itemCount = data.itemCount - 1
            )
            cartRepository.updateCart(newData)
        }
    }
}

