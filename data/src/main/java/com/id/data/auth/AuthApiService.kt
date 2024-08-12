package com.id.data.auth

import com.id.data.auth.model.request.LoginRequest
import com.id.data.auth.model.request.RefreshRequest
import com.id.data.auth.model.request.RegisterRequest
import com.id.data.auth.model.response.AuthResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * Created by: andre.
 * Date: 12/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
interface AuthApiService {
    @POST("login")
    suspend fun login(
        @Body body: LoginRequest
    ): AuthResponse

    @POST("register")
    suspend fun register(
        @Body body: RegisterRequest
    ): AuthResponse


    @POST("refresh")
    suspend fun refresh(
        @Body body: RefreshRequest
    ): AuthResponse
}