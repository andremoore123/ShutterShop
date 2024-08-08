package com.id.shuttershop.ui.screen.product_detail

import com.id.domain.product.ProductDetailModel
import com.id.domain.product.VarianceModel

/**
 * Created by: andre.
 * Date: 08/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
data class DetailProductEvent(
    val onVarianceChange: (VarianceModel) -> Unit,
    val onCheckoutClick: (ProductDetailModel) -> Unit,
    val onCartClick: (ProductDetailModel, Boolean) -> Unit,
    val onWishlistClick: (ProductDetailModel) -> Unit,
    val checkIsOnWishlist: (ProductDetailModel) -> Unit,
    val checkIsOnCart: (ProductDetailModel) -> Unit,
    val onShareClick: () -> Unit,
)
