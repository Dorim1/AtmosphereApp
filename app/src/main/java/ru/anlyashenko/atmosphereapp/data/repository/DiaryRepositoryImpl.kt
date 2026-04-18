package ru.anlyashenko.atmosphereapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import ru.anlyashenko.atmosphereapp.R
import ru.anlyashenko.atmosphereapp.core.utils.MoodIconManager
import ru.anlyashenko.atmosphereapp.core.utils.MoodPalettes
import ru.anlyashenko.atmosphereapp.data.local.database.dao.DiaryDao
import ru.anlyashenko.atmosphereapp.data.local.database.dao.MoodDao
import ru.anlyashenko.atmosphereapp.data.local.database.entity.DiaryEntryDBO
import ru.anlyashenko.atmosphereapp.domain.repository.DiaryRepository
import ru.anlyashenko.atmosphereapp.domain.repository.SettingsRepository
import ru.anlyashenko.atmosphereapp.feature.home.mapper.toUiModel
import ru.anlyashenko.atmosphereapp.feature.home.models.DiaryRecordUiModel
import ru.anlyashenko.atmosphereapp.feature.home.models.MoodUiModel
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiaryRepositoryImpl @Inject constructor(
    private val diaryDao: DiaryDao,
    private val moodDao: MoodDao,
    private val settingsRepository: SettingsRepository
) : DiaryRepository {

//    override val availableMoods: Flow<List<MoodUiModel>> = moodDao.getAllMoods()
//        .map { moods -> moods.map { it.toUiModel() } }

    override val availableMoods: Flow<List<MoodUiModel>> = combine(
        moodDao.getAllMoods(),
        settingsRepository.selectedPaletteFlow
    ) { moods, paletteId ->
        val activePalette = MoodPalettes.getPaletteById(paletteId)

        moods.map { dbModel ->
            val uiModel = dbModel.toUiModel()
            val colorIndex = uiModel.level - 1
            val dynamicColor = activePalette.colors.getOrElse(colorIndex) { uiModel.color }

            uiModel.copy(color = dynamicColor)
        }
    }

    override fun getWeekRecordsFlow(): Flow<List<DiaryRecordUiModel>> {
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

    override fun getAllRecordsFlow(): Flow<List<DiaryRecordUiModel>> {
        return combine(
            diaryDao.getAllEntries(),
            availableMoods
        ) { entries, mood ->
            entries.map { entry ->
                val moodForEntry = mood.find { it.id == entry.moodId }
                DiaryRecordUiModel(
                    date = entry.date,
                    mood = moodForEntry,
                    note = entry.note
                )
            }
        }
    }

    override suspend fun saveMood(date: LocalDate, moodId: Int) {
        val existingEntry = diaryDao.getEntryByDate(date)
        val entryToSave = existingEntry?.copy(moodId = moodId) ?: DiaryEntryDBO(date = date, moodId = moodId)
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
        iconRes: Int
    ) {
        val iconKey = MoodIconManager.getKeyByRes(iconRes)
        moodDao.updateMoodDetails(moodId, customName, iconKey)
    }


    override suspend fun replaceMood(oldMoodId: Int, targetMoodId: Int) {
        diaryDao.replaceMoodInAllEntries(oldMoodId, targetMoodId)
    }
}