package com.id.domain.rating

import com.id.domain.utils.network_response.NetworkResponse
import com.id.domain.utils.resource.Resource

/**
 * Created by: andre.
 * Date: 08/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
interface IRatingRepository {
    suspend fun fetchRatings(productId: String): NetworkResponse<List<RatingModel>>
    suspend fun insertRating(invoiceId: String, rating: Int, review: String): Resource<Boolean>
}