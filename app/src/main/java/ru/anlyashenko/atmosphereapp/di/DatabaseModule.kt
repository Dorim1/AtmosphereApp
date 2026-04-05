package ru.anlyashenko.atmosphereapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import ru.anlyashenko.atmosphereapp.data.local.database.AppDatabase
import ru.anlyashenko.atmosphereapp.data.local.database.DatabaseCallback
import ru.anlyashenko.atmosphereapp.data.local.database.dao.DiaryDao
import ru.anlyashenko.atmosphereapp.data.local.database.dao.MoodDao
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        moodDaoProvider: Provider<MoodDao>
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database.db"
        )
        .addCallback(DatabaseCallback(moodDaoProvider, CoroutineScope(Dispatchers.IO)))
        .build()
    }

    @Provides
    @Singleton
    fun provideMoodDao(database: AppDatabase): MoodDao {
        return database.moodDao()
    }

    @Provides
    @Singleton
    fun provideDiaryDao(database: AppDatabase): DiaryDao {
        return database.diaryDao()
    }
}