package com.id.data.auth

import com.id.data.auth.model.request.LoginRequest
import com.id.data.auth.model.request.RefreshRequest
import com.id.data.auth.model.request.RegisterRequest
import com.id.data.auth.model.response.AuthResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

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

    @Multipart
    @POST("profile")
    suspend fun updateProfile(
        @Part("userName") userName: RequestBody,
    )
}