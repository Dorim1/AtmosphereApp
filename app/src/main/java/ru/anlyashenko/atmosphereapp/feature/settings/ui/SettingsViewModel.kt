package ru.anlyashenko.atmosphereapp.feature.settings.ui

import android.os.Build
import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import ru.anlyashenko.atmosphereapp.core.mvi.BaseViewModel
import ru.anlyashenko.atmosphereapp.domain.model.AlarmItem
import ru.anlyashenko.atmosphereapp.domain.notification.AlarmScheduler
import ru.anlyashenko.atmosphereapp.domain.repository.SettingsRepository
import ru.anlyashenko.atmosphereapp.receiver.notification.NotificationDefaults
import ru.anlyashenko.atmosphereapp.receiver.notification.NotificationPermissionManager
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val alarmScheduler: AlarmScheduler,
    private val permissionManager: NotificationPermissionManager
) : BaseViewModel<SettingsEvent, SettingsState, SettingsEffect>() {

    override fun createInitialState(): SettingsState = SettingsState()

    init {
        viewModelScope.launch {
            combine(
                settingsRepository.notificationEnabledFlow,
                settingsRepository.themeModeFlow
            ) { isEnabled, theme ->
                currentState.copy(
                    isNotificationsEnabled = isEnabled,
                    theme = theme,
                )
            }.collect { newState ->
                setState { newState }
            }
        }
    }

    override fun handleEvent(event: SettingsEvent) {
        when (event) {
            is SettingsEvent.OnBackClick -> setEffect { SettingsEffect.NavigateBack }
            is SettingsEvent.OnAppearanceClick -> setEffect { SettingsEffect.NavigateToAppearance }
            is SettingsEvent.OnEditMoodsClick -> setEffect { SettingsEffect.NavigateToEditMoods }
            is SettingsEvent.OnNotificationClick -> setEffect { SettingsEffect.NavigateToNotificationSettings }

            is SettingsEvent.OpenLanguageDialog -> setState { copy(showLanguageDialog = true) }
            is SettingsEvent.DismissDialogs -> setState { copy(showLanguageDialog = false) }

            is SettingsEvent.OnPermissionResult -> handlePermissionResult(event.isGranted)
        }
    }

    private fun handlePermissionResult(isGranted: Boolean) {
        if (!isGranted) {
            setState { copy(isNotificationsEnabled = false) }
        }
    }

}