package com.titicorp.evcs.model

data class StationDetails(
    val id: String,
    val title: String,
    val description: String,
    val address: String,
    val lat: Double,
    val lng: Double,
    val thumbnail: String?,
    val chargers: List<Charger>,
    val reviews: List<String>,
)
