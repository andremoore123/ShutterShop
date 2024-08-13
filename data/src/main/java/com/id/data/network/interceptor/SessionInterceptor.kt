package com.id.data.network.interceptor

import com.id.domain.session.ISessionRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class SessionInterceptor(
    private val session: ISessionRepository
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)

        if (response.code == 401) {
            runBlocking {
                session.clearUserSession()
            }
        }
        return chain.proceed(request)
    }
}
