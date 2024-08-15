package com.id.domain.transaction

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
class FetchTransactionsUseCase @Inject constructor(
    private val transactionRepository: ITransactionRepository
) {
    suspend operator fun invoke(): Resource<List<TransactionModel>> {
        var result: Resource<List<TransactionModel>> = Resource.Initiate

        val response = transactionRepository.fetchTransaction()
        response.onSuccess {
            result = Resource.Success(it)
        }.onHttpError { code, message ->
            result = Resource.Error(ErrorType.HTTPError(code, message))
        }.onUnknownError {
            result = Resource.Error(ErrorType.UnknownError(it.message.toString()))
        }
        return result
    }
}