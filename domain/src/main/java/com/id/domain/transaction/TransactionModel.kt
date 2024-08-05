package com.id.domain.transaction

/**
 * Created by: andreputras.
 * Date: 05/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
data class TransactionModel(
    val itemName: String,
    val itemTotal: String,
    val itemPrice: String,
    val itemImageUrl: String,
    val transactionTotal: String,
    val transactionStatus: TransactionStatus,
    val transactionDate: String,
)
