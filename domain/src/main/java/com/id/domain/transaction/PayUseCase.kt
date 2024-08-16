package com.id.domain.transaction

import com.id.domain.cart.CartModel
import com.id.domain.cart.ICartRepository
import com.id.domain.payment.PaymentModel
import com.id.domain.utils.ErrorType
import com.id.domain.utils.network_response.onEmptyValueError
import com.id.domain.utils.network_response.onHttpError
import com.id.domain.utils.network_response.onSuccess
import com.id.domain.utils.network_response.onUnknownError
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
        var result: Resource<CheckoutModel> = Resource.Initiate
        val response = transactionRepository.checkout(paymentModel.paymentType, data)

        response.onSuccess {
            result = Resource.Success(it)
            cartRepository.deleteCarts(*data.toTypedArray())
        }.onUnknownError {
            result = Resource.Error(ErrorType.UnknownError(it.message.toString()))
        }.onEmptyValueError {
            result = Resource.Error(ErrorType.EmptyDataError)
        }.onHttpError { code, message ->
            result = Resource.Error(ErrorType.HTTPError(code, message))
        }

        return result
    }
}