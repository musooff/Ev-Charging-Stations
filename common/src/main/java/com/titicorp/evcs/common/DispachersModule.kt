package com.titicorp.evcs.common

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispachersModule {

    @Provides
    fun providesIODispatcher(): CoroutineDispatcher = Dispatchers.IO
}
