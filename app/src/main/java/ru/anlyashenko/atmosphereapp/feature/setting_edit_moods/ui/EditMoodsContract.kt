package ru.anlyashenko.atmosphereapp.feature.setting_edit_moods.ui

import ru.anlyashenko.atmosphereapp.core.design_system.ui.MoodThemeData
import ru.anlyashenko.atmosphereapp.core.mvi.UiEffect
import ru.anlyashenko.atmosphereapp.core.mvi.UiEvent
import ru.anlyashenko.atmosphereapp.core.mvi.UiState
import ru.anlyashenko.atmosphereapp.feature.setting_edit_moods.models.PaletteModel

// todo: МОжет убрать из класса
class EditMoodsContract {

    data class State(
        val selectedPaletteId: Int = 0,
        val palettes: List<PaletteModel> = MoodThemeData.palettes,
    ): UiState

    sealed interface Event: UiEvent {
        object OnBackClick : Event
        data class SelectPalette(val paletteId: Int) : Event
    }

    sealed interface Effect : UiEffect {
        object NavigateBack : Effect
    }
}