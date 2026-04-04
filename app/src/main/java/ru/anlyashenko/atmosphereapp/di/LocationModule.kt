package ru.anlyashenko.atmosphereapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.anlyashenko.atmosphereapp.data.network.location.DefaultLocationTracker
import ru.anlyashenko.atmosphereapp.domain.location.LocationTracker
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface LocationModule {

    @Binds
    @Singleton
    fun bindLocationTracker(
        defaultLocationTracker: DefaultLocationTracker
    ): LocationTracker

}