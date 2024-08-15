package com.id.shuttershop.ui.screen.checkout

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.domain.cart.CartModel
import com.id.domain.payment.IPaymentRepository
import com.id.domain.payment.PaymentModel
import com.id.domain.transaction.CheckoutModel
import com.id.domain.transaction.PayUseCase
import com.id.domain.utils.formatToRupiah
import com.id.domain.utils.resource.onError
import com.id.domain.utils.resource.onSuccess
import com.id.shuttershop.utils.UiState
import com.id.shuttershop.utils.handleUpdateUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
class CheckoutViewModel @Inject constructor(
    paymentRepository: IPaymentRepository,
    private val savedStateHandle: SavedStateHandle,
    private val payUseCase: PayUseCase
) : ViewModel() {

    private val _paymentState = MutableStateFlow<UiState<CheckoutModel>>(UiState.Initiate)
    val paymentState = _paymentState.asStateFlow()

    val isBottomShowValue = savedStateHandle.getStateFlow(IS_SHEET_SHOW_VALUE, false)

    val selectedPaymentValue: StateFlow<PaymentModel?> =
        savedStateHandle.getStateFlow(SELECTED_PAYMENT_METHOD, null)

    val paymentMethods = paymentRepository.fetchPaymentMethods().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = listOf()
    )

    fun payItem(data: List<CartModel>, paymentModel: PaymentModel) {
        viewModelScope.launch(Dispatchers.IO) {
            _paymentState.run {
                handleUpdateUiState(UiState.Loading)
                val response = payUseCase.invoke(data, paymentModel)
                response.onSuccess {
                    handleUpdateUiState(UiState.Success(it))
                }.onError {
                    handleUpdateUiState(UiState.Error(it))
                }
            }
        }
    }

    fun calculateTotalPrice(data: List<CartModel>): String {
        val totalPrice = data.sumOf { it.itemCount * it.itemPrice }
        return totalPrice.formatToRupiah()
    }


    fun modifySheetValue(value: Boolean) {
        savedStateHandle[IS_SHEET_SHOW_VALUE] = value
    }

    fun modifySelectPayment(value: PaymentModel) {
        savedStateHandle[SELECTED_PAYMENT_METHOD] = value
    }


    companion object {
        private const val IS_SHEET_SHOW_VALUE = "isSheetShow"
        private const val SELECTED_PAYMENT_METHOD = "selectedPaymentMethod"
    }
}