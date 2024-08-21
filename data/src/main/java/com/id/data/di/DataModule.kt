package com.id.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.id.data.AppDatabase
import com.id.data.session.SessionRepository
import com.id.domain.session.ISessionRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by: andreputras.
 * Date: 30/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "shutterShopDataStore")

@Module
@InstallIn(SingletonComponent::class)
object DataModule {
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        context.dataStore

    @Provides
    @Singleton
    fun provideSessionRepository(@ApplicationContext context: Context): ISessionRepository =
        SessionRepository(context.dataStore)

    @Provides
    @Singleton
    fun provideFirebaseAnalytics(@ApplicationContext context: Context) = FirebaseAnalytics.getInstance(context)

    @Provides
    @Singleton
    fun provideFirebaseRemoteConfig() = FirebaseRemoteConfig.getInstance()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context) = AppDatabase.buildDatabase(context)

    @Provides
    fun provideWishlistDao(appDatabase: AppDatabase) = appDatabase.wishlistDao()

    @Provides
    fun provideCartDao(appDatabase: AppDatabase) = appDatabase.cartDao()
}