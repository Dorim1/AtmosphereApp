package ru.anlyashenko.atmosphereapp.data.local.database

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.anlyashenko.atmosphereapp.R
import ru.anlyashenko.atmosphereapp.core.utils.MoodConstants
import ru.anlyashenko.atmosphereapp.core.utils.MoodIconManager
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
        val defaultMoods: List<MoodDBO> = listOf(
            MoodDBO(
                1,
                5,
                MoodConstants.COLOR_VERY_SATISFIED,
                MoodIconManager.ICON_VERY_SATISFIED,
                null
            ),
            MoodDBO(
                2,
                4,
                MoodConstants.COLOR_SATISFIED,
                MoodIconManager.ICON_SATISFIED,
                null
            ),
            MoodDBO(
                3,
                3,
                MoodConstants.COLOR_NEUTRAL,
                MoodIconManager.ICON_NEUTRAL,
                null
            ),
            MoodDBO(
                4,
                2,
                MoodConstants.COLOR_DISSATISFIED,
                MoodIconManager.ICON_DISSATISFIED,
                null
            ),
            MoodDBO(
                5,
                1,
                MoodConstants.COLOR_VERY_DISSATISFIED,
                MoodIconManager.ICON_VERY_DISSATISFIED,
                null
            )
        )
        moodDaoProvider.get().insertMoods(defaultMoods)
    }
}