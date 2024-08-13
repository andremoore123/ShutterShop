package com.id.data.wishlist.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Created by: andreputras.
 * Date: 02/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@Dao
interface WishlistDao {
    @Query("SELECT * from wishlist")
    fun fetchWishlists(): Flow<List<WishlistEntity>>

    @Insert
    suspend fun insertWishlist(data: WishlistEntity)

    @Delete
    suspend fun removeWishlist(data: WishlistEntity)

    @Update
    suspend fun updateWishlist(data: WishlistEntity)

    @Query("SELECT * FROM wishlist WHERE itemName = :name")
    suspend fun findWishlistByName(name: String): WishlistEntity?

    @Query("SELECT * FROM WISHLIST WHERE productId = :productId AND itemVariantName = :variantName")
    suspend fun findWishlistByIdAndVariant(productId: String, variantName: String): WishlistEntity?

    @Query("SELECT * FROM wishlist WHERE productId = :id")
    suspend fun findWishlistById(id: Int): WishlistEntity?

    @Query("DELETE FROM WISHLIST")
    suspend fun deleteTable()
}