package ru.anlyashenko.atmosphereapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.anlyashenko.atmosphereapp.domain.model.MoodPalette

interface SettingsRepository {
    val notificationEnabledFlow: Flow<Boolean>
    val notificationHourFlow: Flow<Int>
    val notificationMinuteFlow: Flow<Int>
    val selectedPaletteFlow: Flow<Int>
    suspend fun saveNotificationSettings(isEnabled: Boolean, hour: Int, minute: Int)
    suspend fun saveSelectedPalette(paletteId: Int)
}