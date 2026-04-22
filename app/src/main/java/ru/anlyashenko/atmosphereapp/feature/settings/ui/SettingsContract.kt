package ru.anlyashenko.atmosphereapp.feature.settings.ui

import ru.anlyashenko.atmosphereapp.R
import ru.anlyashenko.atmosphereapp.core.design_system.ui.UiText
import ru.anlyashenko.atmosphereapp.core.mvi.UiEffect
import ru.anlyashenko.atmosphereapp.core.mvi.UiEvent
import ru.anlyashenko.atmosphereapp.core.mvi.UiState
import ru.anlyashenko.atmosphereapp.feature.home.ui.HomeEffect
import ru.anlyashenko.atmosphereapp.feature.setting_appearence.ui.ThemeMode
import ru.anlyashenko.atmosphereapp.receiver.notification.NotificationDefaults

data class SettingsState(
    val isNotificationsEnabled: Boolean = NotificationDefaults.DEFAULT_IS_ENABLED,
    val theme: ThemeMode = ThemeMode.SYSTEM,
    val showLanguageDialog: Boolean = false,
): UiState {
    val notificationSubtitle: UiText
        get() = if (isNotificationsEnabled) {
            UiText.StringResource(R.string.settings_notifications_enabled)
        } else {
            UiText.StringResource(R.string.settings_notifications_disabled)
        }

    val themeSubtitle: UiText
        get() = when (theme) {
            ThemeMode.SYSTEM -> UiText.StringResource(R.string.settings_appearance_system)
            ThemeMode.LIGHT -> UiText.StringResource(R.string.settings_appearance_light)
            ThemeMode.DARK -> UiText.StringResource(R.string.settings_appearance_dark)
        }
}

sealed interface SettingsEvent: UiEvent {
    object OnBackClick : SettingsEvent
    object OnAppearanceClick : SettingsEvent
    object OnEditMoodsClick : SettingsEvent
    object OnNotificationClick : SettingsEvent

    object OpenLanguageDialog : SettingsEvent
    object DismissDialogs : SettingsEvent

    data class OnPermissionResult(val isGranted: Boolean) : SettingsEvent
}

sealed interface SettingsEffect : UiEffect {
    object NavigateBack : SettingsEffect
    object NavigateToAppearance : SettingsEffect
    object NavigateToEditMoods : SettingsEffect
    object NavigateToNotificationSettings : SettingsEffect
}