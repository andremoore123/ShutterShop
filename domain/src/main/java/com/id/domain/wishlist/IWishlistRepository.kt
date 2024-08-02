package com.id.domain.wishlist

import kotlinx.coroutines.flow.Flow

/**
 * Created by: andreputras.
 * Date: 02/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
interface IWishlistRepository {
    fun fetchWishlists(): Flow<List<WishlistModel>>

    suspend fun addToWishlist(data: WishlistModel)
    suspend fun remoteWishlist(data: WishlistModel)

    suspend fun clearDatabase()
}