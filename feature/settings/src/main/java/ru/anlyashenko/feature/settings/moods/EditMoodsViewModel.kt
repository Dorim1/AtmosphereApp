package ru.anlyashenko.feature.settings.moods

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.anlyashenko.core.data.repository.DiaryRepository
import ru.anlyashenko.core.data.repository.SettingsRepository
import ru.anlyashenko.core.presentation.mvi.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class EditMoodsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val diaryRepository: DiaryRepository,
) : BaseViewModel<EditMoodsEvent, EditMoodsState, EditMoodsEffect>(){

    override fun createInitialState() = EditMoodsState()

    init {

        viewModelScope.launch {
            diaryRepository.availableMoods.collectLatest { coloredMoods ->
                setState { copy(moods = coloredMoods) }
            }
        }

        viewModelScope.launch {
            settingsRepository.selectedPaletteFlow.collectLatest { paletteId ->
                setState { copy(selectedPaletteId = paletteId) }
            }
        }

    }

    override fun handleEvent(event: EditMoodsEvent) {
        when (event) {
            is EditMoodsEvent.OnBackClick -> setEffect { EditMoodsEffect.NavigateBack }
            is EditMoodsEvent.SelectPalette -> savePalette(event.paletteId)
            is EditMoodsEvent.SaveMood -> {
                viewModelScope.launch {
                    diaryRepository.updateMoodDetails(
                        moodId = event.id,
                        customName = event.newName,
                        iconRes = event.newIconRes
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