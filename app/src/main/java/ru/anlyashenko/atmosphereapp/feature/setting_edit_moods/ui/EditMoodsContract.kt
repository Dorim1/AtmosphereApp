package ru.anlyashenko.atmosphereapp.feature.setting_edit_moods.ui

import ru.anlyashenko.atmosphereapp.core.utils.MoodPalettes
import ru.anlyashenko.atmosphereapp.core.mvi.UiEffect
import ru.anlyashenko.atmosphereapp.core.mvi.UiEvent
import ru.anlyashenko.atmosphereapp.core.mvi.UiState
import ru.anlyashenko.atmosphereapp.feature.home.models.MoodUiModel
import ru.anlyashenko.atmosphereapp.feature.setting_edit_moods.models.PaletteModel


data class EditMoodsState(
    val selectedPaletteId: Int = 0,
    val palettes: List<PaletteModel> = MoodPalettes.allPalettes,
    val moods: List<MoodUiModel> = emptyList()
) : UiState

sealed interface EditMoodsEvent : UiEvent {
    object OnBackClick : EditMoodsEvent
    data class SelectPalette(val paletteId: Int) : EditMoodsEvent
    data class SaveMood(val id: Int, val newName: String, val newIconRes: Int) : EditMoodsEvent
    data class ReplaceMood(val oldMoodId: Int, val targetMoodId: Int) : EditMoodsEvent
}

sealed interface EditMoodsEffect : UiEffect {
    object NavigateBack : EditMoodsEffect
}
