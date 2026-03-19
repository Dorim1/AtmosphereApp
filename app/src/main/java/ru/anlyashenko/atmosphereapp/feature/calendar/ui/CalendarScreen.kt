package ru.anlyashenko.atmosphereapp.feature.calendar.ui

import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.anlyashenko.atmosphereapp.core.designsystem.theme.MainTitleColorLight
import ru.anlyashenko.atmosphereapp.core.designsystem.theme.SecondaryGrayLight
import java.time.LocalDate
import java.time.Month
import java.time.Period
import java.util.Locale
import kotlin.comparisons.then
import kotlin.text.get

val mockMoodMap: Map<LocalDate, Color> = mapOf(
    LocalDate.of(2026, 3, 2) to Color(0xFFB5EAB5),  // зелёный — отлично
    LocalDate.of(2026, 3, 3) to Color(0xFFB5EAB5),
    LocalDate.of(2026, 3, 4) to Color(0xFFB5EAB5),
    LocalDate.of(2026, 3, 5) to Color(0xFFFFE0A0),  // жёлтый — нормально
    LocalDate.of(2026, 3, 6) to Color(0xFFFFB5B5),  // красный — плохо
    LocalDate.of(2026, 3, 7) to Color(0xFFFFD4A0),  // оранжевый
    LocalDate.of(2026, 3, 8) to Color(0xFFFFB5B5),
    LocalDate.of(2026, 3, 9) to Color(0xFFB5EAB5),
    LocalDate.of(2026, 3, 10) to Color(0xFFFFB5B5),
    LocalDate.of(2026, 3, 11) to Color(0xFFB5EAB5),
)

val mockNote = "Сегодня был продуктивный день, закончил важную задачу."

@Composable
@Preview
private fun CalendarScreenPreview() {
    CalendarScreen(
        selectedDate = LocalDate.of(2026, 3, 11),
        moodMap = mockMoodMap,
        note = mockNote,
        onDateClick = { },
        onDeleteNote = { }
    )
}

@Composable
fun CalendarScreen(
    selectedDate: LocalDate,
    moodMap: Map<LocalDate, Color>,
    note: String?,
    onDateClick: (LocalDate) -> Unit,
    onDeleteNote: () -> Unit
) {
    var displayYear by remember { mutableIntStateOf(selectedDate.year) }
    var displayMonth by remember { mutableStateOf(selectedDate.month) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        Spacer(Modifier.height(46.dp))

        Text(
            text = "Ваш Календарь",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(41.dp))

        PeriodFilter(
            selected = PeriodType.Day,
            onSelect = {}
        )

        Spacer(Modifier.height(40.dp))

        Text(
            text = displayYear.toString(),
            style = MaterialTheme.typography.bodyMedium,
            color = MainTitleColorLight.copy(alpha = 0.6f),
            modifier = Modifier.padding(horizontal = 23.dp)
        )
        Text(
            text = displayMonth.getDisplayName(
                java.time.format.TextStyle.FULL_STANDALONE,
                Locale("ru")
            )
                .replaceFirstChar { it.uppercase() },
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(horizontal = 23.dp)
        )

        Spacer(Modifier.height(21.dp))

        CalendarGrid(
            currentDate = selectedDate,
            moodMap = moodMap,
            selectedDate = selectedDate,
            onDateClick = onDateClick,
            onMonthChanged = { year, month ->
                displayYear = year
                displayMonth = month
            }
        )
        Spacer(Modifier.padding(24.dp))
        HorizontalDivider(modifier = Modifier.padding(horizontal = 60.dp))
        Spacer(Modifier.padding(24.dp))

        DayNoteSection(
            note = note,
            onDelete = onDeleteNote
        )
        Spacer(Modifier.height(32.dp))

    }


}

@Composable
fun PeriodFilter(
    selected: PeriodType,
    onSelect: (PeriodType) -> Unit
) {
    val periods = PeriodType.entries

    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        periods.forEach { period ->
            val isSelected = period == selected
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(
                        if (isSelected) MaterialTheme.colorScheme.primary
                        else Color.Transparent
                    )
                    .clickable { onSelect(period) }
                    .padding(horizontal = 24.dp, vertical = 6.dp)
            ) {
                Text(
                    text = period.label,
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary
                    else MainTitleColorLight,
                    fontSize = 14.sp,
                    lineHeight = 14.sp
                )
            }
        }
    }
}

enum class PeriodType(val label: String) {
    Day("День"), Week("Неделя"), Month("Месяц"), Year("Год")
}

@Composable
fun CalendarGrid(
    currentDate: LocalDate,
    moodMap: Map<LocalDate, Color>,
    selectedDate: LocalDate,
    onDateClick: (LocalDate) -> Unit,
    onMonthChanged: (year: Int, month: Month) -> Unit
) {
    val startPage = 500
    val pagerState = rememberPagerState(initialPage = startPage, pageCount = { 1000 })

    LaunchedEffect(pagerState.currentPage) {
        val offset = (pagerState.currentPage - startPage)
        val newDate = LocalDate.of(currentDate.year, currentDate.month, 1)
            .plusMonths(offset.toLong())
        onMonthChanged(newDate.year, newDate.month)
    }

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth()
    ) { page ->
        val offset = page - startPage
        val pageDate = LocalDate.of(currentDate.year, currentDate.month, 1)
            .plusMonths(offset.toLong())

        CalendarMonthPage(
            year = pageDate.year,
            month = pageDate.month,
            moodMap = moodMap,
            selectedDate = selectedDate,
            onDateClick = onDateClick
        )
    }

}

@Composable
fun CalendarMonthPage(
    year: Int,
    month: Month,
    moodMap: Map<LocalDate, Color>,
    selectedDate: LocalDate,
    onDateClick: (LocalDate) -> Unit
) {
    val firstDay = LocalDate.of(year, month, 1)
    val offset = firstDay.dayOfWeek.value - 1
    val daysInMonth = firstDay.lengthOfMonth()
    val dayLabels = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")

    Column(modifier = Modifier.padding(horizontal = 23.dp)) {
        Row(modifier = Modifier.fillMaxWidth()) {
            dayLabels.forEach { label ->
                Text(
                    text = label,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = MainTitleColorLight
                )
            }
        }

        Spacer(Modifier.height(8.dp))

        val rows = (offset + daysInMonth) / 7

        repeat(rows) { row ->
            Row(modifier = Modifier.fillMaxWidth()) {
                repeat(7) { col ->
                    val index = row * 7 + col
                    val dayNumber = index - offset + 1

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .weight(1f)
                            .aspectRatio(1f)
                    ) {
                        if (dayNumber in 1..daysInMonth) {
                            val date = LocalDate.of(year, month, dayNumber)
                            val moodColor = moodMap[date]
                            val isSelected = date == selectedDate

                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(moodColor ?: Color.Transparent)
                                    .then(
                                        if (isSelected) Modifier.border(
                                            2.dp,
                                            MaterialTheme.colorScheme.primary,
                                            CircleShape
                                        ) else Modifier
                                    )
                                    .clickable { onDateClick(date) }
                            ) {
                                Text(
                                    text = dayNumber.toString(),
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.SemiBold
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DayNoteSection(
    note: String?,
    onDelete: () -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 23.dp)
    ) {
        Text(
            text = "Ваша запись в этот день",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(Modifier.height(18.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 188.dp)
                .clip(RoundedCornerShape(30.dp))
                .border(2.dp, SecondaryGrayLight, RoundedCornerShape(30.dp))
                .background(Color.Transparent)
                .padding(16.dp)
        ) {
            if (note.isNullOrEmpty()) {
                Text(
                    text = "Запись отсутствует",
                    color = MainTitleColorLight.copy(alpha = 0.5f)
                )
            } else {
                Column() {
                    Text(text = note)
                    Spacer(Modifier.height(8.dp))
                    TextButton(
                        onClick = onDelete,
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Text("Удалить")
                    }
                }
            }
        }
    }
}

