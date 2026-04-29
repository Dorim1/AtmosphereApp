package ru.anlyashenko.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.anlyashenko.database.dao.DiaryDao
import ru.anlyashenko.database.dao.MoodDao
import ru.anlyashenko.database.utils.DateConverter
import ru.anlyashenko.database.entity.DiaryEntryDBO
import ru.anlyashenko.database.entity.MoodDBO

// todo: перенести в этот модуль schemas
@Database(
    entities = [DiaryEntryDBO::class, MoodDBO::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(DateConverter::class)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun diaryDao(): DiaryDao
    abstract fun moodDao(): MoodDao
}