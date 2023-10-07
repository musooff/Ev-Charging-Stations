package com.titicorp.evcs.network

import com.titicorp.evcs.network.model.NetworkStation
import retrofit2.http.GET

interface NetworkService {

    @GET("stations/nearby")
    suspend fun getNearbyStations(): List<NetworkStation>

    @GET("stations/saved")
    suspend fun getSavedStations(): List<NetworkStation>
}
