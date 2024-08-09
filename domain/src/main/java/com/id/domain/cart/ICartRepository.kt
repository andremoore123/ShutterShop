package com.id.domain.cart

/**
 * Created by: andre.
 * Date: 08/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
interface ICartRepository {
    suspend fun fetchCarts(): List<CartModel>

    suspend fun insertCart(data: CartModel)
    suspend fun deleteCart(data: CartModel)
    suspend fun findCartById(id: Int): CartModel?
    suspend fun deleteCarts(vararg data: CartModel)

    suspend fun deleteAllCart()
}