package com.id.data.transaction

import com.id.domain.ext.Resource
import com.id.domain.transaction.ITransactionRepository
import com.id.domain.transaction.TransactionModel
import com.id.domain.transaction.TransactionStatus
import javax.inject.Inject

/**
 * Created by: andreputras.
 * Date: 05/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
class TransactionRepository @Inject constructor(

) : ITransactionRepository {
    override suspend fun fetchTransaction(): Resource<List<TransactionModel>> {
        val dummyResponse = listOf(
            TransactionModel(
                itemName = "Dwight Arnold",
                itemTotal = "2",
                itemPrice = "23000",
                transactionTotal = "Rp 23.000.000",
                transactionStatus = TransactionStatus.FAILED,
                transactionDate = "23 February 2024",
                itemImageUrl = "https://search.yahoo.com/search?p=dicta",
            ),
            TransactionModel(
                itemName = "Dwight Arnold",
                itemTotal = "2",
                itemPrice = "23000",
                transactionTotal = "Rp 23.000.000",
                transactionStatus = TransactionStatus.SUCCESS,
                transactionDate = "23 February 2024",
                itemImageUrl = "https://search.yahoo.com/search?p=dicta",
            ),
            TransactionModel(
                itemName = "Dwight Arnold",
                itemTotal = "2",
                itemPrice = "23000",
                transactionTotal = "Rp 23.000.000",
                transactionStatus = TransactionStatus.SUCCESS,
                transactionDate = "23 February 2024",
                itemImageUrl = "https://search.yahoo.com/search?p=dicta",
            ),
        )
        return Resource.Success(dummyResponse)
    }
}
