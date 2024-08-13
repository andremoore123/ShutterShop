package com.id.shuttershop.ui.screen.home

import com.id.domain.product.ProductFilterParams
import com.id.domain.product.ProductModel

/**
 * Created by: andre.
 * Date: 13/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
data class HomeEvent(
    val onLayoutChange: (String) -> Unit,
    val navigateToDetail: (ProductModel) -> Unit,
    val navigateToSearch: () -> Unit,
    val navigateToNotification: () -> Unit,
    val navigateToCart: () -> Unit,
    val changeBottomSheetValue: (Boolean) -> Unit,
    val onShowProduct: (ProductFilterParams) -> Unit
)
