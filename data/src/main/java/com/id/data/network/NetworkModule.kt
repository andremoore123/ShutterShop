package com.id.data.network

import com.id.data.auth.AuthApiService
import com.id.data.network.authenticator.UserAuthenticator
import com.id.data.network.interceptor.AuthInterceptor
import com.id.data.network.interceptor.SessionInterceptor
import com.id.domain.session.ISessionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.logging.HttpLoggingInterceptor

/**
 * Created by: andre.
 * Date: 12/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideAuthApiService(networkClient: NetworkClient) = networkClient.create<AuthApiService>()

    @Provides
    fun provideLoggingInterceptor() =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    fun provideAuthInterceptor(sessionRepository: ISessionRepository) =
        AuthInterceptor(sessionRepository)

    @Provides
    fun provideUserAuthenticator(
        loggingInterceptor: HttpLoggingInterceptor,
        sessionSource: ISessionRepository
    ) = UserAuthenticator(sessionSource, loggingInterceptor)

    @Provides
    fun provideSessionInterceptor(
        sessionSource: ISessionRepository
    ) = SessionInterceptor(sessionSource)

}