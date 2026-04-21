package ru.anlyashenko.atmosphereapp.feature.setting_edit_moods.ui

import ru.anlyashenko.atmosphereapp.core.utils.MoodPalettes
import ru.anlyashenko.atmosphereapp.core.mvi.UiEffect
import ru.anlyashenko.atmosphereapp.core.mvi.UiEvent
import ru.anlyashenko.atmosphereapp.core.mvi.UiState
import ru.anlyashenko.atmosphereapp.feature.home.models.MoodUiModel
import ru.anlyashenko.atmosphereapp.feature.setting_edit_moods.models.PaletteModel

class EditMoodsContract {

    data class State(
        val selectedPaletteId: Int = 0,
        val palettes: List<PaletteModel> = MoodPalettes.allPalettes,
        val moods: List<MoodUiModel> = emptyList()
    ): UiState

    sealed interface Event: UiEvent {
        object OnBackClick : Event
        data class SelectPalette(val paletteId: Int) : Event
        data class SaveMood(val id: Int, val newName: String, val newIconRes: Int) : Event
        data class ReplaceMood(val oldMoodId: Int, val targetMoodId: Int) : Event
    }

    sealed interface Effect : UiEffect {
        object NavigateBack : Effect
    }
}