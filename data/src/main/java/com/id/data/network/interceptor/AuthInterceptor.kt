package com.id.data.network.interceptor

import com.id.data.BuildConfig
import com.id.data.network.PathSegments
import com.id.domain.session.ISessionRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val session: ISessionRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val path = chain.request().url.pathSegments.first()
        val authPathList =
            PathSegments.entries.map { it.path }

        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("Content-Type", "application/json")

        if (authPathList.contains(path)) {
            requestBuilder.addHeader("API_KEY", BuildConfig.API_KEY)
            return chain.proceed(requestBuilder.build())
        }

        val token = runBlocking {
            session.fetchUserToken().first()
        }
        requestBuilder.addHeader("Authorization", "Bearer $token")
        return chain.proceed(requestBuilder.build())
    }
}