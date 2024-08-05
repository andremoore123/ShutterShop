package com.id.shuttershop.ui.screen.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.domain.transaction.ITransactionRepository
import com.id.domain.transaction.TransactionModel
import com.id.shuttershop.utils.UiState
import com.id.shuttershop.utils.handleUpdateUiState
import com.id.shuttershop.utils.onError
import com.id.shuttershop.utils.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by: andreputras.
 * Date: 05/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionRepository: ITransactionRepository,
) : ViewModel() {
    private val _transactionState =
        MutableStateFlow<UiState<List<TransactionModel>>>(UiState.Initiate)
    val transactionState = _transactionState.asStateFlow()

    fun fetchTransaction() {
        viewModelScope.launch(Dispatchers.IO) {
            with(_transactionState) {
                handleUpdateUiState(UiState.Loading)
                val response = transactionRepository.fetchTransaction()
                response.onSuccess {
                    handleUpdateUiState(UiState.Success(it))
                }.onError {
                    handleUpdateUiState(UiState.Error(it))
                }
            }
        }
    }

    // TODO(): Implement when Rating use case is implemented
    fun rateTransaction(data: TransactionModel) {

    }
}