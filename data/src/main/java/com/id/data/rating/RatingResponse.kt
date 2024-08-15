package com.id.data.rating

import com.google.gson.annotations.SerializedName
import com.id.domain.rating.RatingModel

data class RatingResponse(

    @field:SerializedName("userImage")
    val userImage: String? = null,

    @field:SerializedName("userName")
    val userName: String? = null,

    @field:SerializedName("userReview")
    val userReview: String? = null,

    @field:SerializedName("userRating")
    val userRating: Int? = null
)

fun RatingResponse.mapToModel() = RatingModel(
    id = 0,
    userName = userName ?: "",
    userImageUrl = userImage ?: "",
    ratingProduct = userRating ?: 0,
    ratingDescription = userReview ?: ""
)