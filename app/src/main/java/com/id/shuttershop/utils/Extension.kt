package com.id.shuttershop.utils

import com.id.domain.Resource
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Created by: andreputras.
 * Date: 20/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

fun <T> MutableStateFlow<UiState<T>>.handleUpdateUiState(newState: UiState<T>) {
    this.value = newState
}

suspend fun <T> Resource<T>.onSuccess(handleResource: suspend (T) -> Unit): Resource<T> {
    if (this is Resource.Success) {
        handleResource(data)
    }
    return this
}

fun <T> Resource<T>.onError(handleError: (Exception) -> Unit): Resource<T> {
    if (this is Resource.Error) {
        handleError(error)
    }
    return this
}
