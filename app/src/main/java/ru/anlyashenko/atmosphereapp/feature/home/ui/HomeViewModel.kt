package ru.anlyashenko.atmosphereapp.feature.home.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.anlyashenko.atmosphereapp.core.mvi.BaseViewModel
import ru.anlyashenko.atmosphereapp.core.utils.Result
import ru.anlyashenko.atmosphereapp.data.repository.WeatherRepository
import ru.anlyashenko.atmosphereapp.domain.location.LocationTracker
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationTracker: LocationTracker
) : BaseViewModel<HomeEvent, HomeState, HomeEffect>() {

    override fun createInitialState(): HomeState = HomeState(
        weekRecords = getDaysFromMondayToToday()
    )

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.LoadWeather -> fetchWeatherWithLocation()

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

    private fun fetchWeatherWithLocation() {
        setState { copy(isLoadingWeather = true) }

        viewModelScope.launch {
            val location = locationTracker.getCurrentLocation()
            Log.d("Weather", "fetchWeather called: ${location?.lat}, ${location?.lon}")
            if (location != null) {
                val city = location.city ?: "Неизвестный город"
                when (val result = weatherRepository.getWeather(location.lat, location.lon, city)) {
                    is Result.Success -> setState { copy(weather = result.data, isLoadingWeather = false) }
                    is Result.Error -> {
                        setState { copy(isLoadingWeather = false) }
                        setEffect { HomeEffect.ShowSnackbar("Не удалось загрузить погоду") }
                    }
                }
            } else {
                setState { copy(isLoadingWeather = false) }
                setEffect { HomeEffect.ShowSnackbar("Не удалось определить город") }
            }

        }
    }

}