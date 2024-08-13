package com.id.shuttershop.ui.screen.home

/**
 * Created by: andre.
 * Date: 12/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

data class FilterSheetEvent(
    val onSortFilterChange: (String) -> Unit,
    val onHighestPriceChange: (Double) -> Unit,
    val onLowestPriceChange: (Double) -> Unit,
    val onResetClick: () -> Unit
)
