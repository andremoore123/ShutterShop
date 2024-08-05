package com.id.data.session

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.id.domain.session.ISessionRepository
import com.id.domain.session.UserModel
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
class SessionRepository @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : ISessionRepository {
    private val userToken = stringPreferencesKey(USER_TOKEN)
    private val userEmailValue = stringPreferencesKey(USER_EMAIL)
    private val userNameValue = stringPreferencesKey(USER_NAME)

    override fun isUserLogin(): Flow<Boolean?> = dataStore.data.map {
        it[userToken]?.isNotEmpty()
    }

    override suspend fun setUserData(user: UserModel) {
        dataStore.edit {
            it[userNameValue] = user.name
            it[userEmailValue] = user.email
        }
    }

    override suspend fun fetchUserData(): UserModel {
        val data = dataStore.data.first()
        val email = data[userEmailValue] ?: ""
        val name = data[userNameValue] ?: ""
        return UserModel(name = name, email = email)
    }

    override suspend fun fetchUserToken(): String = dataStore.data.first()[userToken] ?: ""

    override suspend fun insertUserToken(token: String) {
        dataStore.edit {
            it[userToken] = token
        }
    }

    override suspend fun clearUserSession() {
        dataStore.edit {
            it[userToken] = ""
            it[userNameValue] = ""
            it[userEmailValue] = ""
        }
    }

    companion object {
        private const val USER_TOKEN = "userToken"
        private const val USER_NAME = "userName"
        private const val USER_EMAIL = "userEmail"
    }
}