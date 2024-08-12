package com.id.domain.ext

/**
 * Created by: andre.
 * Date: 12/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
fun Int.formatToRupiah(): String {
    return "Rp %,d".format(this).replace(',', '.')
}
