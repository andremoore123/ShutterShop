package com.id.data.rating

import com.id.domain.rating.IRatingRepository
import com.id.domain.rating.RatingModel
import com.id.domain.utils.ErrorType
import com.id.domain.utils.network_response.NetworkResponse
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
    override suspend fun fetchRatings(productId: String): NetworkResponse<List<RatingModel>> {
        return try {
            val response = apiService.fetchProductReview(productId).data
            response?.let { ratingResponses ->
                NetworkResponse.Success(ratingResponses.map { it.mapToModel() })
            } ?: throw NullPointerException()
        } catch (e: NullPointerException) {
            NetworkResponse.EmptyValueError
        } catch (e: HttpException) {
            NetworkResponse.HttpError(e.code(), e.message())
        } catch (e: Exception) {
            NetworkResponse.UnknownError(e)
        }
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