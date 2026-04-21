package ru.anlyashenko.atmosphereapp.feature.setting_edit_moods.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.anlyashenko.atmosphereapp.R
import ru.anlyashenko.atmosphereapp.core.utils.MoodIconManager
import ru.anlyashenko.atmosphereapp.feature.home.models.MoodUiModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMoodBottomSheet(
    mood: MoodUiModel,
    onDismissRequest: () -> Unit,
    onSave: (newName: String, newIconRes: Int) -> Unit
) {

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    val displayName = mood.displayName.asString()
    var nameText by remember { mutableStateOf(displayName) }

    val availableIconIndex = remember { MoodIconManager.getAllIconRes() }

    val initialIconIndex = availableIconIndex.indexOf(mood.iconRes).takeIf { it >= 0 } ?: 0
    var selectedIconIndex by remember { mutableIntStateOf(initialIconIndex) }

    val maxCharLimit = 20
    val isLimitReached = nameText.length == maxCharLimit

    val animatedCounterColor by animateColorAsState(
        targetValue = if (isLimitReached) MaterialTheme.colorScheme.error.copy(alpha = 0.8f)
        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
        animationSpec = tween(durationMillis = 300),
        label = "CounterColorAnimation"
    )

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 32.dp)
        ) {
            Text(
                text = mood.displayName.asString(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.edit_mood_change_name),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Spacer(Modifier.height(24.dp))

            OutlinedTextField(
                value = nameText,
                onValueChange = { newText ->
                    if (newText.length <= maxCharLimit) {
                        nameText = newText
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(50.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (isLimitReached)
                        MaterialTheme.colorScheme.error.copy(alpha = 0.8f)
                    else
                        MaterialTheme.colorScheme.primary,

                    unfocusedBorderColor = if (isLimitReached)
                        MaterialTheme.colorScheme.error.copy(alpha = 0.8f)
                    else
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),

                    cursorColor = MaterialTheme.colorScheme.onSurface
                ),
                trailingIcon = {
                    Box(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .size(12.dp)
                            .background(mood.color, CircleShape)
                    )
                },
                singleLine = true,
                supportingText = {
                    Text(
                        text = "${nameText.length} / $maxCharLimit",
                        modifier = Modifier.fillMaxWidth(),
                        color = animatedCounterColor,
                        textAlign = TextAlign.End
                    )
                }
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = stringResource(R.string.edit_mood_change_icon),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
            Spacer(Modifier.height(16.dp))

            BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
                val itemSize = (maxWidth - 16.dp * 5) / 6

                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                ) {
                    availableIconIndex.forEachIndexed { index, iconRes ->
                        val isSelected = index == selectedIconIndex

                        Box(
                            modifier = Modifier
                                .size(itemSize)
                                .clip(CircleShape)
                                .background(if (isSelected) mood.color.copy(alpha = 0.8f) else Color.Transparent)
                                .clickable { selectedIconIndex = index },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(iconRes),
                                contentDescription = null,
                                tint = if (isSelected) Color.Black else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                                modifier = Modifier.fillMaxSize(0.65f)
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.height(40.dp))

            Button(
                onClick = { onSave(nameText, availableIconIndex[selectedIconIndex]) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = nameText.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(
                    text = stringResource(R.string.text_confirm_button),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }

}