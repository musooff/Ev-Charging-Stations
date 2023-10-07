package com.titicorp.evcs.data.repository.fake

import com.titicorp.evcs.data.repository.StationRepository
import com.titicorp.evcs.model.Station
import com.titicorp.evcs.network.fake.FakeNetworkDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FakeStationRepository @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val dataSource: FakeNetworkDataSource,
) : StationRepository {
    override fun getNearbyStations(): Flow<List<Station>> = flow {
        emit(
            dataSource.getNearbyStations()
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

    override fun getSavedStations(): Flow<List<Station>> = flow {
        emit(
            dataSource.getSavedStations()
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
