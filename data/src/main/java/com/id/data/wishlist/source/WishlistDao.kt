package com.id.data.wishlist.source

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
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

    @Query("DELETE FROM WISHLIST")
    suspend fun deleteTable()
}