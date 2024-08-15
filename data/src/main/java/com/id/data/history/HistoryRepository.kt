package com.id.data.history

import com.id.domain.history.HistoryModel
import com.id.domain.history.IHistoryRepository
import com.id.domain.utils.resource.Resource
import javax.inject.Inject

/**
 * Created by: andre.
 * Date: 08/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
class HistoryRepository @Inject constructor(

) : IHistoryRepository {
    override suspend fun fetchHistories(): Resource<List<HistoryModel>> {
        val dummyData = listOf(
            HistoryModel(id = "novum", title = "duis", subTitle = "populo"), HistoryModel(
                id = "noster",
                title = "evertitur",
                subTitle = "invenire"
            )
        )
        return Resource.Success(dummyData)
    }

    override suspend fun insertHistory(data: HistoryModel): Resource<Boolean> {
        return Resource.Success(true)
    }
}