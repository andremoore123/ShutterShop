package com.id.domain.history

import com.id.domain.utils.resource.Resource

/**
 * Created by: andre.
 * Date: 08/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
interface IHistoryRepository {
    suspend fun fetchHistories(): Resource<List<HistoryModel>>

    suspend fun insertHistory(data: HistoryModel): Resource<Boolean>
}