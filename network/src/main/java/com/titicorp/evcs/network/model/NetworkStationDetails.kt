package com.titicorp.evcs.network.model

data class NetworkStationDetails(
    val id: String,
    val title: String,
    val description: String,
    val address: String,
    val lat: Double,
    val lng: Double,
    val thumbnail: String?,
    val chargers: List<String>,
    val reviews: List<String>,
)
