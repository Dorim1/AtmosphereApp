package ru.anlyashenko.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.anlyashenko.database.AppDatabase
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
    ): AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "app_database",
    ).build()
}