package com.titicorp.evcs.model

data class Booking(
    val station: Station,
    val charger: Charger,
    val amount: Float,
    val startedAt: Long,
    val endAt: Long,
)
