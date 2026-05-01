package ru.anlyashenko.feature.yealystats.impl

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import ru.anlyashenko.core.data.repository.DiaryRepository
import ru.anlyashenko.core.data.repository.SettingsRepository
import ru.anlyashenko.core.designsystem.theme.MoodPalettes
import ru.anlyashenko.core.presentation.mvi.BaseViewModel
import ru.anlyashenko.feature.yealystats.impl.model.toUiModel
import javax.inject.Inject

@HiltViewModel
class YearlyStatsViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository,
    private val settingsRepository: SettingsRepository,
): BaseViewModel<YearlyStatsEvent, YearlyStatsState, YearlyStatsEffect>() {

    override fun createInitialState(): YearlyStatsState = YearlyStatsState()

    init {
        observeData()
    }

    private fun observeData() {
        viewModelScope.launch {
            combine(
                diaryRepository.getAllRecordsFlow(),
                diaryRepository.availableMoods,
                settingsRepository.selectedPaletteFlow
            ) { domainRecords, domainMoods, paletteId ->

                val activePalette = MoodPalettes.allPalettes.find { it.id == paletteId }
                    ?: MoodPalettes.allPalettes.first()

                val uiMoods = domainMoods.map { it.toUiModel(activePalette) }
                val uiRecords = domainRecords.map { it.toUiModel(activePalette) }

                Pair(uiRecords, uiMoods)
            }.collect { (records, moods) ->
                setState {
                    copy(
                        records = records,
                        availableMoods = moods,
                        hasEnoughMoodData = records.count { it.hasMood } >= 1
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