package ru.anlyashenko.atmosphereapp.feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.anlyashenko.atmosphereapp.core.mvi.BaseViewModel
import ru.anlyashenko.atmosphereapp.core.utils.Result
import ru.anlyashenko.atmosphereapp.data.repository.WeatherRepository

class HomeViewModel(
    private val weatherRepository: WeatherRepository
) : BaseViewModel<HomeEvent, HomeState, HomeEffect>() {

    override fun createInitialState(): HomeState = HomeState()

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.LoadWeather -> fetchWeather(event.lat, event.lon)
            else -> {}
        }
    }

    private fun fetchWeather(lat: Double, lon: Double) {
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