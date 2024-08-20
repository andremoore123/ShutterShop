package com.id.domain.utils

/**
 * Created by: andre.
 * Date: 20/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

fun Double?.formatToString(): String? {
    return this?.run {
        String.format("%.0f", this)
    }
}