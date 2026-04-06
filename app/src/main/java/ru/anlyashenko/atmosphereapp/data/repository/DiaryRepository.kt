package ru.anlyashenko.atmosphereapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import ru.anlyashenko.atmosphereapp.data.local.database.dao.DiaryDao
import ru.anlyashenko.atmosphereapp.data.local.database.dao.MoodDao
import ru.anlyashenko.atmosphereapp.data.local.database.entity.DiaryEntryDBO
import ru.anlyashenko.atmosphereapp.feature.home.mapper.toUiModel
import ru.anlyashenko.atmosphereapp.feature.home.models.DiaryRecordUiModel
import ru.anlyashenko.atmosphereapp.feature.home.models.MoodUiModel
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiaryRepository @Inject constructor(
    private val diaryDao: DiaryDao,
    private val moodDao: MoodDao
) {

    val availableMoods: Flow<List<MoodUiModel>> = moodDao.getAllMoods()
        .map { moods -> moods.map { it.toUiModel() } }

    fun getWeekRecordsFlow(): Flow<List<DiaryRecordUiModel>> {
        val today = LocalDate.now()
        val monday = today.with(DayOfWeek.MONDAY)

        val dates = mutableListOf<LocalDate>()
        var curr = today
        while (!curr.isBefore(monday)) {
            dates.add(curr)
            curr = curr.minusDays(1)
        }

        return combine(
            diaryDao.getEntriesBetweenDates(monday, today),
            availableMoods
        ) { entries, moods ->
            dates.map { date ->
                val entryForDate = entries.find { it.date == date }
                val moodForEntry = moods.find { it.id == entryForDate?.moodId }

                DiaryRecordUiModel(
                    date = date,
                    mood = moodForEntry,
                    note = entryForDate?.note
                )
            }
        }
    }
    suspend fun saveMood(date: LocalDate, moodId: Int) {
        val existingEntry = diaryDao.getEntryByDate(date)
        val entryToSave = existingEntry?.copy(moodId = moodId) ?: DiaryEntryDBO(date = date, moodId = moodId)
        diaryDao.insertOrUpdate(entryToSave)
    }
}