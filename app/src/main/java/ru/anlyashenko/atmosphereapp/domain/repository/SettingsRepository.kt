package ru.anlyashenko.atmosphereapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.anlyashenko.atmosphereapp.domain.model.MoodPalette

interface SettingsRepository {
    val currentPalette: Flow<MoodPalette>
    suspend fun setPalette(palette: MoodPalette)
}