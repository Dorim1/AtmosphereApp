package ru.anlyashenko.atmosphereapp.feature.setting_appearence.ui
// todo: ----
/*

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import ru.anlyashenko.atmosphereapp.core.mvi.BaseViewModel
import ru.anlyashenko.atmosphereapp.domain.repository.SettingsRepository
import ru.anlyashenko.atmosphereapp.feature.profile.ui.ProfileEffect
import javax.inject.Inject

@HiltViewModel
class AppearanceViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : BaseViewModel<AppearanceEvent, AppearanceState, AppearanceEffect>() {

    init {
        observeSettings()
    }

    override fun createInitialState(): AppearanceState = AppearanceState()
    private fun observeSettings() {
        viewModelScope.launch {
            combine(
                settingsRepository.themeModeFlow,
                settingsRepository.cornerRadiusFlow
            ) { theme, radius ->
                Pair(theme, radius)
            }.collect { (theme, radius) ->
                setState {
                    copy(
                        theme = theme,
                        cornerRadius = radius
                    )
                }
            }
        }
    }


    override fun handleEvent(event: AppearanceEvent) {
        when (event) {
            is AppearanceEvent.OnThemeSelected -> {
                viewModelScope.launch {
                    settingsRepository.saveThemeMode(event.theme)
                }
            }

            is AppearanceEvent.OnCornerRadiusSelected -> {
                viewModelScope.launch {
                    settingsRepository.saveCornerRadius(event.radius)
                }
            }

            AppearanceEvent.OnBackClick -> {
                setEffect { AppearanceEffect.NavigateToBack }
            }
        }
    }



}*/
