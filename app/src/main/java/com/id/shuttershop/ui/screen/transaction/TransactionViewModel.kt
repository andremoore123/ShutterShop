package com.id.shuttershop.ui.screen.transaction

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.domain.analytic.IAnalyticRepository
import com.id.domain.transaction.FetchTransactionsUseCase
import com.id.domain.transaction.TransactionModel
import com.id.domain.utils.resource.onError
import com.id.domain.utils.resource.onSuccess
import com.id.shuttershop.utils.UiState
import com.id.shuttershop.utils.analytics.AnalyticsConstants.EVENT_RATE_PRODUCT
import com.id.shuttershop.utils.analytics.AnalyticsConstants.PARAM_BUTTON
import com.id.shuttershop.utils.analytics.AnalyticsConstants.PARAM_SCREEN_NAME
import com.id.shuttershop.utils.analytics.AnalyticsConstants.PRODUCT_NAME
import com.id.shuttershop.utils.analytics.AnalyticsConstants.RATE_BUTTON
import com.id.shuttershop.utils.analytics.ScreenConstants.SCREEN_TRANSACTION
import com.id.shuttershop.utils.handleUpdateUiState
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
    private val fetchTransactionsUseCase: FetchTransactionsUseCase,
    private val analyticRepository: IAnalyticRepository,
) : ViewModel() {
    private val _transactionState =
        MutableStateFlow<UiState<List<TransactionModel>>>(UiState.Initiate)
    val transactionState = _transactionState.asStateFlow()

    fun fetchTransaction() {
        viewModelScope.launch(Dispatchers.IO) {
            with(_transactionState) {
                handleUpdateUiState(UiState.Loading)
                val response = fetchTransactionsUseCase.invoke()
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
        logRateItem(data.itemName)
    }

    private fun logRateItem(itemName: String) {
        val bundle = Bundle().apply {
            putString(PARAM_SCREEN_NAME, SCREEN_TRANSACTION)
            putString(PARAM_BUTTON, RATE_BUTTON)
            putString(PRODUCT_NAME, itemName)
        }
        analyticRepository.logEvent(EVENT_RATE_PRODUCT, bundle)
    }
}