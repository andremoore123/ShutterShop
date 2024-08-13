package com.id.data.rating

import com.id.domain.ext.Resource
import com.id.domain.rating.IRatingRepository
import com.id.domain.rating.RatingModel
import javax.inject.Inject

/**
 * Created by: andre.
 * Date: 08/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
class RatingRepository @Inject constructor(

) : IRatingRepository {
    override suspend fun fetchRatings(productId: String): Resource<List<RatingModel>> {
        return Resource.Success(RatingModel.dummyList)
    }

    override suspend fun insertRating(data: RatingModel): Resource<Boolean> {
        return Resource.Success(true)
    }
}