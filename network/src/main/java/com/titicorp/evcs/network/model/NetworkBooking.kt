package com.titicorp.evcs.network.model

data class NetworkBooking(
    val station: NetworkStation,
    val charger: NetworkCharger,
    val amount: Float,
    val startedAt: Long,
    val endAt: Long,
)
