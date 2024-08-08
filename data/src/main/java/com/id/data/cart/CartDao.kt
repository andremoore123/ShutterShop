package com.id.data.cart

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

/**
 * Created by: andre.
 * Date: 08/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@Dao
interface CartDao {
    @Query("SELECT * FROM cart")
    suspend fun fetchCarts(): List<CartEntity>

    @Query("SELECT * FROM cart where itemId = :id")
    suspend fun findCartById(id: Int): CartEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCart(data: CartEntity)

    @Delete
    suspend fun deleteCart(data: CartEntity)

    @Delete
    suspend fun deleteCarts(vararg data: CartEntity)

    @Query("DELETE FROM cart")
    suspend fun deleteAllCartData()
}
