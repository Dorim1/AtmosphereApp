package ru.anlyashenko.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import ru.anlyashenko.database.AppDatabase
import ru.anlyashenko.database.DatabaseCallback
import ru.anlyashenko.database.dao.MoodDao
import javax.inject.Provider
import javax.inject.Singleton

/**
 * @Provides
 *     @Singleton
 *     fun provideAppDatabase(
 *         @ApplicationContext context: Context,
 *         moodDaoProvider: Provider<MoodDao>
 *     ): AppDatabase {
 *         return Room.databaseBuilder(
 *             context,
 *             AppDatabase::class.java,
 *             "app_database.db"
 *         )
 *         .addCallback(DatabaseCallback(moodDaoProvider, CoroutineScope(Dispatchers.IO)))
 *         .fallbackToDestructiveMigration(true)
 *         .build()
 *     }
 */

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {
    @Provides
    @Singleton
    fun providesAppDatabase(
        @ApplicationContext context: Context,
        moodDaoProvider: Provider<MoodDao>
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "app_database",
    )
        .addCallback(DatabaseCallback(moodDaoProvider, CoroutineScope(Dispatchers.IO)))
        .fallbackToDestructiveMigration(true)
        .build()
}
