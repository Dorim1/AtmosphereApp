package ru.anlyashenko.core.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import ru.anlyashenko.core.model.DiaryRecord
import ru.anlyashenko.core.model.Mood
import ru.anlyashenko.database.dao.DiaryDao
import ru.anlyashenko.database.dao.MoodDao
import ru.anlyashenko.database.entity.DiaryEntryDBO
import ru.anlyashenko.database.entity.asExternalModel
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.find

@Singleton
class DiaryRepositoryImpl @Inject constructor (
    private val diaryDao: DiaryDao,
    private val moodDao: MoodDao,
) : DiaryRepository {

    override val availableMoods: Flow<List<Mood>> = moodDao.getAllMoods()
        .map { moods -> moods.map { it.asExternalModel() } }

    override fun getWeekRecordsFlow(): Flow<List<DiaryRecord>> {
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

                DiaryRecord(
                    date = date,
                    mood = moodForEntry,
                    note = entryForDate?.note
                )
            }
        }
    }

    override fun getAllRecordsFlow(): Flow<List<DiaryRecord>> {
        return combine(
            diaryDao.getAllEntries(),
            availableMoods
        ) { entries, mood ->
            entries.map { entry ->
                val moodForEntry = mood.find { it.id == entry.moodId }
                DiaryRecord(
                    date = entry.date,
                    mood = moodForEntry,
                    note = entry.note
                )
            }
        }
    }

    override suspend fun saveMood(date: LocalDate, moodId: Int) {
        val existingEntry = diaryDao.getEntryByDate(date)
        val entryToSave = existingEntry?.copy(moodId = moodId) ?: DiaryEntryDBO(
            date = date,
            moodId = moodId
        )
        diaryDao.insertOrUpdate(entryToSave)
    }

    override suspend fun saveNote(date: LocalDate, text: String) {
        val existingEntry = diaryDao.getEntryByDate(date)

        if (text.isBlank()) {
            if (existingEntry != null) {
                if (existingEntry.moodId == null) {
                    diaryDao.delete(existingEntry)
                } else {
                    diaryDao.insertOrUpdate(existingEntry.copy(note = null))
                }
            }
        } else {
            val entryToSave = existingEntry?.copy(note = text) ?: DiaryEntryDBO(date = date, note = text)
            diaryDao.insertOrUpdate(entryToSave)
        }
    }

    override suspend fun updateMoodDetails(
        moodId: Int,
        customName: String,
        iconKey: String
    ) {
        moodDao.updateMoodDetails(moodId, customName, iconKey)
    }


    override suspend fun replaceMood(oldMoodId: Int, targetMoodId: Int) {
        diaryDao.replaceMoodInAllEntries(oldMoodId, targetMoodId)
    }
}
