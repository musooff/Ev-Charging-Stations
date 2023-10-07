package com.titicorp.evcs.data.repository

import com.titicorp.evcs.model.Station
import com.titicorp.evcs.network.NetworkApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class StationRepository @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val api: NetworkApi,
) {

    fun getNearbyStations(): Flow<List<Station>> = flow {
        emit(
            api.getNearbyStations()
                .map {
                    Station(
                        id = it.id,
                        title = it.title,
                        address = it.address,
                        lat = it.lat,
                        lng = it.lng,
                        thumbnail = it.thumbnail,
                    )
                },
        )
    }.flowOn(ioDispatcher)

    fun getSavedStations(): Flow<List<Station>> = flow {
        emit(
            api.getSavedStations()
                .map {
                    Station(
                        id = it.id,
                        title = it.title,
                        address = it.address,
                        lat = it.lat,
                        lng = it.lng,
                        thumbnail = it.thumbnail,
                    )
                },
        )
    }.flowOn(ioDispatcher)
}
