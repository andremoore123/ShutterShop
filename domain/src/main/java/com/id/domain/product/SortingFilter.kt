package com.id.domain.product

/**
 * Created by: andre.
 * Date: 12/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
enum class SortingFilter(val value: String, val title: String) {
    RATING(value = "rating", title = "Ulasan"),
    SALES("sales", title = "Penjualan"),
    LOWEST_PRICE("lowest", title = "Harga Terendah"),
    HIGHEST_PRICE("highest", title = "Harga Tertinggi")
}