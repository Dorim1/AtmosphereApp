package ru.anlyashenko.atmosphereapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.anlyashenko.atmosphereapp.data.local.database.dao.DiaryDao
import ru.anlyashenko.atmosphereapp.data.local.database.dao.MoodDao
import ru.anlyashenko.atmosphereapp.data.local.database.entity.DiaryEntryDBO
import ru.anlyashenko.atmosphereapp.feature.home.mapper.toUiModel
import ru.anlyashenko.atmosphereapp.feature.home.models.MoodUiModel
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiaryRepository @Inject constructor(
    private val diaryDao: DiaryDao,
    private val moodDao: MoodDao
) {
    fun getAvailableMoods(): Flow<List<MoodUiModel>> {
        return moodDao.getAllMoods().map { list -> list.map { it.toUiModel() } }
    }

    suspend fun saveMood(date: LocalDate, moodId: Int) {
        val existing = diaryDao.getEntryByDate(date)
        val entry = existing?.copy(moodId = moodId) ?: DiaryEntryDBO(date = date, moodId = moodId)
        diaryDao.insertOrUpdate(entry)
    }
}