package ru.anlyashenko.feature.settings.api

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable
import ru.anlyashenko.core.navigation.Navigator

@Serializable
object SettingsNavKey : NavKey

@Serializable
object AppearanceNavKey : NavKey

@Serializable
object EditMoodsNavKey : NavKey

@Serializable
object NotificationSettingsNavKey : NavKey

fun Navigator.navigateToSettings() {
    navigate(SettingsNavKey)
}

fun Navigator.navigateToAppearance() {
    navigate(AppearanceNavKey)
}

fun Navigator.navigateToEditMoods() {
    navigate(EditMoodsNavKey)
}

fun Navigator.navigateToNotificationSettings() {
    navigate(NotificationSettingsNavKey)
}
