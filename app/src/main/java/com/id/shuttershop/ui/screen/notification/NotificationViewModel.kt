package com.id.shuttershop.ui.screen.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.domain.history.HistoryModel
import com.id.domain.history.IHistoryRepository
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
 * Created by: andre.
 * Date: 08/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val historyRepository: IHistoryRepository
) : ViewModel() {
    private val _notificationState = MutableStateFlow<UiState<List<HistoryModel>>>(UiState.Initiate)
    val notificationState = _notificationState.asStateFlow()

    fun fetchNotifications() {
        viewModelScope.launch(Dispatchers.IO) {
            _notificationState.run {
                handleUpdateUiState(UiState.Loading)
                val response = historyRepository.fetchHistories()
                response.onSuccess {
                    handleUpdateUiState(UiState.Success(it))
                }.onError {
                    handleUpdateUiState(UiState.Error(it))
                }
            }
        }
    }
}