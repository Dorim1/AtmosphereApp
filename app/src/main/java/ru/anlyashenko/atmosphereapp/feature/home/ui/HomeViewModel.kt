package ru.anlyashenko.atmosphereapp.feature.home.ui

import android.util.Log
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.anlyashenko.atmosphereapp.core.mvi.BaseViewModel
import ru.anlyashenko.atmosphereapp.core.utils.Result
import ru.anlyashenko.atmosphereapp.domain.location.LocationTracker
import ru.anlyashenko.atmosphereapp.domain.repository.DiaryRepository
import ru.anlyashenko.atmosphereapp.domain.repository.WeatherRepository
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationTracker: LocationTracker,
    private val diaryRepository: DiaryRepository,
) : BaseViewModel<HomeEvent, HomeState, HomeEffect>() {

    init {
        observeDiaryData()
    }

    override fun createInitialState(): HomeState = HomeState()

    private fun observeDiaryData() {
        viewModelScope.launch {
            diaryRepository.getWeekRecordsFlow().collect { records ->
                setState { copy(weekRecords = records) }
            }
        }
        viewModelScope.launch {
            diaryRepository.availableMoods.collect { moods ->
                setState { copy(availableMoods = moods) }
            }
        }
    }

//    private fun updateTodayRecord() {
//        val today = LocalDate.now()
//        setState { copy(todayRecord = weekRecords.find { it.date == today }) }
//    }

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.LoadWeather -> fetchWeatherWithLocation()

            is HomeEvent.OnMoodButtonClick -> setState { copy(showMoodSheet = true) }
            is HomeEvent.OnNoteButtonClick -> setState { copy(showNoteDialog = true) }
            is HomeEvent.DismissDialogs -> setState {
                copy(showMoodSheet = false, showNoteDialog = false)
            }

            is HomeEvent.OnMoodSelected -> {
                viewModelScope.launch {
                    val today = LocalDate.now()
                    diaryRepository.saveMood(today, event.moodId)
                    setState { copy(showMoodSheet = false) }
                }
            }
            is HomeEvent.OnSaveNote -> {
                viewModelScope.launch {
                    val today = LocalDate.now()
                    diaryRepository.saveNote(today, event.noteText)
                }
            }
        }
    }


    private fun fetchWeatherWithLocation() {
        setState { copy(isLoadingWeather = true) }

        viewModelScope.launch {
            val location = locationTracker.getCurrentLocation()
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