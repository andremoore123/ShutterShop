package com.id.shuttershop.di

import com.id.data.preference.PreferenceRepository
import com.id.domain.preference.IPreferenceRepository
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
}