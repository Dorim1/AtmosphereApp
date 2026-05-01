package ru.anlyashenko.feature.settings.impl.main

import ru.anlyashenko.core.data.repository.SettingsRepository
import ru.anlyashenko.core.designsystem.R
import ru.anlyashenko.core.designsystem.util.UiText
import ru.anlyashenko.core.model.ThemeMode
import ru.anlyashenko.core.presentation.mvi.UiEffect
import ru.anlyashenko.core.presentation.mvi.UiEvent
import ru.anlyashenko.core.presentation.mvi.UiState

data class SettingsState(
    val isNotificationsEnabled: Boolean = SettingsRepository.DEFAULT_NOTIFICATION_ENABLED,
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

//    data class OnPermissionResult(val isGranted: Boolean) : SettingsEvent
}

sealed interface SettingsEffect : UiEffect {
    object NavigateBack : SettingsEffect
    object NavigateToAppearance : SettingsEffect
    object NavigateToEditMoods : SettingsEffect
    object NavigateToNotificationSettings : SettingsEffect
}