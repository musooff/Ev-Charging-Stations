package com.titicorp.evcs.domain

import com.titicorp.evcs.data.repository.StationRepository
import com.titicorp.evcs.model.Station
import javax.inject.Inject

class GetNearbyStationsUseCase @Inject constructor(
    private val stationRepository: StationRepository
) {

    suspend operator fun invoke(): List<Station> {
        return stationRepository.getNearbyStations()
    }
}
