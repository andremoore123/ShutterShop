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
    val onVarianceChange: (ProductDetailModel, VarianceModel) -> Unit,
    val onCheckoutClick: (ProductDetailModel) -> Unit,
    val addItemToCart: (ProductDetailModel, VarianceModel?) -> Unit,
    val onWishlistClick: (ProductDetailModel, VarianceModel?) -> Unit,
    val checkIsOnWishlist: (ProductDetailModel, VarianceModel?) -> Unit,
    val onShareClick: () -> Unit,
    val changeBottomSheetValue: (Boolean) -> Unit,
    val onRetryRating: () -> Unit,
    val onRetryProduct: () -> Unit,
)
