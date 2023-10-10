package com.titicorp.evcs.network

import com.titicorp.evcs.network.model.Auth
import com.titicorp.evcs.network.model.NetworkStation
import com.titicorp.evcs.network.model.NetworkStationDetails
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
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

    @POST("auth/login")
    suspend fun login(
        @Body body: Auth.LoginRequest,
    ): Auth.Result

    @POST("auth/register")
    suspend fun register(
        @Body body: Auth.RegisterRequest,
    ): Auth.Result
}
