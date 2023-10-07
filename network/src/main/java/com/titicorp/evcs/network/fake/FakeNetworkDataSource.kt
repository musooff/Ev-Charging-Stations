package com.titicorp.evcs.network.fake

import com.titicorp.evcs.network.NetworkDataSource
import com.titicorp.evcs.network.model.NetworkStation
import javax.inject.Inject

class FakeNetworkDataSource @Inject constructor() : NetworkDataSource {

    override suspend fun getNearbyStations(): List<NetworkStation> {
        return Samples
    }

    override suspend fun getSavedStations(): List<NetworkStation> {
        return Samples
    }

    companion object {
        val Samples = listOf(
            NetworkStation(
                title = "First Birmingham Station",
                address = "Park Avenue 110",
                lat = 38.574106716422506,
                lng = 68.78535232665297,
                thumbnail = null,
            ),
            NetworkStation(
                title = "Second Birmingham Station",
                address = "Park Avenue 110",
                lat = 38.578661705786544,
                lng = 68.78273075220778,
                thumbnail = null,
            ),
            NetworkStation(
                title = "Third Birmingham Station",
                address = "Park Avenue 110",
                lat = 38.58096931688277,
                lng = 68.7863539751907,
                thumbnail = null,
            ),

            NetworkStation(
                title = "Forth Birmingham Station",
                address = "Park Avenue 110",
                lat = 38.59096931688277,
                lng = 68.7863539751907,
                thumbnail = null,
            ),
        )
    }
}
