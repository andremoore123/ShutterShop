package com.id.domain.rating

import com.id.domain.ext.Resource

/**
 * Created by: andre.
 * Date: 08/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
interface IRatingRepository {
    suspend fun fetchRatings(productId: Int): Resource<List<RatingModel>>
    suspend fun insertRating(data: RatingModel): Resource<Boolean>
}