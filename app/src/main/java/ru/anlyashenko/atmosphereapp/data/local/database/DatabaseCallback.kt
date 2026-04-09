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
            MoodDBO(1, "EXCELLENT", 1, "#8AA232", "ic_mood_very_satisfied"), // TODO: Создать строковые константы
            MoodDBO(2, "GOOD", 2, "#329340", "ic_mood_satisfied"),
            MoodDBO(3, "NORMAL", 3, "#FBC117", "ic_mood_neutral"),
            MoodDBO(4, "BAD", 4, "#FB5E01", "ic_mood_dissatisfied"),
            MoodDBO(5, "TERRIBLE", 5, "#E40000", "ic_mood_very_dissatisfied"),
        )
        moodDaoProvider.get().insertMoods(defaultMoods)
    }
}