package ru.anlyashenko.atmosphereapp.feature.setting_edit_moods.ui

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import ru.anlyashenko.atmosphereapp.core.utils.MoodPalettes
import ru.anlyashenko.atmosphereapp.core.mvi.BaseViewModel
import ru.anlyashenko.atmosphereapp.domain.repository.DiaryRepository
import ru.anlyashenko.atmosphereapp.domain.repository.SettingsRepository
import ru.anlyashenko.atmosphereapp.feature.home.models.MoodUiModel
import javax.inject.Inject

@HiltViewModel
class EditMoodsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val diaryRepository: DiaryRepository,
) : BaseViewModel<EditMoodsContract.Event, EditMoodsContract.State, EditMoodsContract.Effect>(){

    val moodsFlow: Flow<List<MoodUiModel>> = combine(
        diaryRepository.availableMoods,
        settingsRepository.selectedPaletteFlow
    ) { moods, selectedPaletteId ->
        val activePalette = MoodPalettes.getPaletteById(selectedPaletteId)

        moods.map { mood ->
            val colorIndex = mood.level - 1
            val dynamicColor = activePalette.colors.getOrElse(colorIndex) { mood.color }
            mood.copy(color = dynamicColor)
        }
    }

    override fun createInitialState() = EditMoodsContract.State()

    init {

        viewModelScope.launch {
            moodsFlow.collectLatest { coloredMoods ->
                setState { copy(moods = coloredMoods) }
            }
        }

        viewModelScope.launch {
            settingsRepository.selectedPaletteFlow.collectLatest { paletteId ->
                setState { copy(selectedPaletteId = paletteId) }
            }
        }
    }

    override fun handleEvent(event: EditMoodsContract.Event) {
        when (event) {
            is EditMoodsContract.Event.OnBackClick -> setEffect { EditMoodsContract.Effect.NavigateBack }
            is EditMoodsContract.Event.SelectPalette -> savePalette(event.paletteId)
            is EditMoodsContract.Event.SaveMood -> {
                viewModelScope.launch {
                    diaryRepository.updateMoodDetails(
                        moodId = event.id,
                        customName = event.newName,
                        iconRes = event.newIconRes
                    )
                }

            }
            is EditMoodsContract.Event.ReplaceMood -> {
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