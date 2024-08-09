package com.id.data.cart

import com.id.domain.cart.CartModel
import com.id.domain.cart.ICartRepository
import javax.inject.Inject

/**
 * Created by: andre.
 * Date: 08/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
class CartRepository @Inject constructor(
    private val cartDao: CartDao
) : ICartRepository {
    override suspend fun fetchCarts(): List<CartModel> = cartDao.fetchCarts().map { it.toModel() }

    override suspend fun insertCart(data: CartModel) = cartDao.insertCart(data.toEntity())

    override suspend fun deleteCart(data: CartModel) = cartDao.deleteCart(data.toEntity())

    override suspend fun findCartById(id: Int): CartModel? = cartDao.findCartById(id)?.toModel()

    override suspend fun deleteCarts(vararg data: CartModel) {
        val entities = data.map { it.toEntity() }.toTypedArray()
        cartDao.deleteCarts(*entities)
    }

    override suspend fun deleteAllCart() = cartDao.deleteAllCartData()
}