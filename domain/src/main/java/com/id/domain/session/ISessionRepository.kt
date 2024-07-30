package com.id.domain.session

import kotlinx.coroutines.flow.Flow

/**
 * Created by: andreputras.
 * Date: 30/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
interface ISessionRepository {
    fun isUserLogin(): Flow<Boolean?>

    suspend fun fetchUserToken(): String
    suspend fun insertUserToken(token: String)
    suspend fun clearUserSession()
}