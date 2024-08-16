package com.id.data.transaction

import com.id.data.transaction.request.CheckoutRequest
import com.id.data.transaction.request.toItemCheckoutRequest
import com.id.data.transaction.response.mapToModel
import com.id.data.transaction.response.toModel
import com.id.domain.cart.CartModel
import com.id.domain.payment.PaymentType
import com.id.domain.transaction.CheckoutModel
import com.id.domain.transaction.ITransactionRepository
import com.id.domain.transaction.TransactionModel
import com.id.domain.utils.network_response.NetworkResponse
import retrofit2.HttpException
import javax.inject.Inject

/**
 * Created by: andreputras.
 * Date: 05/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
class TransactionRepository @Inject constructor(
    private val apiService: TransactionApiService
) : ITransactionRepository {
    override suspend fun checkout(
        paymentType: PaymentType, items: List<CartModel>
    ): NetworkResponse<CheckoutModel> {
        return try {
            val checkoutRequest = CheckoutRequest(paymentName = paymentType.value,
                data = items.map { it.toItemCheckoutRequest() })
            val response = apiService.checkoutItems(checkoutRequest)
            response.data?.let {
                NetworkResponse.Success(it.toModel())
            } ?: throw NullPointerException()
        } catch (e: NullPointerException) {
            NetworkResponse.EmptyValueError
        } catch (e: HttpException) {
            NetworkResponse.HttpError(e.code(), e.message())
        } catch (e: Exception) {
            NetworkResponse.UnknownError(e)
        }
    }

    override suspend fun fetchTransaction(): NetworkResponse<List<TransactionModel>> {
        return try {
            val response = apiService.fetchTransactions().data?.map { it.mapToModel() }
                ?: throw NullPointerException()
            NetworkResponse.Success(response)
        } catch (e: NullPointerException) {
            NetworkResponse.EmptyValueError
        } catch (e: HttpException) {
            NetworkResponse.HttpError(e.code(), e.message())
        } catch (e: Exception) {
            NetworkResponse.UnknownError(e)
        }
    }
}
