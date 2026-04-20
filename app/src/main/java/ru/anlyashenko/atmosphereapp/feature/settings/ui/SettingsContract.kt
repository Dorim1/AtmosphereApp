package ru.anlyashenko.atmosphereapp.feature.settings.ui

import ru.anlyashenko.atmosphereapp.R
import ru.anlyashenko.atmosphereapp.core.design_system.ui.UiText
import ru.anlyashenko.atmosphereapp.core.mvi.UiEffect
import ru.anlyashenko.atmosphereapp.core.mvi.UiEvent
import ru.anlyashenko.atmosphereapp.core.mvi.UiState
import ru.anlyashenko.atmosphereapp.receiver.notification.NotificationDefaults

data class SettingsState(
    val isNotificationsEnabled: Boolean = NotificationDefaults.DEFAULT_IS_ENABLED,
    val notificationHour: Int = NotificationDefaults.DEFAULT_HOUR,
    val notificationMinute: Int = NotificationDefaults.DEFAULT_MINUTE,
    val isLoading: Boolean = true,
    val showNotificationSheet: Boolean = false,
    val showLanguageDialog: Boolean = false,
): UiState {
    val notificationSubtitle: UiText
        get() = if (isNotificationsEnabled) {
            UiText.StringResource(R.string.settings_notifications_enabled)
        } else {
            UiText.StringResource(R.string.settings_notifications_disabled)
        }
}

sealed interface SettingsEvent: UiEvent {
    object OnBackClick : SettingsEvent
    object OnAppearanceClick : SettingsEvent
    object OnEditMoodsClick : SettingsEvent

    object OpenNotificationSheet : SettingsEvent
    object OpenLanguageDialog : SettingsEvent
    object DismissDialogs : SettingsEvent

    object OnShouldOpenSettings: SettingsEvent

    data class SaveNotificationSettings(
        val isEnabled: Boolean,
        val hour: Int,
        val minute: Int
    ) : SettingsEvent

    data class OnPermissionResult(val isGranted: Boolean) : SettingsEvent
}

sealed interface SettingsEffect : UiEffect {
    object NavigateBack : SettingsEffect
    object NavigateToAppearance : SettingsEffect
    object NavigateToEditMoods : SettingsEffect
    object RequestNotificationPermission : SettingsEffect
    object OpenAppSettings : SettingsEffect

}