package com.titicorp.evcs.domain

import com.titicorp.evcs.data.repository.StationRepository
import com.titicorp.evcs.model.Station
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNearbyStationsUseCase @Inject constructor(
    private val stationRepository: StationRepository
) {

    operator fun invoke(): Flow<List<Station>> {
        return stationRepository.getNearbyStations()
    }
}
