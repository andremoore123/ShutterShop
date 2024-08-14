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
    override fun fetchWishlists(): Flow<List<WishlistModel>> =
        wishlistDao.fetchWishlists().map { list ->
            list.map {
                it.mapToModel()
            }
        }

    override suspend fun findWishlistByName(name: String): WishlistModel? {
        val data = wishlistDao.findWishlistByName(name)
        return data?.mapToModel()
    }

    override suspend fun findWishlistById(id: Int): WishlistModel? =
        wishlistDao.findWishlistById(id)?.mapToModel()

    override suspend fun findWishlistByIdAndVariant(productId: String, variantName: String): WishlistModel? =
        wishlistDao.findWishlistByIdAndVariant(productId, variantName)?.mapToModel()

    override suspend fun addToWishlist(data: WishlistModel) {
        wishlistDao.insertWishlist(data.mapToEntity())
    }

    override suspend fun removeWishlist(data: WishlistModel) {
        wishlistDao.removeWishlist(data.mapToEntity())
    }

    override suspend fun updateWishlist(data: WishlistModel) {
        wishlistDao.updateWishlist(data.mapToEntity())
    }

    override suspend fun clearDatabase() {
        wishlistDao.deleteTable()
    }
}