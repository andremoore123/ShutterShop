package com.id.domain.preference

/**
 * Created by: andreputras.
 * Date: 30/07/24
 * Name: Andre Eka Putra Simanjuntak
 * Email: andremoore431@gmail.com
 */
interface IPreferenceRepository {
    suspend fun isOnboardShow(): Boolean
    suspend fun showOnBoard()
}