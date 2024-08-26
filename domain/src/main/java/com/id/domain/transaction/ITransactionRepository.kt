package com.id.domain.transaction

import com.id.domain.cart.CartModel
import com.id.domain.payment.PaymentType
import com.id.domain.utils.network_response.NetworkResponse

/**
 * Created by: andreputras.
 * Date: 05/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
interface ITransactionRepository {
    suspend fun checkout(
        paymentType: PaymentType,
        items: List<CartModel>
    ): NetworkResponse<CheckoutModel>

    suspend fun fetchTransaction(): NetworkResponse<List<TransactionModel>>
}