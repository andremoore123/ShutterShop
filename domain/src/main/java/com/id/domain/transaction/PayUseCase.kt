package com.id.domain.transaction

import com.id.domain.cart.CartModel
import com.id.domain.cart.ICartRepository
import com.id.domain.ext.Resource
import com.id.domain.payment.PaymentModel
import kotlinx.coroutines.delay
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
    ): Resource<TransactionModel> {
        delay(2_00)
        cartRepository.deleteAllCart()
        return Resource.Success(
            TransactionModel(
                itemName = "Roosevelt Chase",
                itemTotal = "pretium",
                itemPrice = "habeo",
                itemImageUrl = "https://search.yahoo.com/search?p=habitant",
                transactionTotal = "hac",
                transactionStatus = TransactionStatus.FAILED,
                transactionDate = "gubergren"
            )
        )
    }
}