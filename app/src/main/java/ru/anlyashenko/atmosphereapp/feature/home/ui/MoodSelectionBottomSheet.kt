package ru.anlyashenko.atmosphereapp.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.SentimentDissatisfied
import androidx.compose.material.icons.rounded.SentimentNeutral
import androidx.compose.material.icons.rounded.SentimentSatisfied
import androidx.compose.material.icons.rounded.SentimentVeryDissatisfied
import androidx.compose.material.icons.rounded.SentimentVerySatisfied
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class MoodOption(
    val id: Int,
    val title: String,
    val icon: ImageVector,
    val selectedColor: Color,
    val selectedBgColor: Color
)

val moodOptions = listOf(
    MoodOption(
        id = 1,
        title = "Отлично",
        icon = Icons.Rounded.SentimentVerySatisfied,
        selectedColor = Color(0xFF8AA232),
        selectedBgColor = Color(0xFF8AA232).copy(alpha = 0.1f)
    ),
    MoodOption(
        id = 2,
        title = "Хорошо",
        icon = Icons.Rounded.SentimentSatisfied,
        selectedColor = Color(0xFF0A6C60),
        selectedBgColor = Color(0xFF0A6C60).copy(alpha = 0.1f)
    ),
    MoodOption(
        id = 3,
        title = "Нормально",
        icon = Icons.Rounded.SentimentNeutral,
        selectedColor = Color(0xFFFFC107),
        selectedBgColor = Color(0xFFFFC107).copy(alpha = 0.1f)
    ),
    MoodOption(
        id = 4,
        title = "Плохо",
        icon = Icons.Rounded.SentimentDissatisfied,
        selectedColor = Color(0xFFFF5722),
        selectedBgColor = Color(0xFFFF5722).copy(alpha = 0.1f)
    ),
    MoodOption(
        id = 5,
        title = "Ужасно",
        icon = Icons.Rounded.SentimentVeryDissatisfied,
        selectedColor = Color(0xFFD32F2F),
        selectedBgColor = Color(0xFFD32F2F).copy(alpha = 0.1f)
    ),
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoodSelectionBottomSheet(
    onDismissRequest:() -> Unit,
    onMoodSelected: (MoodOption) -> Unit
) {
    var selectedMood by remember { mutableStateOf<MoodOption?>(null) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        dragHandle = { BottomSheetDefaults.DragHandle() }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 23.dp)
                .padding(bottom = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Выбрать настроение",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(Modifier.height(24.dp))
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                moodOptions.forEach { mood ->
                    MoodItemRow(
                        mood = mood,
                        isSelected = selectedMood?.id == mood.id,
                        onClick = { selectedMood = mood }
                    )
                }
            }

            Spacer(Modifier.height(32.dp))
            Button(
                onClick = {
                    selectedMood?.let { onMoodSelected(it) }
                    onDismissRequest()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = selectedMood != null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    disabledContainerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(30.dp)
            ) {
                Text(
                    text = "Готово",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun MoodItemRow(
    mood: MoodOption,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor = if (isSelected) mood.selectedBgColor else MaterialTheme.colorScheme.background
    val contentColor = if (isSelected) mood.selectedColor else MaterialTheme.colorScheme.onSurface

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(30.dp))
            .background(backgroundColor)
            .clickable(onClick = onClick)
            .padding(horizontal = 21.dp, vertical = 27.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = mood.icon,
                contentDescription = mood.title,
                tint = contentColor,
                modifier = Modifier.size(36.dp)
            )
            Text(
                text = mood.title,
                fontSize = 24.sp,
                color = contentColor,
            )
        }

        Box(
            modifier = Modifier
                .size(24.dp)
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = CircleShape
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(15.dp)
                        .background(mood.selectedColor, CircleShape)
                )
            }
        }
    }
}