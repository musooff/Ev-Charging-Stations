package com.titicorp.evcs.network.model

import java.util.UUID

data class NetworkStation(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val address: String,
    val lat: Double,
    val lng: Double,
    val thumbnail: String?,
)
