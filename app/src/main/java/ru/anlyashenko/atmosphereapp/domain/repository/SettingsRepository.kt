package ru.anlyashenko.atmosphereapp.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    val notificationEnabledFlow: Flow<Boolean>
    val notificationHourFlow: Flow<Int>
    val notificationMinuteFlow: Flow<Int>
    val selectedPaletteFlow: Flow<Int>
    val isOnboardingCompletedFlow: Flow<Boolean>
    suspend fun saveNotificationSettings(isEnabled: Boolean, hour: Int, minute: Int)
    suspend fun saveSelectedPalette(paletteId: Int)
    suspend fun saveOnboardingCompleted(isCompleted: Boolean)
}