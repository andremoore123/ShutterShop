package com.id.shuttershop.utils

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.getAndUpdate

/**
 * Created by: andreputras.
 * Date: 20/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

fun <T> MutableStateFlow<UiState<T>>.handleUpdateUiState(newState: UiState<T>) {
    this.getAndUpdate {
        newState
    }
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

@Composable
fun <T : Any> LazyPagingItems<T>.onEmptyResultError(
    content: @Composable (Int) -> Unit
): LazyPagingItems<T> {
    if (this.loadState.refresh is LoadState.Error) {
        val error = (loadState.refresh as LoadState.Error).error

        if (error is NullPointerException) {
            content(404)
        }
    }
    return this
}

@Composable
fun <T : Any> LazyPagingItems<T>.onUnknownError(
    content: @Composable () -> Unit
): LazyPagingItems<T> {
    if (this.loadState.refresh is LoadState.Error) {
        val error = (loadState.refresh as LoadState.Error).error
        if ((error is NullPointerException).not()) {
            content()
        }
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
