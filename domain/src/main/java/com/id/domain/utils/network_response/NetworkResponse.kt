package com.id.domain.utils.network_response

/**
 * Created by: andreputras.
 * Date: 30/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
sealed class NetworkResponse<out T> {
    data class UnknownError(val error: Exception) : NetworkResponse<Nothing>()
    data object EmptyValueError : NetworkResponse<Nothing>()
    data class Success<T>(val data: T) : NetworkResponse<T>()
    data class HttpError(val errorCode: Int, val message: String) : NetworkResponse<Nothing>()
}
