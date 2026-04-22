package ru.anlyashenko.atmosphereapp.feature.yearly_stats.ui

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import ru.anlyashenko.atmosphereapp.core.mvi.BaseViewModel
import ru.anlyashenko.atmosphereapp.domain.repository.DiaryRepository
import javax.inject.Inject

@HiltViewModel
class YearlyStatsViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository
): BaseViewModel<YearlyStatsEvent, YearlyStatsState, YearlyStatsEffect>() {

    override fun createInitialState(): YearlyStatsState = YearlyStatsState()

    init {
        observeData()
    }

    private fun observeData() {
        viewModelScope.launch {
            combine(
                diaryRepository.getAllRecordsFlow(),
                diaryRepository.availableMoods
            ) { records, moods ->
                Pair(records, moods)
            }.collect { (records, moods) ->
                setState {
                    copy(
                        records = records,
                        availableMoods = moods,
                        hasEnoughMoodData = records.count { it.hasMood } >= 3
                    )
                }
            }
        }
    }

    override fun handleEvent(event: YearlyStatsEvent) {
        when (event) {
            is YearlyStatsEvent.OnBackClick -> setEffect { YearlyStatsEffect.NavigateBack }
        }
    }

}