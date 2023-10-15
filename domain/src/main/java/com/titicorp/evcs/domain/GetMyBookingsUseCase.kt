package com.titicorp.evcs.domain

import com.titicorp.evcs.data.repository.StationRepository
import com.titicorp.evcs.model.Booking
import javax.inject.Inject

class GetMyBookingsUseCase @Inject constructor(
    private val stationRepository: StationRepository
) {

    suspend operator fun invoke(): List<Booking> {
        return stationRepository.getMyBookings()
    }
}
