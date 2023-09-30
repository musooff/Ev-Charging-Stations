package com.titicorp.evcs.model

import androidx.compose.runtime.Immutable
import java.util.UUID

@Immutable
data class Station(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val address: String,
    val lat: Double,
    val lng: Double,
    val thumbnail: String?,
) {
    companion object {
        val Sample = Station(
            title = "Birmingham Station",
            address = "Park Avenue 110",
            lat = 38.574106716422506,
            lng = 68.78535232665297,
            thumbnail = null
        )
        val Nearby = listOf(
            Station(
                title = "Birmingham Station",
                address = "Park Avenue 110",
                lat = 38.574106716422506,
                lng = 68.78535232665297,
                thumbnail = null
            ),
            Station(
                title = "Birmingham Station",
                address = "Park Avenue 110",
                lat = 38.58096931688277,
                lng = 68.7863539751907,
                thumbnail = null
            ),
            Station(
                title = "Birmingham Station",
                address = "Park Avenue 110",
                lat = 38.578661705786544,
                lng = 68.78273075220778,
                thumbnail = null
            )
        )
    }
}
