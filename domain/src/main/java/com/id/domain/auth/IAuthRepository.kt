package com.id.domain.auth

import com.id.domain.auth.model.AuthDataModel
import com.id.domain.utils.network_response.NetworkResponse

/**
 * Created by: andreputras.
 * Date: 30/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
interface IAuthRepository {
    suspend fun login(email: String, password: String): NetworkResponse<AuthDataModel>
    suspend fun register(name: String, email: String, password: String): NetworkResponse<AuthDataModel>
}