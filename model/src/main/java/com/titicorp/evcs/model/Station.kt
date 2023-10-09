package com.titicorp.evcs.model

data class Station(
    val id: String,
    val title: String,
    val address: String,
    val lat: Double,
    val lng: Double,
    val thumbnail: String?,
)
