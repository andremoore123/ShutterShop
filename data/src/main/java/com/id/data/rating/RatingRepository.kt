package com.id.data.rating

import com.id.domain.rating.IRatingRepository
import com.id.domain.rating.RatingModel
import com.id.domain.utils.ErrorType
import com.id.domain.utils.resource.Resource
import retrofit2.HttpException
import javax.inject.Inject

/**
 * Created by: andre.
 * Date: 08/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
class RatingRepository @Inject constructor(
    private val apiService: RatingApiService
) : IRatingRepository {
    override suspend fun fetchRatings(productId: String): Resource<List<RatingModel>> {
        return Resource.Success(RatingModel.dummyList)
    }

    override suspend fun insertRating(
        invoiceId: String,
        rating: Int,
        review: String
    ): Resource<Boolean> {
        return try {
            val request = RatingRequest(invoiceId = invoiceId, rating = rating, review = review)
            apiService.sendRating(request)
            Resource.Success(true)
        } catch (e: HttpException) {
            Resource.Error(ErrorType.HTTPError(code = e.code(), e.message()))
        } catch (e: Exception) {
            Resource.Error(ErrorType.NetworkError(e.message.toString()))
        }
    }
}