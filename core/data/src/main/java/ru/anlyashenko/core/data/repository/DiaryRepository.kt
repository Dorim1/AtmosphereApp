package ru.anlyashenko.core.data.repository

import kotlinx.coroutines.flow.Flow
import ru.anlyashenko.core.model.DiaryRecord
import ru.anlyashenko.core.model.Mood
import java.time.LocalDate

interface DiaryRepository {
    val availableMoods: Flow<List<Mood>>
    fun getWeekRecordsFlow(): Flow<List<DiaryRecord>>
    fun getAllRecordsFlow(): Flow<List<DiaryRecord>>
    suspend fun saveMood(date: LocalDate, moodId: Int)
    suspend fun saveNote(date: LocalDate, text: String)
    suspend fun updateMoodDetails(moodId: Int, customName: String, iconKey: String)
    suspend fun replaceMood(oldMoodId: Int, targetMoodId: Int)

}
