package com.id.domain.ext

/**
 * Created by: andreputras.
 * Date: 30/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
sealed class NetworkResponse<out T> {
    data class UnknownError(val error: Exception) : NetworkResponse<Nothing>()
    data class EmptyValueError(val error: Exception) : NetworkResponse<Nothing>()
    data class Success<T>(val data: T) : NetworkResponse<T>()
}

suspend fun <T> NetworkResponse<T>.onSuccess(handleResponse: suspend (T) -> Unit): NetworkResponse<T> {
    if (this is NetworkResponse.Success) {
        handleResponse(this.data)
    }
    return this
}

suspend fun <T> NetworkResponse<T>.onEmptyValueError(handleResponse: suspend (Exception) -> Unit): NetworkResponse<T> {
    if (this is NetworkResponse.EmptyValueError) {
        handleResponse(this.error)
    }
    return this
}
suspend fun <T> NetworkResponse<T>.onUnknownError(handleResponse: suspend (Exception) -> Unit): NetworkResponse<T> {
    if (this is NetworkResponse.UnknownError) {
        handleResponse(this.error)
    }
    return this
}