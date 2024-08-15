package com.id.data.rating

import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by: andre.
 * Date: 15/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
interface RatingApiService {
    @POST("rating")
    suspend fun sendRating(
        @Body request: RatingRequest
    )
}