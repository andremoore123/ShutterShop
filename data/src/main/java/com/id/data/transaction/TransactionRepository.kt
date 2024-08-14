package com.id.data.transaction

import com.id.data.transaction.request.CheckoutRequest
import com.id.data.transaction.request.toItemCheckoutRequest
import com.id.data.transaction.response.toModel
import com.id.domain.cart.CartModel
import com.id.domain.ext.ErrorType
import com.id.domain.ext.Resource
import com.id.domain.payment.PaymentType
import com.id.domain.transaction.CheckoutModel
import com.id.domain.transaction.ITransactionRepository
import com.id.domain.transaction.TransactionModel
import com.id.domain.transaction.TransactionStatus
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
    ): Resource<CheckoutModel> {
        return try {
            val checkoutRequest = CheckoutRequest(paymentName = paymentType.value,
                data = items.map { it.toItemCheckoutRequest() })
            val response = apiService.checkoutItems(checkoutRequest)
            response.data?.let {
                Resource.Success(it.toModel())
            } ?: throw NullPointerException()
        } catch (e: Exception) {
            Resource.Error(ErrorType.NetworkError(e.message.toString()))
        }
    }

    override suspend fun fetchTransaction(): Resource<List<TransactionModel>> {
        val dummyResponse = listOf(
            TransactionModel(
                itemName = "Dwight Arnold",
                itemTotal = "2",
                itemPrice = "23000",
                transactionTotal = "Rp 23.000.000",
                transactionStatus = TransactionStatus.FAILED,
                transactionDate = "23 February 2024",
                itemImageUrl = "https://search.yahoo.com/search?p=dicta",
            ),
            TransactionModel(
                itemName = "Dwight Arnold",
                itemTotal = "2",
                itemPrice = "23000",
                transactionTotal = "Rp 23.000.000",
                transactionStatus = TransactionStatus.SUCCESS,
                transactionDate = "23 February 2024",
                itemImageUrl = "https://search.yahoo.com/search?p=dicta",
            ),
            TransactionModel(
                itemName = "Dwight Arnold",
                itemTotal = "2",
                itemPrice = "23000",
                transactionTotal = "Rp 23.000.000",
                transactionStatus = TransactionStatus.SUCCESS,
                transactionDate = "23 February 2024",
                itemImageUrl = "https://search.yahoo.com/search?p=dicta",
            ),
        )
        return Resource.Success(dummyResponse)
    }
}
