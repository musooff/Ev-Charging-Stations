package com.titicorp.evcs.data.di

import com.titicorp.evcs.data.repository.StationRepository
import com.titicorp.evcs.data.repository.fake.FakeStationRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsStationRepository(
        stationRepository: FakeStationRepository,
    ): StationRepository
}
