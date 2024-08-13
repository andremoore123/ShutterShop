package com.id.data.product.mapper

import com.id.data.product.response.ProductDetailResponse
import com.id.data.product.response.ProductVariantResponse
import com.id.domain.product.ProductDetailModel
import com.id.domain.product.VarianceModel

/**
 * Created by: andre.
 * Date: 13/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

fun ProductDetailResponse.mapToModel() = ProductDetailModel(
    id = productId.orEmpty(),
    productName = productName.orEmpty(),
    productDesc = description.orEmpty(),
    productVariance = productVariant?.map { it.mapToModel() } ?: listOf(),
    productPrice = productPrice ?: 0,
    productSold = sale?.toString() ?: "",
    productRating = productRating?.toString() ?: "0.0",
    totalRating = (totalRating ?: 0).toString(),
    imageUrl = image ?: listOf(),
    productStore = store.orEmpty(),
    productStock = stock ?: 0
)

fun ProductVariantResponse.mapToModel() = VarianceModel(
    id = 0, title = variantName ?: "", additionalPrice = variantPrice ?: 0
)