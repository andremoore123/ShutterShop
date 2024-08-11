package com.id.domain.cart

import javax.inject.Inject

/**
 * Created by: andre.
 * Date: 10/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
class RemoveFromCartUseCase @Inject constructor(
    private val cartRepository: ICartRepository,
) {
    suspend operator fun invoke(data: CartModel) {
        cartRepository.deleteCart(data)
    }
}