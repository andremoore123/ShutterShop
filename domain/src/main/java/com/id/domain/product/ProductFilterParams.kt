package com.id.domain.product

/**
 * Created by: andreputras.
 * Date: 05/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
data class ProductFilterParams(
    val sortBy: String? = null,
    val productCategory: String? = null,
    val lowestPrice: Double? = null,
    val highestPrice: Double? = null,
)
