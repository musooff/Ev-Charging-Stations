package com.titicorp.evcs.data.repository

import com.titicorp.evcs.model.User
import com.titicorp.evcs.network.NetworkApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val api: NetworkApi,
) {

    suspend fun login(phoneNumber: String, password: String): User =
        withContext(ioDispatcher) {
            val result = api.login(phoneNumber, password)
            User(
                name = result.name,
                phoneNumber = result.phoneNumber,
            )
        }

    suspend fun register(name: String, phoneNumber: String, password: String): User =
        withContext(ioDispatcher) {
            val result = api.register(name, phoneNumber, password)
            User(
                name = result.name,
                phoneNumber = result.phoneNumber,
            )
        }
}
