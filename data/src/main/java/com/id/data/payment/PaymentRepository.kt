package com.id.data.payment

import com.id.domain.payment.IPaymentRepository
import com.id.domain.payment.PaymentModel
import com.id.domain.payment.PaymentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by: andre.
 * Date: 09/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
class PaymentRepository @Inject constructor(
) : IPaymentRepository {
    override fun fetchPaymentMethods(): Flow<List<PaymentModel>> = flow {
        val dummyData = listOf(
            PaymentModel(
                idPayment = 4681,
                paymentName = "Amelia Benjamin",
                paymentImageUrl = "https://duckduckgo.com/?q=tortor",
                paymentType = PaymentType.INSTANT_PAYMENT
            ),
            PaymentModel(
                idPayment = 4681,
                paymentName = "Amelia Benjamin",
                paymentImageUrl = "https://duckduckgo.com/?q=tortor",
                paymentType = PaymentType.INSTANT_PAYMENT
            ),
        )
        emit(dummyData)
    }
}