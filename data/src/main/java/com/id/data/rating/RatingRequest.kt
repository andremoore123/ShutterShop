package com.id.data.rating

import com.google.gson.annotations.SerializedName

/**
 * Created by: andre.
 * Date: 15/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
data class RatingRequest(
    @SerializedName("invoiceId")
    val invoiceId: String,
    @SerializedName("rating")
    val rating: Int,
    @SerializedName("review")
    val review: String,
)