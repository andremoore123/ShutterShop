package com.id.domain.preference

import kotlinx.coroutines.flow.Flow

/**
 * Created by: andreputras.
 * Date: 30/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
interface IPreferenceRepository {
    val isDarkMode: Flow<Boolean>
    val isIndonesiaLanguage: Flow<Boolean>

    suspend fun setDarkMode()
    suspend fun setLanguage()

    suspend fun isOnboardShow(): Boolean
    suspend fun showOnBoard()
}