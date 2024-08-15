package com.id.domain.transaction

import com.id.domain.cart.CartModel
import com.id.domain.cart.ICartRepository
import com.id.domain.payment.PaymentModel
import com.id.domain.utils.resource.Resource
import javax.inject.Inject

/**
 * Created by: andre.
 * Date: 10/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
class PayUseCase @Inject constructor(
    private val transactionRepository: ITransactionRepository,
    private val cartRepository: ICartRepository
) {
    suspend operator fun invoke(
        data: List<CartModel>,
        paymentModel: PaymentModel
    ): Resource<CheckoutModel> {
        val response = transactionRepository.checkout(paymentModel.paymentType, data)
        if (response is Resource.Success) {
            cartRepository.deleteCarts(data = data.toTypedArray())
        }
        return response
    }
}