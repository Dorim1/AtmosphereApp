package ru.anlyashenko.atmosphereapp.data.local.database

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.anlyashenko.atmosphereapp.R
import ru.anlyashenko.atmosphereapp.data.local.database.dao.MoodDao
import ru.anlyashenko.atmosphereapp.data.local.database.entity.MoodDBO
import javax.inject.Provider

class DatabaseCallback(
    private val moodDaoProvider: Provider<MoodDao>,
    private val scope: CoroutineScope
) : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        scope.launch(Dispatchers.IO) {
            populateDefaultMoods()
        }
    }

    private suspend fun populateDefaultMoods() {
        val defaultMoods = listOf(
            MoodDBO(1, "Отлично", 1),
            MoodDBO(2, "Хорошо", 2),
            MoodDBO(3, "Нормально", 3),
            MoodDBO(4, "Плохо", 4),
            MoodDBO(5, "Ужасно", 5),
        )
        moodDaoProvider.get().insertMoods(defaultMoods)
    }
}