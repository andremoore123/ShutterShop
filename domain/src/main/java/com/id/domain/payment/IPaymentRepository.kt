package com.id.domain.payment

import kotlinx.coroutines.flow.Flow

/**
 * Created by: andre.
 * Date: 09/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
interface IPaymentRepository {
    fun fetchPaymentMethods(): Flow<List<PaymentModel>>
}