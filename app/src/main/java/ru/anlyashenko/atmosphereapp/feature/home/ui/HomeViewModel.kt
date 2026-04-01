package ru.anlyashenko.atmosphereapp.feature.home.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.anlyashenko.atmosphereapp.core.mvi.BaseViewModel
import ru.anlyashenko.atmosphereapp.core.utils.Result
import ru.anlyashenko.atmosphereapp.data.repository.WeatherRepository

class HomeViewModel(
    private val weatherRepository: WeatherRepository
) : BaseViewModel<HomeEvent, HomeState, HomeEffect>() {

    override fun createInitialState(): HomeState = HomeState(
        weekRecords = getDaysFromMondayToToday()
    )

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.LoadWeather -> fetchWeather(event.lat, event.lon)

            is HomeEvent.OnMoodButtonClick -> setState { copy(showMoodSheet = true) }
            is HomeEvent.OnNoteButtonClick -> setState { copy(showNoteDialog = true) }
            is HomeEvent.DismissDialogs -> setState {
                copy(showMoodSheet = false, showNoteDialog = false)
            }

            is HomeEvent.OnMoodSelected -> {
                // TODO: Сохранить в БД
                println("Выбрано настроение: ${event.moodId}")
                setState { copy(showMoodSheet = false) }
            }
            is HomeEvent.OnSaveNote -> {
                // TODO: Сохранить в БД
                println("Сохранён текст: ${event.noteText}")
                setState { copy(showNoteDialog = false) }
            }
        }
    }

    private fun fetchWeather(lat: Double, lon: Double) {
        Log.d("Weather", "fetchWeather called: $lat, $lon")
        setState { copy(isLoadingWeather = true) }

        viewModelScope.launch {
            when (val result = weatherRepository.getWeather(lat, lon)) {
                is Result.Success -> {
                    setState {
                        copy(
                            weather = result.data,
                            isLoadingWeather = false
                        )
                    }
                }
                is Result.Error -> {
                    setState { copy(isLoadingWeather = false) }
                    setEffect { HomeEffect.ShowSnackbar(result.message ?: "Ошибка") }
                }
            }
        }
    }

}