package com.id.domain.cart

import com.id.domain.ext.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Created by: andre.
 * Date: 08/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
interface ICartRepository {
    fun fetchCarts(): Flow<List<CartModel>>

    suspend fun fetchCartFromNetwork(id: Int): Resource<CartModel>

    suspend fun insertCart(data: CartModel)
    suspend fun deleteCart(data: CartModel)
    suspend fun findCartById(id: Int): CartModel?
    suspend fun deleteCarts(vararg data: CartModel)

    suspend fun updateCart(data: CartModel)
    suspend fun deleteAllCart()
}