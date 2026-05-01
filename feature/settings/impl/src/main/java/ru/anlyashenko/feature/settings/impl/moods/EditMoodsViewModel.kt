package ru.anlyashenko.feature.settings.impl.moods

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import ru.anlyashenko.core.data.repository.DiaryRepository
import ru.anlyashenko.core.data.repository.SettingsRepository
import ru.anlyashenko.core.designsystem.icons.MoodIconManager
import ru.anlyashenko.core.designsystem.theme.MoodPalettes
import ru.anlyashenko.core.presentation.mvi.BaseViewModel
import ru.anlyashenko.feature.settings.impl.model.toUiModel
import javax.inject.Inject

@HiltViewModel
class EditMoodsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val diaryRepository: DiaryRepository,
) : BaseViewModel<EditMoodsEvent, EditMoodsState, EditMoodsEffect>() {

    override fun createInitialState() = EditMoodsState()

    init {

        /*viewModelScope.launch {
            diaryRepository.availableMoods.collectLatest { coloredMoods ->
                setState { copy(moods = coloredMoods) }
            }
        }

        viewModelScope.launch {
            settingsRepository.selectedPaletteFlow.collectLatest { paletteId ->
                setState { copy(selectedPaletteId = paletteId) }
            }
        }*/
        viewModelScope.launch {
            combine(
                diaryRepository.availableMoods,
                settingsRepository.selectedPaletteFlow
            ) { domainModel, paletteId ->

                val activePalette = MoodPalettes.allPalettes.find { it.id == paletteId }
                    ?: MoodPalettes.allPalettes.first()

                val uiMoods = domainModel.map { mood ->
                    mood.toUiModel(activePalette)
                }

                Pair(paletteId, uiMoods)
            }.collectLatest { (newPaletteId, uiMoods) ->
                setState {
                    copy(
                        selectedPaletteId = newPaletteId,
                        moods = uiMoods
                    )
                }
            }
        }

    }

    override fun handleEvent(event: EditMoodsEvent) {
        when (event) {
            is EditMoodsEvent.OnBackClick -> setEffect { EditMoodsEffect.NavigateBack }
            is EditMoodsEvent.SelectPalette -> savePalette(event.paletteId)
            is EditMoodsEvent.SaveMood -> {
                viewModelScope.launch {
                    val iconKeyToSave = MoodIconManager.getKeyByRes(event.newIconRes)

                    diaryRepository.updateMoodDetails(
                        moodId = event.id,
                        customName = event.newName,
                        iconKey = iconKeyToSave
                    )
                }

            }

            is EditMoodsEvent.ReplaceMood -> {
                viewModelScope.launch {
                    diaryRepository.replaceMood(event.oldMoodId, event.targetMoodId)
                }
            }
        }
    }

    private fun savePalette(paletteId: Int) {
        setState { copy(selectedPaletteId = paletteId) }

        viewModelScope.launch {
            settingsRepository.saveSelectedPalette(paletteId)
        }
    }
}