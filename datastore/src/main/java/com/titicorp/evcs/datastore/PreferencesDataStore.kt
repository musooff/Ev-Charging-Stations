package com.titicorp.evcs.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class PreferencesDataStore @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) {

    private val userNamePreferences = stringPreferencesKey(USER_NAME)

    suspend fun setUser(name: String, phoneNumber: String) {
        dataStore.edit {
            it[userNamePreferences] = name
        }
    }

    fun getUserName(): Flow<String> {
        return dataStore.data.mapNotNull { it[userNamePreferences] }
    }

    fun isLoggedIn(): Flow<Boolean> {
        return dataStore.data.map { it.contains(userNamePreferences) }
    }

    companion object {
        private const val USER_NAME = "user_name"
    }
}
