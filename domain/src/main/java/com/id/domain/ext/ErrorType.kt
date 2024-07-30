package com.id.domain.ext

/**
 * Created by: andreputras.
 * Date: 30/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
sealed class ErrorType {
    data class NetworkError(val message: String) : ErrorType()
    data class ValidationError(val message: String) : ErrorType()
    data class UnknownError(val message: String) : ErrorType()
}
