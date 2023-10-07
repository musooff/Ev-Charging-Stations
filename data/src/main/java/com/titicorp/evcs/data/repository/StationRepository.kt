package com.titicorp.evcs.data.repository

import com.titicorp.evcs.model.Station
import kotlinx.coroutines.flow.Flow

interface StationRepository {

    fun getNearbyStations(): Flow<List<Station>>

    fun getSavedStations(): Flow<List<Station>>
}
