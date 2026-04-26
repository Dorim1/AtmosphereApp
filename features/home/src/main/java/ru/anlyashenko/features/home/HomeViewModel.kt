package ru.anlyashenko.features.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import ru.anlyashenko.core.common.util.Result
import ru.anlyashenko.core.data.location.LocationTracker
import ru.anlyashenko.core.data.repository.DiaryRepository
import ru.anlyashenko.core.data.repository.WeatherRepository
import ru.anlyashenko.core.designsystem.theme.MoodPalettes
import ru.anlyashenko.core.presentation.mvi.BaseViewModel
import ru.anlyashenko.features.home.mapper.toUiModel
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
        fetchWeatherWithLocation()
    }

    override fun createInitialState(): HomeState = HomeState()

    private fun observeDiaryData() {
        /*viewModelScope.launch {
            diaryRepository.getWeekRecordsFlow().collect { records ->
                setState { copy(weekRecords = records) }
            }
        }
        viewModelScope.launch {
            diaryRepository.availableMoods.collect { moods ->
                setState { copy(availableMoods = moods) }
            }
        }*/

        viewModelScope.launch {
            combine(
                diaryRepository.getWeekRecordsFlow(),
                diaryRepository.availableMoods
            ) { domainRecords, domainMoods ->
                // todo: SettingRepo брать палитру
                val activePalette = MoodPalettes.getPaletteById(0)
                val uiMoods = domainMoods.map { it.toUiModel(activePalette) }

                val uiRecords = domainRecords.map { record ->
                    record.toUiModel(uiMoods)
                }

                Pair(uiRecords, uiMoods)
            }.collect { (mappedRecord, mappedMoods) ->
                setState {
                    copy(
                        weekRecords = mappedRecord,
                        availableMoods = mappedMoods
                    )
                }
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
        /*setState { copy(isLoadingWeather = true) }

        viewModelScope.launch {
            val location = locationTracker.getCurrentLocation()
            if (location != null) {
                val city = location.city ?: "Неизвестный город"
                when (val result = weatherRepository.getWeather(location.latitude, location.longitude, city)) {
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

        }*/

        setState { copy(isLoadingWeather = true) }

        viewModelScope.launch {
            val location = locationTracker.getCurrentLocation()
            if (location != null) {
                val city = location.city ?: "Unknown city"
                when (val result = weatherRepository.getWeather(location.latitude, location.longitude, city)) {
                    is Result.Success -> {
                        val weatherUiModel = result.data.toUiModel()

                        setState { copy(weather = weatherUiModel, isLoadingWeather = false) }
                    }
                    is Result.Error -> {
                        setState { copy(isLoadingWeather = false) }
                        setEffect { HomeEffect.ShowSnackbar("Couldn't load weather") }
                    }
                }
            } else {
                setState { copy(isLoadingWeather = false) }
                setEffect { HomeEffect.ShowSnackbar("Couldn't identify the city") }
            }
        }

    }

}