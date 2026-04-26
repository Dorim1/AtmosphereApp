package ru.anlyashenko.database.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.anlyashenko.database.AppDatabase
import ru.anlyashenko.database.dao.DiaryDao
import ru.anlyashenko.database.dao.MoodDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {

    @Provides
    fun provideMoodDao(database: AppDatabase): MoodDao {
        return database.moodDao()
    }

    @Provides
    fun provideDiaryDao(database: AppDatabase): DiaryDao {
        return database.diaryDao()
    }
}