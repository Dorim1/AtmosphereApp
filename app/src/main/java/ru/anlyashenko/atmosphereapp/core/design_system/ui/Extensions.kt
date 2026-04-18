package ru.anlyashenko.atmosphereapp.core.design_system.ui

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

fun Int.toTwoDigits(): String = "%02d".format(this)

// todo: возможно перенести к DataStore (SettingsRepository)
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

