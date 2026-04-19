package ru.anlyashenko.atmosphereapp

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import ru.anlyashenko.atmosphereapp.core.navigation.Destination
import ru.anlyashenko.atmosphereapp.domain.repository.SettingsRepository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    var isLoading by mutableStateOf(true)
        private set

    var startDestination by mutableStateOf<Destination>(Destination.IntroRoute)
        private set

    init {
        viewModelScope.launch {
            settingsRepository.isOnboardingCompletedFlow.collect { isCompleted ->
                startDestination = if (isCompleted) {
                    Destination.HomeRoute
                } else {
                    Destination.IntroRoute
                }
                isLoading = false
            }
        }
    }
}