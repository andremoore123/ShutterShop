package com.id.data.auth

import com.id.data.auth.model.request.LoginRequest
import com.id.data.auth.model.request.RegisterRequest
import com.id.data.auth.model.response.toModel
import com.id.domain.auth.IAuthRepository
import com.id.domain.auth.model.AuthDataModel
import com.id.domain.utils.network_response.NetworkResponse
import javax.inject.Inject

/**
 * Created by: andreputras.
 * Date: 30/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
class AuthRepository @Inject constructor(
    private val authApiService: AuthApiService
) : IAuthRepository {
    override suspend fun login(email: String, password: String): NetworkResponse<AuthDataModel> {
        return try {
            val loginRequest = LoginRequest(email, password, "")
            val response = authApiService.login(loginRequest).data
            response?.run {
                NetworkResponse.Success(toModel())
            } ?: throw NullPointerException()
        } catch (e: Exception) {
            NetworkResponse.UnknownError(e)
        }
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String,
    ): NetworkResponse<AuthDataModel> {
        return try {
            val registerRequest = RegisterRequest(email, password, "")
            val response = authApiService.register(registerRequest).data
            response?.run {
                NetworkResponse.Success(toModel())
            } ?: throw NullPointerException()
        } catch (e: Exception) {
            NetworkResponse.UnknownError(e)
        }
    }
}