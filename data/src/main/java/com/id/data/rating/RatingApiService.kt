package com.id.data.rating

import com.id.data.network.ApiResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

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

    @GET("review/{productId}")
    suspend fun fetchProductReview(
        @Path("productId") productId: String
    ): ApiResponse<List<RatingResponse>>
}