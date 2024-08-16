package com.id.data.network.authenticator

import com.id.data.BuildConfig
import com.id.data.auth.AuthApiService
import com.id.data.auth.model.request.RefreshRequest
import com.id.data.auth.model.response.AuthResponse
import com.id.domain.session.ISessionRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class UserAuthenticator(
    private val sessionSource: ISessionRepository,
    private val loggingInterceptor: HttpLoggingInterceptor
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val newRequest = response.request
        synchronized(this) {
            return runBlocking {
                val newResponse = refreshToken()
                if (newResponse != null) {
                    val newRefreshToken = newResponse.data?.refreshToken ?: ""
                    val newAccessToken = newResponse.data?.accessToken ?: ""
                    sessionSource.insertUserToken(newAccessToken, newRefreshToken)
                    newRequest
                        .newBuilder()
                        .header("Authorization", "Bearer $newAccessToken")
                        .build()
                } else {
                    null
                }
            }
        }
    }

    private suspend fun refreshToken(): AuthResponse? {
        val interceptor = Interceptor.invoke { chain ->
            val request = chain.request().newBuilder()
                .header("API_KEY", BuildConfig.API_KEY)
                .build()
            chain.proceed(request)
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()

        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val refreshService = retrofit.create(AuthApiService::class.java)

        try {
            val refreshToken = sessionSource.fetchUserRefreshToken()
            val bodyRefreshToken = RefreshRequest(refreshToken)
            val newResponse = refreshService.refresh(bodyRefreshToken)
            return newResponse
        } catch (e: Exception) {
            return null
        }
    }
}