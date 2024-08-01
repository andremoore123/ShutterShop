package com.id.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.id.domain.preference.IPreferenceRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * Created by: andreputras.
 * Date: 30/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
class PreferenceRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>
): IPreferenceRepository {
    private val onBoarding = booleanPreferencesKey(ON_BOARDING)
    private val isDarkModeStatus = booleanPreferencesKey(DARK_MODE)
    private val isIndonesiaLanguageStatus = booleanPreferencesKey(INDONESIA_LANGUAGE)

    override val isDarkMode: Flow<Boolean> = dataStore.data.map {
        it[isDarkModeStatus] ?: false
    }
    override val isIndonesiaLanguage: Flow<Boolean> = dataStore.data.map {
        it[isIndonesiaLanguageStatus] ?: false
    }

    override suspend fun setDarkMode() {
        val currentValue = dataStore.data.first()[isDarkModeStatus] ?: false
        dataStore.edit {
            it[isDarkModeStatus] = currentValue.not()
        }
    }

    override suspend fun setLanguage() {
        val currentValue = dataStore.data.first()[isIndonesiaLanguageStatus] ?: false
        dataStore.edit {
            it[isIndonesiaLanguageStatus] = currentValue.not()
        }
    }

    override suspend fun isOnboardShow(): Boolean = dataStore.data.first()[onBoarding] ?: false
    override suspend fun showOnBoard() {
        dataStore.edit {
            it[onBoarding] = it[onBoarding]?.not() ?: true
        }
    }

    companion object {
        const val ON_BOARDING = "onBoard"
        const val DARK_MODE = "darkMode"
        const val INDONESIA_LANGUAGE = "indonesiaLanguage"
    }
}