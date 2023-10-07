package com.titicorp.evcs.network

import com.titicorp.evcs.network.model.NetworkStation

interface NetworkDataSource {

    suspend fun getNearbyStations(): List<NetworkStation>

    suspend fun getSavedStations(): List<NetworkStation>
}
