package com.id.domain.transaction

/**
 * Created by: andreputras.
 * Date: 05/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
data class TransactionModel(
    val transactionId: String = "",
    val itemName: String,
    val itemTotal: Int,
    val itemImageUrl: String,
    val transactionTotal: Int,
    val transactionStatus: TransactionStatus,
    val transactionDate: String,
    val itemStatus: ItemStatus,
    val time: String = "",
    val review: String = "",
    val rating: Int = 0,
    val paymentName: String = "",
)

fun TransactionModel.mapToCheckoutModel() = CheckoutModel(
    invoiceId = transactionId,
    date = transactionDate,
    time = time,
    paymentName = paymentName,
    total = transactionTotal,
    rating = rating,
    review = review
)
