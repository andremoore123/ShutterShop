package com.id.data.di

import com.id.data.analytic.AnalyticRepository
import com.id.data.auth.AuthRepository
import com.id.data.preference.PreferenceRepository
import com.id.data.session.SessionRepository
import com.id.domain.analytic.IAnalyticRepository
import com.id.domain.auth.IAuthRepository
import com.id.domain.preference.IPreferenceRepository
import com.id.domain.session.ISessionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

/**
 * Created by: andreputras.
 * Date: 30/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {
    @Binds
    abstract fun providePreferenceRepository(repository: PreferenceRepository): IPreferenceRepository

    @Binds
    abstract fun provideSessionRepository(repository: SessionRepository): ISessionRepository

    @Binds
    abstract fun provideAuthRepository(repository: AuthRepository): IAuthRepository

    @Binds
    abstract fun provideAnalyticRepository(repository: AnalyticRepository): IAnalyticRepository
}