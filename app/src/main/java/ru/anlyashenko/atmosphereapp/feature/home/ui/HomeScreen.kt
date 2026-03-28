package ru.anlyashenko.atmosphereapp.feature.home.ui

import android.graphics.drawable.Icon
import android.widget.Space
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
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Mood
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

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
    val weekRecords = remember { getDaysFromMondayToToday() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 7.dp)
    ) {
        Spacer(Modifier.height(6.dp))
        WeatherCard()

        Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
            weekRecords.forEachIndexed { index, record ->
                val isToday = index == 0

                DayEntryCard(
                    record = record,
                    isToday = isToday
                )

                if (isToday) {
                    CurrentDayActionRow(
                        hasMood = record.hasMood,
                        onMoodClick = {
                            // TODO: Открыть выбор настроения
                        },
                        onNoteClick = {
                            // TODO: Открыть написание заметки
                        }
                    )
                }
            }
        }
        Spacer(Modifier.height(6.dp))
    }
}


@Composable
fun WeatherCard(
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        color = MaterialTheme.colorScheme.primary
    ) {
        WeatherSection()
    }
}
@Composable
fun WeatherSection() {
    Box(
        modifier = Modifier.padding(horizontal = 28.dp, vertical = 39.dp),
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
                WeatherHourlyItem("16:00", painterResource(R.drawable.ic_weather_hail), "+13°")
                WeatherHourlyItem("20:00", painterResource(R.drawable.ic_weather_hail), "+9°")
                WeatherHourlyItem("00:00", painterResource(R.drawable.ic_cloud), "+3°")
            }
        }
    }
}


@Composable
fun WeatherHourlyItem(
    time: String,
    icon: Painter,
    temp: String,
) {
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
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 14.sp,
            lineHeight = 14.sp,
            fontWeight = FontWeight.SemiBold
        )

    }
}

@Composable
fun CurrentDayActionRow(
    hasMood: Boolean,
    onMoodClick:() -> Unit,
    onNoteClick:() -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(158.dp), // TODO: Всегда форма квадрата
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Surface(
            modifier = Modifier
                .weight(1.5f)
                .fillMaxHeight(),
            shape = RoundedCornerShape(30.dp),
            color = MaterialTheme.colorScheme.primary,
            onClick = onMoodClick
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = if (hasMood) "Изменить\nнастроение" else "Выбрать\nнастроение",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Normal
                )

                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(48.dp)
                )

            }
        }

        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            shape = RoundedCornerShape(30.dp),
            color = MaterialTheme.colorScheme.secondary,
            onClick = onNoteClick
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Rounded.Edit,
                    contentDescription = "Добавить заметку",
                    tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.size(82.dp)
                )
            }
        }
    }
}

@Composable
fun DayEntryCard(
    record: DailyRecord,
    isToday: Boolean,
    modifier: Modifier = Modifier
) {
    val formatterDayOfWeek = DateTimeFormatter.ofPattern("EEEE", Locale("ru"))
    val formatterMonth = DateTimeFormatter.ofPattern("MMM", Locale("ru"))

    val dayOfWeek = record.date.format(formatterDayOfWeek).replaceFirstChar { it.uppercase() }
    val dayOfMonth = record.date.dayOfMonth.toString().padStart(2, '0')
    val month = record.date.format(formatterMonth).uppercase()

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column() {
                Text(
                    text = dayOfWeek,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = dayOfMonth,
                    fontSize = 82.sp,
                    lineHeight = 82.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = month,
                    fontSize = 82.sp,
                    lineHeight = 82.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface

                )
            }

            Column(horizontalAlignment = Alignment.End) {
                if (isToday && !record.hasMood) {
                    Text(
                        text = "Как прошёл ваш день?",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSurface,
//                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start

                    )
                } else {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        if (record.hasNote) {
                            Icon(
                                imageVector = Icons.Rounded.Edit,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        if (record.hasMood) {
                            Icon(
                                imageVector = Icons.Rounded.Mood,
                                contentDescription = null,
                                tint = Color(0xFF8AA232),
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

// todo: Удалить
data class DailyRecord(
    val date: LocalDate,
    val hasMood: Boolean = false,
    val hasNote: Boolean = false,
)

fun getDaysFromMondayToToday(): List<DailyRecord> {
    val today = LocalDate.now()
    val monday = today.with(DayOfWeek.MONDAY)

    val daysList = mutableListOf<DailyRecord>()
    var currentDate = today

    while (!currentDate.isBefore(monday)) {
        daysList.add(
            DailyRecord(
                date = currentDate,
                hasMood = currentDate != today,
                hasNote = currentDate.dayOfMonth % 2 == 0
            )
        )
        currentDate = currentDate.minusDays(1)
    }
    return daysList
}

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


