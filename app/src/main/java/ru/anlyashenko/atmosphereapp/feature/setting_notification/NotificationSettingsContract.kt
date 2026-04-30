package ru.anlyashenko.atmosphereapp.feature.setting_notification
// todo: ----
/*

import ru.anlyashenko.atmosphereapp.core.mvi.UiEffect
import ru.anlyashenko.atmosphereapp.core.mvi.UiEvent
import ru.anlyashenko.atmosphereapp.core.mvi.UiState
import ru.anlyashenko.atmosphereapp.feature.settings.ui.SettingsEffect
import ru.anlyashenko.atmosphereapp.feature.settings.ui.SettingsEvent
import ru.anlyashenko.atmosphereapp.receiver.notification.NotificationDefaults

data class NotificationSettingsState(
    val isNotificationsEnabled: Boolean = NotificationDefaults.DEFAULT_IS_ENABLED,
    val notificationHour: Int = NotificationDefaults.DEFAULT_HOUR,
    val notificationMinute: Int = NotificationDefaults.DEFAULT_MINUTE,
    val isLoading: Boolean = true,
): UiState

sealed interface NotificationSettingsEvent : UiEvent {
    object OnBackClick: NotificationSettingsEvent
    data class SaveNotificationSettings(
        val hour: Int,
        val minute: Int,
        val isEnabled: Boolean,
    ) : NotificationSettingsEvent
    object OnShouldOpenSettings: NotificationSettingsEvent
    data class OnPermissionResult(val isGranted: Boolean) : NotificationSettingsEvent
}

sealed interface NotificationSettingsEffect : UiEffect {
    object NavigateBack : NotificationSettingsEffect
    object RequestNotificationPermission : NotificationSettingsEffect
    object OpenAppSettings : NotificationSettingsEffect
}*/
