package ru.anlyashenko.atmosphereapp.feature.calendar.ui

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.anlyashenko.atmosphereapp.R
import ru.anlyashenko.atmosphereapp.core.design_system.ui.DragHandle
import java.time.LocalDate
import java.time.Month
import java.time.format.TextStyle
import java.util.Locale

@Composable
fun CalendarRoute(
    viewModel: CalendarViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    CalendarScreen(
        selectedDate = state.selectedDate,
        moodMap = state.moodMap,
        daysWithNotes = state.daysWithNotes,
        note = state.selectedRecord?.note?.takeIf { it.isNotBlank() },
        onDateClick = { date ->
            viewModel.setEvent(CalendarEvent.OnDateSelected(date))
        },
        onDeleteNote = {
            viewModel.setEvent(CalendarEvent.OnDeleteNote)
        }
    )

}

@Composable
fun CalendarScreen(
    selectedDate: LocalDate,
    moodMap: Map<LocalDate, Color>,
    daysWithNotes: Set<LocalDate>,
    note: String?,
    onDateClick: (LocalDate) -> Unit,
    onDeleteNote: () -> Unit,
) {
    var displayYear by remember { mutableIntStateOf(selectedDate.year) }
    var displayMonth by remember { mutableStateOf(selectedDate.month) }
    val startPage = 500
    val pagerState = rememberPagerState(initialPage = startPage, pageCount = { startPage + 1 })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 7.dp)
    ) {
        Spacer(Modifier.height(6.dp))
        CalendarHeaderCard(
            displayMonth = displayMonth,
            displayYear = displayYear
        )

        Spacer(Modifier.height(6.dp))
        CalendarPagerCard(
            pagerState = pagerState,
            startPage = startPage,
            selectedDate = selectedDate,
            moodMap = moodMap,
            daysWithNotes = daysWithNotes,
            onDateClick = onDateClick,
            onMonthChanged = { year, month ->
                displayYear = year
                displayMonth = month
            }
        )

        Spacer(Modifier.height(6.dp))
        if (!note.isNullOrEmpty()) {
            NoteCard(
                note = note,
                moodColor = moodMap[selectedDate],
                selectedDate = selectedDate,
                onDelete = onDeleteNote
            )
        }
        Spacer(Modifier.height(6.dp))

    }

}

@Composable
fun NoteCard(
    note: String,
    moodColor: Color?,
    selectedDate: LocalDate,
    onDelete: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(modifier = Modifier.padding(top = 32.dp)) {
            DayNoteSection(
                note = note,
                moodColor = moodColor,
                selectedDate = selectedDate,
                onDelete = onDelete
            )
        }

    }
}

@Composable
fun DayNoteSection(
    note: String,
    moodColor: Color?,
    selectedDate: LocalDate,
    onDelete: () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    val monthName = selectedDate.month.getDisplayName(TextStyle.FULL, Locale.getDefault())

    if (showDeleteDialog) {
        DeleteConfirmationDialog(
            onDismiss = { showDeleteDialog = false },
            onConfirm = onDelete
        )
    }

    Column(modifier = Modifier.padding(start = 16.dp)) {
        Text(
            text = stringResource(
                R.string.calendar_record_date,
                selectedDate.dayOfMonth,
                monthName
            ),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )
        Spacer(Modifier.height(6.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
                .padding(end = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(6.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(50.dp))
                    .background(moodColor ?: MaterialTheme.colorScheme.primary)
            )
            Spacer(Modifier.width(16.dp))
            Text(
                text = note,
                fontSize = 24.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
        Spacer(Modifier.height(32.dp))
        Button(
            onClick = {
                showDeleteDialog = true
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 9.dp, bottom = 9.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f),
                contentColor = MaterialTheme.colorScheme.error
            )
        ) {
            Text(
                text = stringResource(R.string.text_delete_button),
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

@Composable
fun CalendarPagerCard(
    pagerState: PagerState,
    startPage: Int,
    selectedDate: LocalDate,
    moodMap: Map<LocalDate, Color>,
    daysWithNotes: Set<LocalDate>,
    onDateClick: (LocalDate) -> Unit,
    onMonthChanged: (Int, Month) -> Unit,
) {
    val scope = rememberCoroutineScope()

    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        color = MaterialTheme.colorScheme.surface
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
                        contentDescription = stringResource(R.string.cd_previous_button),
                        tint = MaterialTheme.colorScheme.onSurface
                    )
                }
                IconButton(
                    onClick = {
                        if (pagerState.currentPage < startPage) {
                            scope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                            }
                        }
                    },
                    enabled = pagerState.currentPage < startPage
                ) {
                    Icon(
                        imageVector = Icons.Rounded.ChevronRight,
                        contentDescription = stringResource(R.string.cd_next_button),
                        tint = if (pagerState.currentPage < startPage) MaterialTheme.colorScheme.onSurface
                        else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                    )
                }
            }

            CalendarGrid(
                pagerState = pagerState,
                startPage = startPage,
                currentDate = selectedDate,
                moodMap = moodMap,
                daysWithNotes = daysWithNotes,
                selectedDate = selectedDate,
                onDateClick = onDateClick,
                onMonthChanged = onMonthChanged
            )

            Spacer(Modifier.height(12.dp))
            DragHandle()
        }
    }
}

@Composable
fun CalendarGrid(
    pagerState: PagerState,
    startPage: Int,
    currentDate: LocalDate,
    moodMap: Map<LocalDate, Color>,
    daysWithNotes: Set<LocalDate>,
    selectedDate: LocalDate,
    onDateClick: (LocalDate) -> Unit,
    onMonthChanged: (year: Int, month: Month) -> Unit,
) {
    val today = remember { LocalDate.now() }
    val dayLabels = stringArrayResource(R.array.calendar_day_labels).toList()

    LaunchedEffect(pagerState.currentPage) {
        val offset = (pagerState.currentPage - startPage)
        val newDate =
            LocalDate.of(currentDate.year, currentDate.month, 1).plusMonths(offset.toLong())
        onMonthChanged(newDate.year, newDate.month)
    }

    LaunchedEffect(Unit) {
        delay(50)
        pagerState.animateScrollBy(value = -20f, animationSpec = tween(400))
        pagerState.animateScrollBy(value = 20f, animationSpec = tween(400))
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            dayLabels.forEach { label ->
                Text(
                    text = label,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
        Spacer(Modifier.height(6.dp))
        BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
            val cellsSize = maxWidth / 7
            val calendarHeight = cellsSize * 6

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(calendarHeight),
                verticalAlignment = Alignment.Top
            ) { page ->
                val offset = page - startPage
                val pageDate = LocalDate.of(today.year, today.month, 1).plusMonths(offset.toLong())

                CalendarMonthPage(
                    year = pageDate.year,
                    month = pageDate.month,
                    moodMap = moodMap,
                    daysWithNotes = daysWithNotes,
                    selectedDate = selectedDate,
                    onDateClick = onDateClick
                )
            }
        }
    }

}

@Composable
fun CalendarMonthPage(
    year: Int,
    month: Month,
    moodMap: Map<LocalDate, Color>,
    daysWithNotes: Set<LocalDate>,
    selectedDate: LocalDate,
    onDateClick: (LocalDate) -> Unit,
) {
    val firstDay = LocalDate.of(year, month, 1)
    val offset = firstDay.dayOfWeek.value - 1
    val daysInMonth = firstDay.lengthOfMonth()

    Column(modifier = Modifier.padding(horizontal = 8.dp)) {
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
                            val hasNote = daysWithNotes.contains(date)

                            val scale by animateFloatAsState(
                                targetValue = if (isSelected) 1.1f else 1f,
                                animationSpec = spring(
                                    dampingRatio = Spring.DampingRatioMediumBouncy,
                                    stiffness = Spring.StiffnessMedium
                                ),
                                label = "scale"
                            )

                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .size(50.dp)
                                    .scale(scale)
                                    .clip(CircleShape)
                                    .background(
                                        when {
                                            isFuture || hasNoMood -> MaterialTheme.colorScheme.onSurface.copy(
                                                alpha = 0.05f
                                            )

                                            else -> moodColor
                                        }
                                    )
                                    .clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null,
                                        onClick = { onDateClick(date) }
                                    )
                            ) {
                                /*Box(
                                    modifier = Modifier
                                        .size(46.dp)
                                        .clip(CircleShape)
                                        .background(
                                            when {
                                                isFuture || hasNoMood -> MaterialTheme.colorScheme.onSurface.copy(
                                                    alpha = 0.05f
                                                )
                                                else -> moodColor
                                            }
                                        )
                                        .then(
                                            if (!isSelected && (isFuture || hasNoMood)) Modifier.background(
                                                color = MaterialTheme.colorScheme.onSurface.copy(
                                                    alpha = 0.05f
                                                ),
                                                shape = CircleShape
                                            ) else Modifier
                                        ),
                                    contentAlignment = Alignment.Center*/

                                if (hasNote) {
                                    Icon(
                                        painter = painterResource(R.drawable.ic_edit),
                                        contentDescription = null,
                                        modifier = Modifier.size(24.dp),
                                        tint = MaterialTheme.colorScheme.onSurface
                                    )
                                }
//                                ) {
//                                    if (hasNote) {
//                                        Icon(
//                                            painter = painterResource(R.drawable.ic_edit),
//                                            contentDescription = null,
//                                            modifier = Modifier.size(24.dp),
//                                            tint = MaterialTheme.colorScheme.onSurface
//                                        )
//                                    }
//                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CalendarHeaderCard(displayMonth: Month, displayYear: Int) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        color = MaterialTheme.colorScheme.secondary
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 27.dp),
        ) {
            Text(
                text = displayMonth.getDisplayName(
                    TextStyle.FULL_STANDALONE, Locale.getDefault()
                ).replaceFirstChar { it.uppercase() },
                fontSize = 64.sp,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = displayYear.toString(),
                fontSize = 32.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f)
            )
        }

    }
}




