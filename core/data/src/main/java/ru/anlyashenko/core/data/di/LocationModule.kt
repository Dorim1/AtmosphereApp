package ru.anlyashenko.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.anlyashenko.core.data.location.DefaultLocationTracker
import ru.anlyashenko.core.data.location.LocationTracker
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
