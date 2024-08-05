package com.id.domain.transaction

import com.id.domain.ext.Resource

/**
 * Created by: andreputras.
 * Date: 05/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
interface ITransactionRepository {
    suspend fun fetchTransaction(): Resource<List<TransactionModel>>
}