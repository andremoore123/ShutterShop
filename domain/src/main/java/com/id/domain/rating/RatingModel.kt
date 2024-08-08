package com.id.domain.rating

/**
 * Created by: andre.
 * Date: 08/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
data class RatingModel(
    val id: Int,
    val userName: String,
    val userImageUrl: String,
    val ratingProduct: Int,
    val ratingDescription: String,
) {
    companion object {
        val emptyData = RatingModel(
            id = 0, userName = "andremoore", userImageUrl = "", ratingProduct = 3, ratingDescription = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book."
        )
        val dummyList = listOf(
            emptyData,
            emptyData,
            emptyData,
            emptyData,
        )
    }
}
