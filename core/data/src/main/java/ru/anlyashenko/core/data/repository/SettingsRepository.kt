package ru.anlyashenko.core.data.repository

import kotlinx.coroutines.flow.Flow
import ru.anlyashenko.core.model.CornerRadiusMode
import ru.anlyashenko.core.model.ThemeMode

interface SettingsRepository {

    companion object {
        const val DEFAULT_NOTIFICATION_HOUR = 20
        const val DEFAULT_NOTIFICATION_MINUTE = 30
        const val DEFAULT_NOTIFICATION_ENABLED = true
        const val NOTIFICATION_ALARM_ID = 1001
    }

    val notificationEnabledFlow: Flow<Boolean>
    val notificationHourFlow: Flow<Int>
    val notificationMinuteFlow: Flow<Int>
    val selectedPaletteFlow: Flow<Int>
    val isOnboardingCompletedFlow: Flow<Boolean>
    val themeModeFlow: Flow<ThemeMode>
    val cornerRadiusFlow: Flow<CornerRadiusMode>
    suspend fun saveNotificationSettings(isEnabled: Boolean, hour: Int, minute: Int)
    suspend fun saveSelectedPalette(paletteId: Int)
    suspend fun saveOnboardingCompleted(isCompleted: Boolean)
    suspend fun saveThemeMode(mode: ThemeMode)
    suspend fun saveCornerRadius(mode: CornerRadiusMode)
}
