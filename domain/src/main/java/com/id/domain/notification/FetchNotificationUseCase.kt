package com.id.domain.notification

import com.id.domain.transaction.ITransactionRepository
import com.id.domain.transaction.TransactionModel
import com.id.domain.utils.ErrorType
import com.id.domain.utils.network_response.onEmptyValueError
import com.id.domain.utils.network_response.onHttpError
import com.id.domain.utils.network_response.onSuccess
import com.id.domain.utils.network_response.onUnknownError
import com.id.domain.utils.resource.Resource
import javax.inject.Inject

/**
 * Created by: andre.
 * Date: 16/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
class FetchNotificationUseCase @Inject constructor(
    private val transactionRepository: ITransactionRepository
) {
    suspend operator fun invoke(): Resource<List<NotificationModel>> {
        val mapper: (TransactionModel) -> NotificationModel = {
            NotificationModel(
                id = "0",
                title = "Transaksi Berhasil",
                subTitle = "Transaksi anda sedang di proses oleh penjual, mohon ditunggu untuk update selanjutnya di aplikasi. Sambil menunggu, anda bisa cari barang lain terlebih dahulu"
            )
        }

        var result: Resource<List<NotificationModel>> = Resource.Initiate
        val response = transactionRepository.fetchTransaction()
        response.onSuccess { transactionModels ->
            val dataList = transactionModels.map { mapper(it) }
            result = Resource.Success(dataList)
        }.onHttpError { code, message ->
            result = Resource.Error(ErrorType.HTTPError(code, message))
        }.onUnknownError {
            result = Resource.Error(ErrorType.UnknownError(it.message.toString()))
        }.onEmptyValueError {
            result = Resource.Error(ErrorType.EmptyDataError)
        }
        return result
    }
}