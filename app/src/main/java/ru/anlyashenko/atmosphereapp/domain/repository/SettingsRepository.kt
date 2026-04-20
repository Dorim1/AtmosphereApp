package ru.anlyashenko.atmosphereapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.anlyashenko.atmosphereapp.feature.setting_appearence.ui.CornerRadiusMode
import ru.anlyashenko.atmosphereapp.feature.setting_appearence.ui.ThemeMode
import ru.anlyashenko.atmosphereapp.receiver.notification.NotificationDefaults

interface SettingsRepository {
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