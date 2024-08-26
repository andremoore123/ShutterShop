package com.id.shuttershop.utils.validation

import com.id.shuttershop.R

/**
 * Created by: andre.
 * Date: 26/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

fun String.nameValidation(): ErrorValidation {
    return when {
        this.isEmpty() -> ErrorValidation.FieldEmpty
        !this.matches("^[a-zA-Z\\s]+$".toRegex()) -> ErrorValidation.FieldError(R.string.invalid_name_error)
        else -> ErrorValidation.FieldValid
    }
}

fun String.emailValidation(): ErrorValidation {
    val emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
    return when {
        this.isEmpty() -> ErrorValidation.FieldEmpty
        !this.matches(emailPattern) -> ErrorValidation.FieldError(R.string.invalid_email_error)
        else -> ErrorValidation.FieldValid
    }
}

fun String.passwordValidation(): ErrorValidation {
    return when {
        this.isEmpty() -> ErrorValidation.FieldEmpty
        this.length < 8 || !this.contains(Regex("[0-9]")) || !this.contains(Regex("[^A-Za-z0-9]")) ->
            ErrorValidation.FieldError(R.string.invalid_password_error)

        else -> ErrorValidation.FieldValid
    }
}

