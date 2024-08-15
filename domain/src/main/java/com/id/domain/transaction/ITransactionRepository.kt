package com.id.domain.transaction

import com.id.domain.cart.CartModel
import com.id.domain.ext.Resource
import com.id.domain.payment.PaymentType

/**
 * Created by: andreputras.
 * Date: 05/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
interface ITransactionRepository {
    suspend fun checkout(paymentType: PaymentType, items: List<CartModel>): Resource<CheckoutModel>
    suspend fun fetchTransaction(): Resource<List<TransactionModel>>
}