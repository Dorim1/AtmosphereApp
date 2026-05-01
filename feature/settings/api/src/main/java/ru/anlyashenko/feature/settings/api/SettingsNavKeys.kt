package ru.anlyashenko.feature.settings.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
object SettingsNavKey: NavKey

@Serializable
object AppearanceNavKey: NavKey

@Serializable
object EditMoodsNavKey: NavKey

@Serializable
object NotificationSettingsNavKey: NavKey