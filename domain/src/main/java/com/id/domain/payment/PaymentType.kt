package com.id.domain.payment

/**
 * Created by: andre.
 * Date: 09/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
enum class PaymentType(val title: String, val value: String = "") {
    VIRTUAL_ACCOUNT("Transfer Virtual Account", "Transfer Virtual Account"),
    INSTANT_PAYMENT("Instant Payment", "Pembayaran Instan"),
    TRANSFER_BANK("Transfer Bank","Transfer Bank"),
    OTHERS("Others", "")
}