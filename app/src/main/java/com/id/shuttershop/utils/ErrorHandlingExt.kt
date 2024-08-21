package com.id.shuttershop.utils

import androidx.compose.runtime.Composable
import com.id.domain.utils.ErrorType

/**
 * Created by: andre.
 * Date: 15/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@Composable
fun ErrorType.OnUnknownError(content: @Composable () -> Unit) {
    if (this is ErrorType.UnknownError) {
        content()
    }
}

@Composable
fun ErrorType.OnEmptyError(content: @Composable () -> Unit) {
    if (this is ErrorType.EmptyDataError) {
        content()
    }
}

@Composable
fun ErrorType.OnHttpError(content: @Composable (Int) -> Unit) {
    if (this is ErrorType.HTTPError) {
        content(this.code)
    }
}