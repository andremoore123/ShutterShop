package com.id.data.wishlist

import com.id.data.wishlist.source.WishlistDao
import com.id.data.wishlist.source.mapToEntity
import com.id.data.wishlist.source.mapToModel
import com.id.domain.wishlist.IWishlistRepository
import com.id.domain.wishlist.WishlistModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by: andreputras.
 * Date: 02/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
class WishlistRepository @Inject constructor(
    private val wishlistDao: WishlistDao,
) : IWishlistRepository {
    override fun fetchWishlists(): Flow<WishlistModel> = wishlistDao.fetchWishlists().map {
        it.mapToModel()
    }

    override suspend fun addToWishlist(data: WishlistModel) {
        wishlistDao.insertWishlist(data.mapToEntity())
    }

    override suspend fun remoteWishlist(data: WishlistModel) {
        wishlistDao.removeWishlist(data.mapToEntity())
    }

    override suspend fun clearDatabase() {
        wishlistDao.deleteTable()
    }
}