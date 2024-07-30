package com.id.data.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.id.domain.preference.IPreferenceRepository
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
    override suspend fun isOnboardShow(): Boolean = dataStore.data.first()[onBoarding] ?: false
    override suspend fun showOnBoard() {
        dataStore.edit {
            it[onBoarding] = it[onBoarding]?.not() ?: true
        }
    }

    companion object {
        const val ON_BOARDING = "onBoard"
    }
}