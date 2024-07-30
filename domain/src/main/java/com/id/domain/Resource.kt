package com.id.domain

sealed class Resource<out T> {
    data class Error(val error: Exception) : Resource<Nothing>()
    data class Success<T>(val data: T) : Resource<T>()
}