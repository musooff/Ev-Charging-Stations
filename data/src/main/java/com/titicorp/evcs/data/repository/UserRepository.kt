package com.titicorp.evcs.data.repository

import com.titicorp.evcs.datastore.PreferencesDataStore
import com.titicorp.evcs.network.NetworkApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val api: NetworkApi,
    private val dataStore: PreferencesDataStore,
) {

    suspend fun login(phoneNumber: String, password: String) =
        withContext(ioDispatcher) {
            val result = api.login(phoneNumber, password)
            dataStore.setUser(
                name = result.name,
                phoneNumber = result.phoneNumber,
            )
        }

    suspend fun register(name: String, phoneNumber: String, password: String) =
        withContext(ioDispatcher) {
            val result = api.register(name, phoneNumber, password)
            dataStore.setUser(
                name = result.name,
                phoneNumber = result.phoneNumber,
            )
        }
    suspend fun getUserName(): String {
        return dataStore.getUserName().first()
    }

    suspend fun isLoggedIn(): Boolean {
        return dataStore.isLoggedIn().first()
    }

    suspend fun logOut() {
        dataStore.removeUser()
    }
}
