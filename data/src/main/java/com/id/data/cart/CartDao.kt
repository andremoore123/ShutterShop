package com.id.data.cart

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Created by: andre.
 * Date: 08/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@Dao
interface CartDao {
    @Query("SELECT * FROM cart")
    fun fetchCarts(): Flow<List<CartEntity>>

    @Query("SELECT * FROM cart where itemId = :id")
    suspend fun findCartById(id: Int): CartEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(data: CartEntity)

    @Delete
    suspend fun deleteCart(data: CartEntity)

    @Query("SELECT * FROM cart WHERE itemId = :itemId AND itemVariantName = :variantName")
    suspend fun findCartByItemIdAndVariant(itemId: Int, variantName: String): CartEntity?

    @Delete
    suspend fun deleteCarts(vararg data: CartEntity)

    @Query("DELETE FROM cart")
    suspend fun deleteAllCartData()

    @Update
    suspend fun updateCart(data: CartEntity)
}
