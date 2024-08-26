package com.id.shuttershop.utils.validation

import androidx.annotation.StringRes

/**
 * Created by: andre.
 * Date: 26/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

sealed class ErrorValidation {
    data object FieldEmpty: ErrorValidation()
    data object FieldValid: ErrorValidation()
    data class FieldError(@StringRes val errorStringRes: Int): ErrorValidation()
}
