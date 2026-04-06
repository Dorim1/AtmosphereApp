package ru.anlyashenko.atmosphereapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.anlyashenko.atmosphereapp.feature.home.models.DiaryRecordUiModel
import ru.anlyashenko.atmosphereapp.feature.home.models.MoodUiModel
import java.time.LocalDate

interface DiaryRepository {
    val availableMoods: Flow<List<MoodUiModel>>
    fun getWeekRecordsFlow(): Flow<List<DiaryRecordUiModel>>
    suspend fun saveMood(date: LocalDate, moodId: Int)
}