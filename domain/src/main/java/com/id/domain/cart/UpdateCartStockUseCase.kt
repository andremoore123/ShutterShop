package com.id.domain.cart

import com.id.domain.product.IProductRepository
import com.id.domain.utils.ErrorType
import com.id.domain.utils.network_response.NetworkResponse
import com.id.domain.utils.resource.Resource
import kotlinx.coroutines.flow.first
import javax.inject.Inject

/**
 * Created by: andre.
 * Date: 09/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

class UpdateCartStockUseCase @Inject constructor(
    private val cartRepository: ICartRepository,
    private val productRepository: IProductRepository
) {
    suspend operator fun invoke(): Resource<Boolean> {
        var dataState: Resource<Boolean> = Resource.Initiate
        val data = cartRepository.fetchCarts().first()
        data.forEach { cartModel ->
            when (val response = productRepository.fetchProductDetail(cartModel.itemId)) {
                is NetworkResponse.Success -> {
                    val newCartModel = cartModel.copy(
                        itemStock = response.data.productStock
                    )
                    cartRepository.updateCart(newCartModel)
                }

                else -> {
                    dataState = Resource.Error(ErrorType.EmptyDataError)
                    return@forEach
                }
            }
            if (cartModel == data.last()) {
                dataState = Resource.Success(true)
            }
        }
        return dataState
    }
}