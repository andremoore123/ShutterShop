package com.id.domain.utils.network_response


suspend fun <T> NetworkResponse<T>.onSuccess(handleResponse: suspend (T) -> Unit): NetworkResponse<T> {
    if (this is NetworkResponse.Success) {
        handleResponse(this.data)
    }
    return this
}

suspend fun <T> NetworkResponse<T>.onHttpError(handleResponse: suspend (Int, String) -> Unit): NetworkResponse<T> {
    if (this is NetworkResponse.HttpError) {
        handleResponse(this.errorCode, message)
    }
    return this
}

suspend fun <T> NetworkResponse<T>.onEmptyValueError(handleResponse: suspend () -> Unit): NetworkResponse<T> {
    if (this is NetworkResponse.EmptyValueError) {
        handleResponse()
    }
    return this
}

suspend fun <T> NetworkResponse<T>.onUnknownError(handleResponse: suspend (Exception) -> Unit): NetworkResponse<T> {
    if (this is NetworkResponse.UnknownError) {
        handleResponse(this.error)
    }
    return this
}