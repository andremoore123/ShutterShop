package com.id.domain.cart

import com.id.domain.wishlist.WishlistModel
import javax.inject.Inject

/**
 * Created by: andre.
 * Date: 10/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
class AddToCartUseCase @Inject constructor(
    private val cartRepository: ICartRepository,
) {
    suspend operator fun invoke(data: CartModel) {
        insertCart(data)
    }

    suspend operator fun invoke(data: WishlistModel) {
        val cartModel = CartModel(
            itemId = data.itemId, itemName = data.itemName, itemPrice = data.itemPrice
        )
        insertCart(cartModel)
    }

    /**
     * - This function will insert cart data
     *
     * - If data is exist, then add the item count +1
     * - Else, Insert a new one
     */
    private suspend fun insertCart(data: CartModel) {
        val cartFromDatabase = cartRepository.findCartById(data.cartId ?: 0)
        cartFromDatabase?.let {
            it.run {
                val newData = copy(
                    itemCount = it.itemCount + 1
                )
                cartRepository.updateCart(newData)
            }
        } ?: cartRepository.insertCart(data)
    }
}