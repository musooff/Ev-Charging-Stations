package com.titicorp.evcs.domain

import com.titicorp.evcs.data.repository.StationRepository
import com.titicorp.evcs.model.StationDetails
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetStationDetailsUseCase @Inject constructor(
    private val stationRepository: StationRepository,
) {

    operator fun invoke(id: String): Flow<StationDetails> {
        return stationRepository.getStationDetails(id)
    }
}
