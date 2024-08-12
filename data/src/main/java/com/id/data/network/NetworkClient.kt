package com.id.data.network

import com.id.data.BuildConfig
import com.id.data.network.authenticator.UserAuthenticator
import com.id.data.network.interceptor.AuthInterceptor
import com.id.data.network.interceptor.SessionInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by: andre.
 * Date: 12/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
class NetworkClient @Inject constructor(
    val loggingInterceptor: HttpLoggingInterceptor,
    val authInterceptor: AuthInterceptor,
    val sessionInterceptor: SessionInterceptor,
    val userAuthenticator: UserAuthenticator
) {
    inline fun <reified I> create(): I {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(sessionInterceptor)
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .authenticator(userAuthenticator)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(I::class.java)
    }
}