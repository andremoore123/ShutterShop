package com.id.shuttershop.utils

import java.text.NumberFormat
import java.util.Locale

/**
 * Created by: andre.
 * Date: 20/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
fun String?.toTitleCase(): String {
    return this?.split(" ")?.joinToString(" ") { s ->
        s.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(
                Locale.getDefault()
            ) else it.toString()
        }
    }?.trim() ?: this.toString()
}

fun Double?.formatToRupiah(): String {
    return this?.run {
        val indonesianLocale = Locale("in", "ID")
        val formatter = NumberFormat.getCurrencyInstance(indonesianLocale)
        formatter.format(this)
    } ?: ""
}

