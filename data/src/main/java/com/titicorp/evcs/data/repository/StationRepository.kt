package com.titicorp.evcs.data.repository

import com.titicorp.evcs.data.repository.utils.toBooking
import com.titicorp.evcs.data.repository.utils.toStation
import com.titicorp.evcs.data.repository.utils.toStationDetails
import com.titicorp.evcs.model.Booking
import com.titicorp.evcs.model.Station
import com.titicorp.evcs.model.StationDetails
import com.titicorp.evcs.network.NetworkApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class StationRepository @Inject constructor(
    private val ioDispatcher: CoroutineDispatcher,
    private val api: NetworkApi,
) {

    suspend fun getStations(): List<Station> =
        withContext(ioDispatcher) {
            api.getStations()
                .map { it.toStation }
        }

    suspend fun getSavedStations(): List<Station> =
        withContext(ioDispatcher) {
            api.getSavedStations()
                .map { it.toStation }
        }

    suspend fun getMyBookings(): List<Booking> =
        withContext(ioDispatcher) {
            api.getMyBookings().map { it.toBooking }
        }

    fun getStationDetails(id: String): Flow<StationDetails> = flow {
        emit(
            api.getStationDetails(id).toStationDetails,
        )
    }.flowOn(ioDispatcher)
}
