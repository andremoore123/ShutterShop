package com.id.domain.payment

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Created by: andre.
 * Date: 09/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@Parcelize
data class PaymentModel(
    val idPayment: Int,
    val paymentName: String,
    val paymentImageUrl: String,
    val paymentType: PaymentType,
) : Parcelable
