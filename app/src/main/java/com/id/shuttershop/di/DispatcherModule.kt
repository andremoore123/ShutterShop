package com.id.shuttershop.di

import com.id.shuttershop.utils.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by: andre.
 * Date: 19/08/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */

@Module
@InstallIn(SingletonComponent::class)
class DispatcherModule {
    @Provides
    fun provideDispatcherProvider(): DispatcherProvider = DispatcherProvider()
}