package com.id.domain.cart

import com.id.domain.ext.Resource
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Created by: andre.
 * Date: 09/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

class UpdateCartStockUseCase @Inject constructor(
    private val cartRepository: ICartRepository
) {
    suspend operator fun invoke(): Resource<Boolean> {
        var dataState: Resource<Boolean> = Resource.Initiate
        val data = cartRepository.fetchCarts().first()
        data.forEach { cartModel ->
            when (val response = cartRepository.fetchCartFromNetwork(cartModel.itemId)) {
                is Resource.Error -> {
                    dataState = Resource.Error(response.errorType)
                    return@forEach
                }
                else -> {}
            }
            if (cartModel == data.last()) {
                dataState = Resource.Success(true)
            }
        }
        return dataState
    }
}