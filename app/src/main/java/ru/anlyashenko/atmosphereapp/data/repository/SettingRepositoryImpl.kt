package ru.anlyashenko.atmosphereapp.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.anlyashenko.atmosphereapp.domain.repository.SettingsRepository
import ru.anlyashenko.atmosphereapp.feature.setting_appearence.ui.CornerRadiusMode
import ru.anlyashenko.atmosphereapp.feature.setting_appearence.ui.ThemeMode
import ru.anlyashenko.atmosphereapp.receiver.notification.NotificationDefaults
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {

    companion object {
        val NOTIFICATION_ENABLED = booleanPreferencesKey("notifications_enabled")
        val NOTIFICATION_HOUR = intPreferencesKey("notification_hour")
        val NOTIFICATION_MINUTE = intPreferencesKey("notification_minute")
        val SELECTED_PALETTE_ID = intPreferencesKey("selected_palette_id")
        val ONBOARDING_COMPLETED = booleanPreferencesKey("onboarding_completed")
        val THEME_MODE = stringPreferencesKey("theme_mode")
        val CORNER_RADIUS = stringPreferencesKey("corner_radius")
    }

    override val notificationEnabledFlow: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[NOTIFICATION_ENABLED] ?: true
        }

    override val notificationHourFlow: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[NOTIFICATION_HOUR] ?: NotificationDefaults.DEFAULT_HOUR
        }

    override val notificationMinuteFlow: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[NOTIFICATION_MINUTE] ?: NotificationDefaults.DEFAULT_MINUTE
        }
    override val selectedPaletteFlow: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[SELECTED_PALETTE_ID] ?: 0
        }
    override val isOnboardingCompletedFlow: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[ONBOARDING_COMPLETED] ?: false
        }
    override val themeModeFlow: Flow<ThemeMode> = dataStore.data
        .map { preferences ->
            val themeName =preferences[THEME_MODE] ?: ThemeMode.SYSTEM.name
            ThemeMode.valueOf(themeName)
        }

    override val cornerRadiusFlow: Flow<CornerRadiusMode> = dataStore.data
        .map { preferences ->
            val radiusName = preferences[CORNER_RADIUS] ?: CornerRadiusMode.MODERATE.name
            CornerRadiusMode.valueOf(radiusName)
        }

    override suspend fun saveNotificationSettings(isEnabled: Boolean, hour: Int, minute: Int) {
        dataStore.edit { preferences ->
            preferences[NOTIFICATION_ENABLED] = isEnabled
            preferences[NOTIFICATION_HOUR] = hour
            preferences[NOTIFICATION_MINUTE] = minute
        }
    }

    override suspend fun saveSelectedPalette(paletteId: Int) {
        dataStore.edit { preferences ->
            preferences[SELECTED_PALETTE_ID] = paletteId
        }
    }

    override suspend fun saveOnboardingCompleted(isCompleted: Boolean) {
        dataStore.edit { preferences ->
            preferences[ONBOARDING_COMPLETED] = isCompleted
        }
    }

    override suspend fun saveThemeMode(mode: ThemeMode) {
        dataStore.edit { preferences ->
            preferences[THEME_MODE] = mode.name
        }
    }

    override suspend fun saveCornerRadius(mode: CornerRadiusMode) {
        dataStore.edit { preferences ->
            preferences[CORNER_RADIUS] = mode.name
        }
    }
}