package ru.anlyashenko.atmosphereapp.di

import android.app.Application
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.anlyashenko.atmosphereapp.data.location.DefaultLocationTracker
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

    companion object {
        @Provides
        @Singleton
        fun provideFusedLocationProviderClient(app: Application): FusedLocationProviderClient {
            return LocationServices.getFusedLocationProviderClient(app)
        }
    }
}