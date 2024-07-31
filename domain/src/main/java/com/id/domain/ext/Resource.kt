package com.id.domain.ext

sealed class Resource<out T> {
    data object Initiate: Resource<Nothing>()
    data class Error(val errorType: ErrorType) : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
}