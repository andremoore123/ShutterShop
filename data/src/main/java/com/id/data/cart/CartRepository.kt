package com.id.data.cart

import com.id.domain.cart.CartModel
import com.id.domain.cart.ICartRepository
import com.id.domain.utils.resource.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
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
    override fun fetchCarts(): Flow<List<CartModel>> =
        cartDao.fetchCarts().map { cartEntities -> cartEntities.map { it.toModel() } }

    override suspend fun fetchCartFromNetwork(id: String): Resource<CartModel> {
        return Resource.Success(
            CartModel(
                itemId = "",
                itemName = "",
                itemVariantName = "",
                itemStock = 0,
                itemCount = 0,
                itemPrice = 0
            )
        )
    }

    override suspend fun insertCart(data: CartModel) = cartDao.insertCart(data.toEntity())

    override suspend fun deleteCart(data: CartModel) = cartDao.deleteCart(data.toEntity())

    override suspend fun findCartById(id: Int): CartModel? = cartDao.findCartById(id)?.toModel()

    override suspend fun findCartByItemIdAndVariant(itemId: String, variantName: String): CartModel? =
        cartDao.findCartByItemIdAndVariant(itemId, variantName)?.toModel()

    override suspend fun deleteCarts(vararg data: CartModel) {
        val entities = data.map { it.toEntity() }.toTypedArray()
        cartDao.deleteCarts(*entities)
    }

    override suspend fun updateCart(data: CartModel) = cartDao.updateCart(data.toEntity())

    override suspend fun deleteAllCart() = cartDao.deleteAllCartData()
}