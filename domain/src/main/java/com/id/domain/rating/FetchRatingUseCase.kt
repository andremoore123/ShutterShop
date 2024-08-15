package com.id.domain.rating

import com.id.domain.utils.ErrorType
import com.id.domain.utils.network_response.onEmptyValueError
import com.id.domain.utils.network_response.onHttpError
import com.id.domain.utils.network_response.onSuccess
import com.id.domain.utils.network_response.onUnknownError
import com.id.domain.utils.resource.Resource
import javax.inject.Inject

/**
 * Created by: andre.
 * Date: 15/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
class FetchRatingUseCase @Inject constructor(
    private val ratingRepository: IRatingRepository
) {
    suspend operator fun invoke(productId: String): Resource<List<RatingModel>> {
        var result: Resource<List<RatingModel>> = Resource.Initiate
        val response = ratingRepository.fetchRatings(productId)
        response.onSuccess {
            result = if (it.isEmpty()) {
                Resource.Error(ErrorType.EmptyDataError)
            } else {
                Resource.Success(it)
            }
        }.onUnknownError {
            result = Resource.Error(ErrorType.UnknownError(it.message.toString()))
        }.onHttpError { code, message ->
            result = Resource.Error(ErrorType.HTTPError(code, message))
        }.onEmptyValueError {
            result = Resource.Error(ErrorType.EmptyDataError)
        }
        return result
    }
}