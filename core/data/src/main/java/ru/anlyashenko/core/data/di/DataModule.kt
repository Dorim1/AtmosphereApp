package ru.anlyashenko.core.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.anlyashenko.core.data.repository.DiaryRepository
import ru.anlyashenko.core.data.repository.DiaryRepositoryImpl
import ru.anlyashenko.core.data.repository.SettingRepositoryImpl
import ru.anlyashenko.core.data.repository.SettingsRepository
import ru.anlyashenko.core.data.repository.WeatherRepository
import ru.anlyashenko.core.data.repository.WeatherRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    @Singleton
    fun bindDiaryRepository(
        diaryRepositoryImpl: DiaryRepositoryImpl
    ): DiaryRepository

    @Binds
    @Singleton
    fun bindWeatherRepository(
        weatherRepositoryImpl: WeatherRepositoryImpl
    ): WeatherRepository

    @Binds
    @Singleton
    fun bindSettingsRepository(
        settingsRepositoryImpl: SettingRepositoryImpl
    ): SettingsRepository

}