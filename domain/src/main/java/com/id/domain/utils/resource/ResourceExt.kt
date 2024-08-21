package com.id.domain.utils.resource

import com.id.domain.utils.ErrorType

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