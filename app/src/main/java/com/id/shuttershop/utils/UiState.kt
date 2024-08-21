package com.id.shuttershop.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import com.id.domain.utils.ErrorType

@Stable
sealed class UiState<out T> {
    data object Initiate : UiState<Nothing>()
    data class Error(val errorType: ErrorType) : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data object Loading : UiState<Nothing>()
}

@Composable
fun <T> UiState<T>.onSuccess(
    handleState: @Composable (data: T) -> Unit,
): UiState<T> {
    if (this is UiState.Success) {
        handleState(data)
    }
    return this
}

@Composable
fun <T> UiState<T>.onError(
    handleState: @Composable (errorType: ErrorType) -> Unit,
): UiState<T> {
    if (this is UiState.Error) {
        handleState(errorType)
    }
    return this
}

@Composable
fun <T> UiState<T>.onLoading(
    handleState: @Composable () -> Unit,
): UiState<T> {
    if (this is UiState.Loading) {
        handleState()
    }
    return this
}
