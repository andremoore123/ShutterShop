package com.id.domain.utils.resource

import com.id.domain.utils.ErrorType

sealed class Resource<out T> {
    data object Initiate: Resource<Nothing>()
    data class Error(val errorType: ErrorType) : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
}