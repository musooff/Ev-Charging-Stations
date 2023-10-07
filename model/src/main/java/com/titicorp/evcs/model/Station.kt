package com.titicorp.evcs.model

import java.util.UUID

data class Station(
    val id: String,
    val title: String,
    val address: String,
    val lat: Double,
    val lng: Double,
    val thumbnail: String?,
) {
    companion object {
        fun byId(): Station {
            return Station(
                id = UUID.randomUUID().toString(),
                title = "First Birmingham Station",
                address = "Park Avenue 110",
                lat = 38.574106716422506,
                lng = 68.78535232665297,
                thumbnail = null,
            )
        }
    }
}
