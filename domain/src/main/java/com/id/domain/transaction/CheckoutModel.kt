package com.id.domain.transaction

/**
 * Created by: andre.
 * Date: 14/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
data class CheckoutModel(
    val invoiceId: String,
    val date: String,
    val time: String,
    val paymentName: String,
    val total: Int,
)
