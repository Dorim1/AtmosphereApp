package ru.anlyashenko.atmosphereapp.feature.setting_edit_moods.ui

import androidx.lifecycle.viewModelScope
import androidx.room.util.copy
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import ru.anlyashenko.atmosphereapp.core.mvi.BaseViewModel
import ru.anlyashenko.atmosphereapp.domain.repository.SettingsRepository
import javax.inject.Inject

@HiltViewModel
class EditMoodsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : BaseViewModel<EditMoodsContract.Event, EditMoodsContract.State, EditMoodsContract.Effect>(){

    override fun createInitialState() = EditMoodsContract.State()

    init {
        viewModelScope.launch {
            settingsRepository.selectedPaletteFlow.collectLatest { savedPaletteId ->
                setState { copy(selectedPaletteId = savedPaletteId) }
            }
        }
    }

    override fun handleEvent(event: EditMoodsContract.Event) {
        when (event) {
            is EditMoodsContract.Event.OnBackClick -> setEffect { EditMoodsContract.Effect.NavigateBack }
            is EditMoodsContract.Event.SelectPalette -> savePalette(event.paletteId)
        }
    }

    private fun savePalette(paletteId: Int) {
        setState { copy(selectedPaletteId = paletteId) }

        viewModelScope.launch {
            settingsRepository.saveSelectedPalette(paletteId)
        }
    }
}