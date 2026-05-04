package ru.anlyashenko.feature.settings.impl.moods

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import ru.anlyashenko.core.designsystem.R
import ru.anlyashenko.core.designsystem.theme.PaletteModel
import ru.anlyashenko.feature.settings.impl.model.SettingsMoodUiModel

@Composable
fun EditMoodsScreen(
    viewModel: EditMoodsViewModel = hiltViewModel(),
    onBackClick: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.effect) {
        viewModel.effect.collectLatest { effect ->
            when (effect) {
                is EditMoodsEffect.NavigateBack -> onBackClick()
            }
        }
    }

    EditMoodsScreen(
        moods = state.moods,
        palettes = state.palettes,
        selectedPaletteId = state.selectedPaletteId,
        onBackClick = { viewModel.setEvent(EditMoodsEvent.OnBackClick) },
        onSaveMood = { id, name, iconId ->
            viewModel.setEvent(
                EditMoodsEvent.SaveMood(
                    id,
                    name,
                    iconId
                )
            )
        },
        onReplaceMood = { oldId, targetId ->
            viewModel.setEvent(
                EditMoodsEvent.ReplaceMood(
                    oldId,
                    targetId
                )
            )
        },
        onSelectPalette = { viewModel.setEvent(EditMoodsEvent.SelectPalette(it)) }
    )
}


@Composable
internal fun EditMoodsScreen(
    moods: List<SettingsMoodUiModel>,
    palettes: List<PaletteModel>,
    selectedPaletteId: Int,
    onBackClick: () -> Unit,
    onSaveMood: (id: Int, name: String, iconId: Int) -> Unit,
    onReplaceMood: (oldId: Int, targetId: Int) -> Unit,
    onSelectPalette: (Int) -> Unit,
) {

    var moodToEdit by remember { mutableStateOf<SettingsMoodUiModel?>(null) }
    var moodToReplace by remember { mutableStateOf<SettingsMoodUiModel?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 23.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp, bottom = 34.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_back),
                contentDescription = stringResource(R.string.cd_settings_back),
                modifier = Modifier
                    .size(24.dp)
                    .clip(RoundedCornerShape(50.dp))
                    .clickable { onBackClick() },
            )

            Spacer(Modifier.width(18.dp))
            Text(
                text = stringResource(R.string.edit_moods_title),
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Text(
            text = stringResource(R.string.edit_moods_moods_title),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.edit_moods_moods_description),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
        )

        Spacer(Modifier.height(28.dp))
        Column(verticalArrangement = Arrangement.spacedBy(27.dp)) {
            moods.forEach { mood ->
                MoodEditItem(
                    mood = mood,
                    onEditClick = { moodToEdit = mood },
                    onReplaceClick = { moodToReplace = mood }
                )
            }
        }

        Spacer(Modifier.height(40.dp))
        Text(
            text = stringResource(R.string.edit_moods_palette_title),
            fontSize = 20.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(8.dp))
        Text(
            text = stringResource(R.string.edit_moods_palette_description),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
        )
        Spacer(Modifier.height(28.dp))
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            palettes.forEach { palette ->
                PaletteSelectionItem(
                    palette = palette,
                    isSelected = selectedPaletteId == palette.id,
                    onClick = { onSelectPalette(palette.id) }
                )
            }
        }
        Spacer(Modifier.height(40.dp))

    }

    moodToEdit?.let { mood ->
        EditMoodBottomSheet(
            mood = mood,
            onDismissRequest = { moodToEdit = null },
            onSave = { newName, newIconId ->
                onSaveMood(mood.id, newName, newIconId)
                moodToEdit = null
            }
        )
    }

    moodToReplace?.let { mood ->
        ReplaceMoodBottomSheet(
            moodToReplace = mood,
            availableMoods = moods.filter { it.id != mood.id },
            onDismissRequest = { moodToReplace = null },
            onReplaceConfirm = { targetMood ->
                onReplaceMood(mood.id, targetMood.id)
                moodToReplace = null
            }
        )
    }
}


@Composable
fun MoodEditItem(
    mood: SettingsMoodUiModel,
    onEditClick: () -> Unit,
    onReplaceClick: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(mood.color, CircleShape)
        )
        Spacer(Modifier.width(24.dp))

        Text(
            text = mood.displayName.asString(),
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f)
        )

        IconButton(
            onClick = onReplaceClick,
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_setting_replace),
                contentDescription = stringResource(R.string.cd_mood_replace),
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(Modifier.width(8.dp))

        IconButton(
            onClick = onEditClick,
            modifier = Modifier.size(32.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_edit),
                contentDescription = stringResource(R.string.cd_mood_edit),
                tint = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
fun PaletteSelectionItem(
    palette: PaletteModel,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable(
                indication = null, interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            )
            .padding(vertical = 8.dp)
            .semantics {
                selected = isSelected
                role = Role.RadioButton
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = null,
            modifier = Modifier.size(24.dp),
            colors = RadioButtonDefaults.colors(
                selectedColor = MaterialTheme.colorScheme.primary,
                unselectedColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
            )
        )
        Spacer(Modifier.width(16.dp))

        Text(
            text = stringResource(palette.nameRes),
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier.weight(1f)
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            palette.colors.forEach { color ->
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(color, CircleShape)
                )
            }
        }
    }
}