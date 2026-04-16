package ru.anlyashenko.atmosphereapp.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.anlyashenko.atmosphereapp.domain.repository.SettingsRepository
import javax.inject.Inject

// TODO: вынести dataStore
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class SettingRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : SettingsRepository {

    companion object {
        val NOTIFICATION_ENABLED = booleanPreferencesKey("notifications_enabled")
        val NOTIFICATION_HOUR = intPreferencesKey("notification_hour")
        val NOTIFICATION_MINUTE = intPreferencesKey("notification_minute")
        val SELECTED_PALETTE_ID = intPreferencesKey("selected_palette_id")
    }

    override val notificationEnabledFlow: Flow<Boolean> = dataStore.data
        .map { preferences ->
            preferences[NOTIFICATION_ENABLED] ?: true
        }

    override val notificationHourFlow: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[NOTIFICATION_HOUR] ?: 20
        }

    override val notificationMinuteFlow: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[NOTIFICATION_MINUTE] ?: 30
        }
    override val selectedPaletteFlow: Flow<Int> = dataStore.data
        .map { preferences ->
            preferences[SELECTED_PALETTE_ID] ?: 0
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
}