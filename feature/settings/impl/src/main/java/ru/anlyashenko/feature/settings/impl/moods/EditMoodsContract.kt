package ru.anlyashenko.feature.settings.impl.moods

import ru.anlyashenko.core.designsystem.theme.MoodPalettes
import ru.anlyashenko.core.designsystem.theme.PaletteModel
import ru.anlyashenko.core.presentation.mvi.UiEffect
import ru.anlyashenko.core.presentation.mvi.UiEvent
import ru.anlyashenko.core.presentation.mvi.UiState
import ru.anlyashenko.feature.settings.impl.model.SettingsMoodUiModel

data class EditMoodsState(
    val selectedPaletteId: Int = 0,
    val palettes: List<PaletteModel> = MoodPalettes.allPalettes,
    val moods: List<SettingsMoodUiModel> = emptyList()
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