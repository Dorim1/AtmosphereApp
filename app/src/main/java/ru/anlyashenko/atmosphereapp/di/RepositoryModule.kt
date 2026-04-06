package ru.anlyashenko.atmosphereapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.anlyashenko.atmosphereapp.data.repository.DiaryRepositoryImpl
import ru.anlyashenko.atmosphereapp.data.repository.WeatherRepositoryImpl
import ru.anlyashenko.atmosphereapp.domain.repository.DiaryRepository
import ru.anlyashenko.atmosphereapp.domain.repository.WeatherRepository

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindDiaryRepository(
        diaryRepositoryImpl: DiaryRepositoryImpl
    ): DiaryRepository

    @Binds
    fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository
}