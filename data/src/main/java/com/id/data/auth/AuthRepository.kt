package com.id.data.auth

import com.id.domain.ext.NetworkResponse
import com.id.domain.auth.IAuthRepository
import javax.inject.Inject

/**
 * Created by: andreputras.
 * Date: 30/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
class AuthRepository @Inject constructor(

) : IAuthRepository {
    override suspend fun login(email: String, password: String): NetworkResponse<String> {
        return NetworkResponse.Success("Success")
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String,
    ): NetworkResponse<String> {
        return NetworkResponse.Success("Success")
    }
}