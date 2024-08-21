package com.id.domain.product

import com.id.domain.utils.ErrorType
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
class FetchProductDetailUseCase @Inject constructor(
    private val productRepository: IProductRepository
) {
    suspend operator fun invoke(id: String): Resource<ProductDetailModel> {
        var result: Resource<ProductDetailModel> = Resource.Initiate
        val response = productRepository.fetchProductDetail(id)
        response.onSuccess {
            result = Resource.Success(it)
        }.onUnknownError {
            result = Resource.Error(ErrorType.UnknownError(it.message.toString()))
        }.onHttpError { code, message ->
            result = Resource.Error(ErrorType.HTTPError(code, message))
        }
        return result
    }
}