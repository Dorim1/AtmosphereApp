package ru.anlyashenko.atmosphereapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation3.runtime.NavKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.anlyashenko.core.data.repository.SettingsRepository
import ru.anlyashenko.core.model.CornerRadiusMode
import ru.anlyashenko.core.model.ThemeMode
import ru.anlyashenko.feature.home.api.HomeNavKey
import ru.anlyashenko.feature.onboarding.api.IntroNavKey
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    var isLoading by mutableStateOf(true)
        private set

    var startDestination by mutableStateOf<NavKey?>(null)
        private set

    val themeMode: StateFlow<ThemeMode> = settingsRepository.themeModeFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ThemeMode.SYSTEM
    )

    val cornerRadius: StateFlow<CornerRadiusMode> = settingsRepository.cornerRadiusFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = CornerRadiusMode.BIG
    )

    init {
        viewModelScope.launch {
            settingsRepository.isOnboardingCompletedFlow.collect { isCompleted ->
                startDestination = if (isCompleted) {
                    HomeNavKey
                } else {
                    IntroNavKey
                }
                isLoading = false
            }
        }
    }
}