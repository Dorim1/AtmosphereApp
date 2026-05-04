package ru.anlyashenko.database

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.anlyashenko.core.model.MoodDefaults
import ru.anlyashenko.database.dao.MoodDao
import ru.anlyashenko.database.entity.MoodDBO
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
        val defaultMoods: List<MoodDBO> = listOf(
            MoodDBO(
                1,
                5,
                MoodDefaults.COLOR_VERY_SATISFIED,
                MoodDefaults.ICON_VERY_SATISFIED,
                null
            ),
            MoodDBO(
                2,
                4,
                MoodDefaults.COLOR_SATISFIED,
                MoodDefaults.ICON_SATISFIED,
                null
            ),
            MoodDBO(
                3,
                3,
                MoodDefaults.COLOR_NEUTRAL,
                MoodDefaults.ICON_NEUTRAL,
                null
            ),
            MoodDBO(
                4,
                2,
                MoodDefaults.COLOR_DISSATISFIED,
                MoodDefaults.ICON_DISSATISFIED,
                null
            ),
            MoodDBO(
                5,
                1,
                MoodDefaults.COLOR_VERY_DISSATISFIED,
                MoodDefaults.ICON_VERY_DISSATISFIED,
                null
            )
        )
        moodDaoProvider.get().insertMoods(defaultMoods)
    }
}
