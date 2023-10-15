package com.titicorp.evcs.network.model

data class NetworkStation(
    val id: String,
    val title: String,
    val address: String,
    val lat: Double,
    val lng: Double,
    val thumbnail: String?,
    val chargers: List<NetworkCharger>,
)
