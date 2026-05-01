package ru.anlyashenko.feature.settings.impl.main

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import ru.anlyashenko.core.data.repository.SettingsRepository
import ru.anlyashenko.core.presentation.mvi.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
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

//            is SettingsEvent.OnPermissionResult -> handlePermissionResult(event.isGranted)
        }
    }

    /*private fun handlePermissionResult(isGranted: Boolean) {
        if (!isGranted) {
            setState { copy(isNotificationsEnabled = false) }
        }
    }*/

}