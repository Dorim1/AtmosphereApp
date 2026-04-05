package ru.anlyashenko.atmosphereapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.anlyashenko.atmosphereapp.data.local.database.dao.DiaryDao
import ru.anlyashenko.atmosphereapp.data.local.database.dao.MoodDao
import ru.anlyashenko.atmosphereapp.data.local.database.entity.DiaryEntryDBO
import ru.anlyashenko.atmosphereapp.data.local.database.entity.MoodDBO

@Database(
    entities = [DiaryEntryDBO::class, MoodDBO::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDao
    abstract fun moodDao(): MoodDao
}