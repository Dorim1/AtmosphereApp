package ru.anlyashenko.atmosphereapp.feature.home.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SentimentDissatisfied
import androidx.compose.material.icons.outlined.SentimentNeutral
import androidx.compose.material.icons.outlined.SentimentSatisfied
import androidx.compose.material.icons.outlined.SentimentVeryDissatisfied
import androidx.compose.material.icons.outlined.SentimentVerySatisfied
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.anlyashenko.atmosphereapp.R
import ru.anlyashenko.atmosphereapp.core.designsystem.elements.StopPostScrollNestedScrollConnection
import ru.anlyashenko.atmosphereapp.core.designsystem.theme.AtmosphereAppTheme
import ru.anlyashenko.atmosphereapp.core.designsystem.theme.MainTitleColorLight
import ru.anlyashenko.atmosphereapp.core.designsystem.theme.SecondaryGrayLight
import ru.anlyashenko.atmosphereapp.data.model.DayUI
import ru.anlyashenko.atmosphereapp.data.model.MoodUI

@Composable
@Preview
private fun HomeScreenPreview() {
    AtmosphereAppTheme {
        HomeScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    var noteText by remember { mutableStateOf("") }
    var isNoteMode by remember { mutableStateOf(false) }

    // Блок погоды
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 7.dp, end = 7.dp, top = 8.dp, bottom = 8.dp),
            shape = RoundedCornerShape(30.dp),
            color = MaterialTheme.colorScheme.primary
        ) {
            WeatherSection()
            
        }

        // Блок недели
        if (!isNoteMode) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 7.dp, end = 7.dp, top = 0.dp, bottom = 8.dp),
                shape = RoundedCornerShape(30.dp),
                color = MaterialTheme.colorScheme.surface,
                ) {
                Column(
                    modifier = Modifier.padding(vertical = 32.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) {
                        // Заголовок недели
                        Text(
                            text = stringResource(R.string.home_title_week_section),
                            style = MaterialTheme.typography.headlineLarge
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            text = "с 9 марта, 2026 по 16 марта, 2026",
                            style = MaterialTheme.typography.titleMedium
                        )
                    }
                    Spacer(Modifier.height(32.dp))
                    WeekRow(days = days)
                }
            }

        }
        // Блок настроений
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 7.dp, end = 7.dp, top = 0.dp, bottom = 8.dp),
            shape = RoundedCornerShape(30.dp),
            color = MaterialTheme.colorScheme.surface,
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                Spacer(Modifier.height(32.dp))
                Text(
                    text = "Мой День",
                    style = MaterialTheme.typography.headlineLarge
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Какое у вас настроение?",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(16.dp))

                if (!isNoteMode) {
                    MoodColumn(moods = systemMoods, onMoodSelected = {})
                    AddNoteDayItem(onClick = { isNoteMode = true })
                    Spacer(Modifier.height(18.dp))

                } else {
                    NoteTextField(
                        value = noteText,
                        onValueChange = { noteText = it }
                    )
                }
            }
        }

        Spacer(Modifier.height(16.dp))
        FooterSection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 22.dp),
            isNoteMode = isNoteMode,
            onGetInClick = { },
            onSaveMode = { },
            onCancel = { isNoteMode = false }
        )



    }
}


@Composable
fun WeatherSection() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 28.dp, vertical = 39.dp)
    ) {
        Column() {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column() {
                    Text(
                        text = "12°",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 64.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Дождь со\nснегом",
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Icon(
                    painter = painterResource(R.drawable.ic_weather_hail),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(120.dp)
                )
            }
            Spacer(Modifier.height(44.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf(
                    Triple("16:00", painterResource(R.drawable.ic_weather_hail), "+13°"),
                    Triple("20:00", painterResource(R.drawable.ic_weather_hail), "+9°"),
                    Triple("00:00", painterResource(R.drawable.ic_cloud), "+3°"),
                ).forEach { (time, icon, temp) ->
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(1.dp)
                    ) {
                        Icon(
                            painter = icon,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(48.dp)
                        )
                        Text(
                            text = time,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontSize = 16.sp,
                            lineHeight = 16.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = temp,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                            fontSize = 14.sp,
                            lineHeight = 14.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun NoteTextField(
    modifier: Modifier = Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    maxLength: Int = 250,
) {

    val isOverLimit = value.length >= maxLength
    val borderColor by animateColorAsState(
        targetValue = if (isOverLimit) Color.Red else SecondaryGrayLight,
        label = "borderColor"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 200.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(2.dp, borderColor, RoundedCornerShape(30.dp))
    ) {
        BasicTextField(
            value = value,
            onValueChange = { if (it.length <= maxLength) onValueChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 14.dp, end = 14.dp, top = 14.dp, bottom = 60.dp),
            textStyle = TextStyle(
                fontSize = 14.sp,
                color = MainTitleColorLight
            ),
            decorationBox = { innerTextField ->
                if (value.isEmpty()) {
                    Text(
                        text = "Напишите как прошёл ваш день",
                        fontSize = 14.sp,
                        color = MainTitleColorLight.copy(alpha = 0.5f)
                    )
                }
                innerTextField()
            }
        )

        Text(
            text = "${value.length} / $maxLength",
            fontSize = 10.sp,
            fontWeight = FontWeight.SemiBold,
            color = if (isOverLimit) Color.Red else MainTitleColorLight.copy(alpha = 0.3f),
            lineHeight = 10.sp,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(horizontal = 24.dp, vertical = 20.dp)
        )

    }
}

// todo: Удалить
val days = listOf(
    DayUI(9, "марта", DayState.Last),
    DayUI(10, "марта", DayState.Last),
    DayUI(11, "марта", DayState.Today),
    DayUI(12, "марта", DayState.Next),
    DayUI(13, "марта", DayState.Next),
    DayUI(14, "марта", DayState.Next),
    DayUI(15, "марта", DayState.Next),
    DayUI(16, "марта", DayState.Next),
)

val systemMoods = listOf(
    MoodUI(1, "Отлично", Color(0xFFE4F9D4), Icons.Outlined.SentimentVerySatisfied),
    MoodUI(2, "Хорошо", Color(0xFFE4F3D6), Icons.Outlined.SentimentSatisfied),
    MoodUI(3, "Нормально", Color(0xFFFFEAC1), Icons.Outlined.SentimentNeutral),
    MoodUI(4, "Плохо", Color(0xFFD4D4FF), Icons.Outlined.SentimentDissatisfied),
    MoodUI(5, "Ужасно", Color(0xFFFFD1DC), Icons.Outlined.SentimentVeryDissatisfied)
)

@Composable
fun WeekRow(days: List<DayUI>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(26.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(days) { day ->
            DayListItem(
                day = day.number,
                month = day.month,
                state = day.state,
                onClick = { }
            )
        }
    }
}

@Composable
fun MoodColumn(
    moods: List<MoodUI>,
    onMoodSelected: (MoodUI) -> Unit
) {
    var selectedMooId by remember { mutableStateOf<Int?>(null) }
    var moodToEdit by remember { mutableStateOf<MoodUI?>(null) }

    Column(
        verticalArrangement = Arrangement.spacedBy(26.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 4.dp,
                bottom = 23.dp
            )
    ) {
        moods.forEach { mood ->
            MoodListItem(
                mood = mood,
                isSelected = selectedMooId == mood.id,
                onClick = {
                    selectedMooId = mood.id
                    onMoodSelected(mood)
                },
                onEdit = {
                    moodToEdit = mood
                }
            )
        }
    }

    moodToEdit?.let { mood ->
        AlertDialog(
            onDismissRequest = { moodToEdit = null },
            title = { Text(text = "Редактировать настроение") },
            text = { Text(text = "Здесь будут настройки для ${mood.title}") },
            confirmButton = {
                TextButton(onClick = {
                    // todo: Сохранить изменения
                    moodToEdit = null
                }) {
                    Text("Сохранить")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    moodToEdit = null
                }) { Text("Отмена") }
            }
        )
    }
}

@Composable
fun FooterSection(
    modifier: Modifier = Modifier,
    isNoteMode: Boolean = false,
    onGetInClick: () -> Unit,
    onSaveMode: () -> Unit = {},
    onCancel: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 23.dp),
        horizontalArrangement = if (isNoteMode) Arrangement.spacedBy(11.dp) else Arrangement.Center
    ) {
        if (isNoteMode) {
            Button(
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                onClick = onCancel,
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = SecondaryGrayLight,
                    contentColor = MainTitleColorLight
                )
            ) {
                Text(
                    text = "Отмена",
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            Button(
                modifier = Modifier
                    .weight(1f)
                    .height(50.dp),
                onClick = onSaveMode,
                shape = RoundedCornerShape(50.dp),
            ) {
                Text(
                    text = "Добавить",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        } else {
            Button(
                modifier = Modifier.size(200.dp, 50.dp),
                onClick = onGetInClick,
                shape = RoundedCornerShape(50.dp),
            ) {
                Text(
                    text = "Сохранить",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

    }

}

@Composable
fun AddNoteDayItem(onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() },
                onClick = onClick
            ),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        border = BorderStroke(2.dp, SecondaryGrayLight)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 88.dp)
                .padding(horizontal = 19.dp, vertical = 23.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column() {
                Text(
                    text = "Создать заметку",
                    fontSize = 20.sp,
                    color = MainTitleColorLight
                )
                Text(
                    text = "Как прошёл день (необязательно)",
                    fontSize = 15.sp,
                    color = MainTitleColorLight.copy(alpha = 0.5f)
                )
            }
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
