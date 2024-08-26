package com.id.shuttershop.utils.validation

import com.id.shuttershop.R

/**
 * Created by: andre.
 * Date: 26/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

fun ErrorValidation.isError(): Boolean {
    return this is ErrorValidation.FieldError || this is ErrorValidation.FieldEmpty
}

fun ErrorValidation.getErrorString(): Int? {
    return when (this) {
        is ErrorValidation.FieldEmpty -> {
            R.string.invalid_field_empty
        }

        is ErrorValidation.FieldError -> {
            this.errorStringRes
        }

        else -> {
            null
        }
    }
}