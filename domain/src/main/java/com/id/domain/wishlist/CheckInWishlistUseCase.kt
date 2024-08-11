package com.id.domain.wishlist

import javax.inject.Inject

/**
 * Created by: andre.
 * Date: 11/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
class CheckInWishlistUseCase @Inject constructor(
    private val wishlistRepository: IWishlistRepository
) {
    suspend operator fun invoke(data: WishlistModel): WishlistModel? {
        return wishlistRepository.findWishlistByIdAndVariant(
            productId = data.itemId,
            data.itemVariantName
        )
    }
}