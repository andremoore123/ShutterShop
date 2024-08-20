package com.id.domain.transaction

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by: andre.
 * Date: 14/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
@Parcelize
data class CheckoutModel(
    val invoiceId: String,
    val date: String,
    val time: String,
    val paymentName: String,
    val total: Int,
    val rating: Int = 0,
    val review: String = "",
): Parcelable
