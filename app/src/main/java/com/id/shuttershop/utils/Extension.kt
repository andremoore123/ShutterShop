package com.id.shuttershop.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.id.domain.ext.ErrorType
import com.id.domain.ext.Resource
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

fun <T> Resource<T>.onError(handleError: (ErrorType) -> Unit): Resource<T> {
    if (this is Resource.Error) {
        handleError(errorType)
    }
    return this
}

fun NavController.navigateAndPopUpAll(destination: String) {
    val currentDestination = currentDestination?.route
    currentDestination?.let {
        navigate(destination) {
            popUpTo(currentDestination) { inclusive = true }
        }
    }
}

@Composable
fun <T: Any>LazyPagingItems<T>.onLoadingState(
    loadingView: @Composable () -> Unit
): LazyPagingItems<T> {
    if (this.loadState.refresh is LoadState.Loading) {
        loadingView()
    }
    return this
}

@Composable
fun <T: Any>LazyPagingItems<T>.onLoaded(
    content: @Composable () -> Unit
): LazyPagingItems<T> {
    if (this.loadState.refresh is LoadState.NotLoading) {
        content()
    }
    return this
}

fun <T : Any>LazyPagingItems<T>.onAddContentLoading(
    loadingHandling: () -> Unit
): LazyPagingItems<T> {
    if (this.loadState.append is LoadState.Loading) {
        loadingHandling()
    }
    return this
}

@Composable
fun <T: Any>LazyPagingItems<T>.onErrorState(
    errorState: @Composable () -> Unit
): LazyPagingItems<T> {
    if (this.loadState.hasError) {
        errorState()
    }
    return this
}