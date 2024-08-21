package com.id.shuttershop.ui.screen.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.id.domain.notification.FetchNotificationUseCase
import com.id.domain.notification.NotificationModel
import com.id.domain.utils.resource.onError
import com.id.domain.utils.resource.onSuccess
import com.id.shuttershop.utils.DispatcherProvider
import com.id.shuttershop.utils.UiState
import com.id.shuttershop.utils.handleUpdateUiState
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val fetchNotificationUseCase: FetchNotificationUseCase,
    private val coroutineDispatcher: DispatcherProvider
) : ViewModel() {
    private val _notificationState = MutableStateFlow<UiState<List<NotificationModel>>>(UiState.Initiate)
    val notificationState = _notificationState.asStateFlow()

    fun fetchNotifications() {
        viewModelScope.launch(coroutineDispatcher.io) {
            _notificationState.run {
                handleUpdateUiState(UiState.Loading)
                val response = fetchNotificationUseCase.invoke()
                response.onSuccess {
                    handleUpdateUiState(UiState.Success(it))
                }.onError {
                    handleUpdateUiState(UiState.Error(it))
                }
            }
        }
    }
}