package ru.anlyashenko.atmosphereapp.feature.calendar.ui

import android.widget.Space
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.anlyashenko.atmosphereapp.core.designsystem.theme.AtmosphereAppTheme
import ru.anlyashenko.atmosphereapp.core.designsystem.theme.MainTitleColorLight
import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale

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
    AtmosphereAppTheme() {
        CalendarScreen(
            selectedDate = LocalDate.of(2026, 3, 11),
            moodMap = mockMoodMap,
            note = mockNote,
            onDateClick = { },
            onDeleteNote = { }
        )
    }

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
    val startPage = 500
    val pagerState = rememberPagerState(initialPage = startPage, pageCount = { startPage + 1 })
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
    ) {
        // Блок даты
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 7.dp, end = 7.dp, top = 8.dp, bottom = 8.dp),
            shape = RoundedCornerShape(30.dp),
            color = MaterialTheme.colorScheme.secondary,
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 37.dp),
            ) {
                Text(
                    text = displayMonth.getDisplayName(
                        TextStyle.FULL_STANDALONE, Locale("ru")
                    ).replaceFirstChar { it.uppercase() },
                    fontSize = 64.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = displayYear.toString(),
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Medium,
                    color = MainTitleColorLight.copy(alpha = 0.3f)
                )
            }
        }

        // Блок Календаря
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 7.dp, end = 7.dp, top = 0.dp, bottom = 8.dp),
            shape = RoundedCornerShape(30.dp),
            color = MaterialTheme.colorScheme.surface,
        ) {
            Column(modifier = Modifier.padding(vertical = 16.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {

                    IconButton(
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ChevronLeft,
                            contentDescription = "Предыдущий месяц",
                            tint = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    IconButton(
                        onClick = {
                            if (pagerState.currentPage < 500) {
                                scope.launch {
                                    pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                }
                            }
                        },
                        enabled = pagerState.currentPage < 500
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ChevronRight,
                            contentDescription = "Следующий месяц",
                            tint = if (pagerState.currentPage < 500) MaterialTheme.colorScheme.onBackground
                            else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
                        )
                    }

                }

                CalendarGrid(
                    pagerState = pagerState,
                    currentDate = selectedDate,
                    moodMap = moodMap,
                    selectedDate = selectedDate,
                    onDateClick = onDateClick,
                    onMonthChanged = { year, month ->
                        displayYear = year
                        displayMonth = month
                    }
                )
                Spacer(Modifier.height(12.dp))
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .width(32.dp)
                            .height(4.dp)
                            .clip(RoundedCornerShape(50.dp))
                            .background(MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f))
                    )
                }
                Spacer(Modifier.height(8.dp))
            }
        }

        if (!note.isNullOrEmpty()) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 7.dp, end = 7.dp, top = 0.dp, bottom = 8.dp),
                shape = RoundedCornerShape(30.dp),
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(modifier = Modifier.padding(top = 16.dp, end = 0.dp, bottom = 0.dp)) {
                    DayNoteSection(
                        note = note,
                        moodColor = moodMap[selectedDate],
                        selectedDate = selectedDate,
                        onDelete = onDeleteNote
                    )
                }
            }
        }
        Spacer(Modifier.height(32.dp))
    }
}


@Composable
fun CalendarGrid(
    pagerState: PagerState,
    currentDate: LocalDate,
    moodMap: Map<LocalDate, Color>,
    selectedDate: LocalDate,
    onDateClick: (LocalDate) -> Unit,
    onMonthChanged: (year: Int, month: Month) -> Unit,
) {
    val startPage = 500
    val today = remember { LocalDate.now() }

    LaunchedEffect(pagerState.currentPage) {
        val offset = (pagerState.currentPage - startPage)
        val newDate = LocalDate.of(currentDate.year, currentDate.month, 1)
            .plusMonths(offset.toLong())
        onMonthChanged(newDate.year, newDate.month)
    }

    LaunchedEffect(Unit) {
        delay(50)
        pagerState.animateScrollBy(value = -20f, animationSpec = tween(400))
        pagerState.animateScrollBy(value = 20f, animationSpec = tween(400))

    }

    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val cellsSize = maxWidth / 7
        val calendarHeight = cellsSize * 6 + 8.dp

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(calendarHeight),
        ) { page ->
            val offset = page - startPage
            val pageDate = LocalDate.of(today.year, today.month, 1)
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

    Column(modifier = Modifier.padding(horizontal = 8.dp)) {
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

        val rows = (offset + daysInMonth + 6) / 7

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
                            val today = LocalDate.now()
                            val isFuture = date.isAfter(today)
                            val hasNoMood = moodColor == null

                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .background(
                                        when {
                                            isFuture || hasNoMood -> Color.Transparent
                                            else -> moodColor
                                        }
                                    )
                                    .border(
                                        width = 1.dp,
                                        color = when {
                                            isSelected -> MaterialTheme.colorScheme.primary
                                            isFuture || hasNoMood -> MaterialTheme.colorScheme.onSurface.copy(
                                                0.2f
                                            )

                                            else -> Color.Transparent
                                        },
                                        shape = CircleShape
                                    )
                                    .clickable { onDateClick(date) }
                            ) {

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
    moodColor: Color?,
    selectedDate: LocalDate,
    onDelete: () -> Unit
) {
    if (note.isNullOrEmpty()) return

    Column(
        modifier = Modifier.padding(
            start = 16.dp,
            top = 16.dp,
        )
    ) {
        Text(
            text = "Ваша запись в этот день: ${selectedDate.dayOfMonth} ${
                selectedDate.month.getDisplayName(
                    TextStyle.FULL_STANDALONE, Locale("ru")
                )
            }",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )

        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            Box(
                modifier = Modifier
                    .width(8.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(50.dp))
                    .background(moodColor ?: MaterialTheme.colorScheme.primary)
            )
            Spacer(Modifier.width(16.dp))
            Text(
                text = note,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
        Spacer(Modifier.height(32.dp))
        Button(
            onClick = onDelete,
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 9.dp, bottom = 9.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.error.copy(alpha = 0.07f),
                contentColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text(
                text = "Удалить",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}


