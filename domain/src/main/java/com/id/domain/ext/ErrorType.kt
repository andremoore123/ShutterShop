package com.id.domain.ext

/**
 * Created by: andreputras.
 * Date: 30/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
sealed class ErrorType(val errorMessage: String) {
    data class NetworkError(val message: String) : ErrorType(message)
    data class HTTPError(val message: String) : ErrorType(message)
    data class UnknownError(val message: String) : ErrorType(message)
}
