package ru.anlyashenko.feature.settings.impl.notifications

import ru.anlyashenko.core.data.repository.SettingsRepository
import ru.anlyashenko.core.presentation.mvi.UiEffect
import ru.anlyashenko.core.presentation.mvi.UiEvent
import ru.anlyashenko.core.presentation.mvi.UiState

data class NotificationSettingsState(
    val isNotificationsEnabled: Boolean = SettingsRepository.DEFAULT_NOTIFICATION_ENABLED,
    val notificationHour: Int = SettingsRepository.DEFAULT_NOTIFICATION_HOUR,
    val notificationMinute: Int = SettingsRepository.DEFAULT_NOTIFICATION_MINUTE,
    val isLoading: Boolean = true,
) : UiState

sealed interface NotificationSettingsEvent : UiEvent {
    object OnBackClick : NotificationSettingsEvent
    data class SaveNotificationSettings(
        val hour: Int,
        val minute: Int,
        val isEnabled: Boolean,
    ) : NotificationSettingsEvent

    object OnShouldOpenSettings : NotificationSettingsEvent
    data class OnPermissionResult(val isGranted: Boolean) : NotificationSettingsEvent
    data class OnResumePermissionCheck(val isGranted: Boolean) : NotificationSettingsEvent
}

sealed interface NotificationSettingsEffect : UiEffect {
    object NavigateBack : NotificationSettingsEffect
    object RequestNotificationPermission : NotificationSettingsEffect
    object OpenAppSettings : NotificationSettingsEffect
}
