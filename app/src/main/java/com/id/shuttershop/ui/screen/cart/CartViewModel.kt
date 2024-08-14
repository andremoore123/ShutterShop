package com.id.shuttershop.ui.screen.cart

import androidx.lifecycle.SavedStateHandle
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
    private val updateCartStockUseCase: UpdateCartStockUseCase,
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val totalPaymentValue = savedStateHandle.getStateFlow(TOTAL_PAYMENT, 0)

    val cartList = cartRepository.fetchCarts().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = listOf()
    )

    private val _selectedCart = MutableStateFlow<List<Int>>(listOf())
    val selectedCart = _selectedCart.asStateFlow()

    private val _screenState = MutableStateFlow<UiState<Boolean>>(UiState.Initiate)
    val screenState = _screenState.asStateFlow()

    /**
     * This Function is Fetched All of The Product Stocks from Internet
     *
     * **Run This When The Screen First Launch!!**
     */
    fun updateCartStockFromNetwork() {
        viewModelScope.launch(Dispatchers.IO) {
            _screenState.run {
                handleUpdateUiState(UiState.Loading)
                val response = updateCartStockUseCase.invoke()
                response.onSuccess {
                    handleUpdateUiState(UiState.Success(it))
                }.onError { handleUpdateUiState(UiState.Error(it)) }
            }
        }
    }

    /**
     * Remove Cart from Local Database
     * and Remove it From selected
     *
     * Wrap it With List<CartModel>
     */
    fun removeCarts(cartIds: List<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            if (cartIds.isNotEmpty()) {
                val selectedCart = getSelectedCartModelsByIds(cartIds)
                selectedCart.forEach {
                    removeCartFromSelected(it)
                }
                cartRepository.deleteCarts(*selectedCart.toTypedArray())
            } else {
                val selectedIds = selectedCart.value
                val selectedCart = getSelectedCartModelsByIds(selectedIds)
                selectedCart.forEach {
                    removeCartFromSelected(it)
                }
                cartRepository.deleteCarts(*selectedCart.toTypedArray())
            }
        }
    }

    /**
     * Add Item Cart by 1
     */
    fun addCartQuantity(data: CartModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val newItemCount = data.itemCount + 1
            if (newItemCount <= data.itemStock) {
                val newData = data.copy(
                    itemCount = newItemCount,
                )
                cartRepository.updateCart(newData)
            }
        }
    }

    /**
     * Reduce Item Cart by 1
     */
    fun reduceCartQuantity(data: CartModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val newItemCount = data.itemCount - 1
            if (newItemCount > 0) {
                val newData = data.copy(
                    itemCount = newItemCount,
                )
                cartRepository.updateCart(newData)
            }
        }
    }

    /**
     * Return Total Price of Selected Cart
     */
    fun calculateTotalPrice(): Int {
        val selectedIds = selectedCart.value
        val totalSelectedCart = getSelectedCartModelsByIds(selectedIds)
        val totalPrice = totalSelectedCart.sumOf { it.itemCount * it.itemPrice }
        return totalPrice
    }

    /**
     * Handle Cart Selected Status
     */
    fun onSelectCart(status: Boolean, data: CartModel) {
        viewModelScope.launch(Dispatchers.IO) {
            if (status) {
                addCartToSelected(data)
            } else {
                removeCartFromSelected(data)
            }
        }
    }

    /**
     * Handle on Select All
     *
     * if true, add all eligible cart to selected
     *
     * else, remove all items from selected
     */
    fun onSelectAllClick(status: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (status) {
                val listCartIds = cartList.value.getEligibleList().map { it.cartId ?: 0 }
                _selectedCart.getAndUpdate { listCartIds }
            } else {
                _selectedCart.getAndUpdate { listOf() }
            }
        }
    }

    /**
     * Check if all carts are selected
     *
     * Return Null if there are no carts that eligible
     */
    fun isAllCartSelected(): Boolean? {
        return if (cartList.value.getEligibleList().isEmpty()) {
            null
        } else {
            val selectedIds = selectedCart.value
            getSelectedCartModelsByIds(selectedIds) == cartList.value.getEligibleList()
        }
    }

    fun getSelectedCartModelsByIds(cartIds: List<Int>): List<CartModel> {
        val cart = cartList.value.getEligibleList()
        return cart.filter { cartModel ->
            cartIds.contains(cartModel.cartId)
        }
    }


    fun updateTotalPayment(totalPayment: Int) {
        savedStateHandle[TOTAL_PAYMENT] = totalPayment
    }

    private fun addCartToSelected(data: CartModel) {
        _selectedCart.getAndUpdate {
            it + (data.cartId ?: 0)
        }
    }

    private fun removeCartFromSelected(data: CartModel) {
        _selectedCart.getAndUpdate { selectedIds ->
            selectedIds.filter { it != (data.cartId ?: 0) }
        }
    }


    private fun List<CartModel>.getEligibleList(): List<CartModel> {
        val requirement: (CartModel) -> Boolean = {
            it.itemCount <= it.itemStock && it.itemStock > 0
        }
        return this.filter(requirement)
    }

    companion object {
        private const val TOTAL_PAYMENT = "totalPaymentValue"
    }
}

