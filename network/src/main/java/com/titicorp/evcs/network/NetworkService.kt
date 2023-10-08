package com.titicorp.evcs.network

import com.titicorp.evcs.network.model.NetworkStation
import com.titicorp.evcs.network.model.NetworkStationDetails
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkService {

    @GET("stations/nearby")
    suspend fun getNearbyStations(): List<NetworkStation>

    @GET("stations/saved")
    suspend fun getSavedStations(): List<NetworkStation>

    @GET("stations")
    suspend fun getStationDetails(
        @Query("id") id: String,
    ): NetworkStationDetails
}
