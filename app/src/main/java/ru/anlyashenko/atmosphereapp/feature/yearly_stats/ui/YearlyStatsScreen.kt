package ru.anlyashenko.atmosphereapp.feature.yearly_stats.ui

import androidx.compose.foundation.background
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.anlyashenko.atmosphereapp.core.designsystem.elements.DragHandle
import ru.anlyashenko.atmosphereapp.core.designsystem.theme.AtmosphereAppTheme
import ru.anlyashenko.atmosphereapp.feature.calendar.ui.mockMoodMap
import java.time.LocalDate
import java.time.Year.isLeap
import java.time.YearMonth


@Composable
@Preview
fun YearlyStatsScreenPreview() {
    AtmosphereAppTheme() {
        YearlyStatsScreen(
            moodMap = mockMoodMap,
        )
    }
}

@Composable
fun YearlyStatsScreen(
    moodMap: Map<LocalDate, Color>,
    modifier: Modifier = Modifier
) {
    val currentYear = LocalDate.now().year
    val availableYears = remember(moodMap) {
        val years = moodMap.keys.map { it.year }.toSet().toMutableList()
        if (!years.contains(currentYear)) years.add(currentYear)
        years.sorted()
    }

    val pagerState = rememberPagerState(
        initialPage = availableYears.lastIndex,
        pageCount = { availableYears.size }
    )

    val displayYear = availableYears[pagerState.currentPage]

    val percentage = remember(displayYear, moodMap) {
        val isLeapYear = isLeap(displayYear.toLong())
        val totalDays = if (isLeapYear) 366 else 365
        val entriesThisYear = moodMap.keys.count { it.year == displayYear }
        if (totalDays > 0) (entriesThisYear * 100) / totalDays else 0
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 7.dp)
    ) {
        Spacer(Modifier.height(8.dp))
        YearHeaderCard(
            displayYear = displayYear,
            percentage = percentage
        )

        Spacer(Modifier.height(8.dp))
        YearlyStatsPagerCard(
            pagerState = pagerState,
            availableYears = availableYears,
            moodMap = moodMap
        )

        Spacer(Modifier.height(8.dp))

    }

}

@Composable
fun YearlyStatsPagerCard(
    pagerState: PagerState,
    availableYears: List<Int>,
    moodMap: Map<LocalDate, Color>
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(modifier = Modifier.padding(horizontal = 30.dp, vertical = 24.dp)) {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxWidth(),
            ) { page ->
                val year = availableYears[page]
                YearMatrixCard(year = year, moodMap = moodMap)
            }

            Spacer(Modifier.height(27.dp))
            DragHandle()
        }

    }

}

@Composable
fun YearMatrixCard(
    year: Int,
    moodMap: Map<LocalDate, Color>
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        for (month in 1..12) {
            val daysInMonth = YearMonth.of(year, month).lengthOfMonth()

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                for (day in 1..31) {
                    Box(
                        modifier = Modifier.size(15.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (day <= daysInMonth) {
                            val date = LocalDate.of(year, month, day)
                            val color = moodMap[date]
                            val today = LocalDate.now()
                            val isFuture = date.isAfter(today)

                            if (color != null) {
                                Box(
                                    modifier = Modifier
                                        .size(15.dp)
                                        .clip(CircleShape)
                                        .background(color)
                                )
                            } else if (!isFuture) {
                                Box(
                                    modifier = Modifier
                                        .size(5.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f))
                                )
                            } else if (year == today.year) {
                                Box(
                                    modifier = Modifier
                                        .size(5.dp)
                                        .clip(CircleShape)
                                        .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f))
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
fun YearHeaderCard(displayYear: Int, percentage: Int) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        color = MaterialTheme.colorScheme.secondary
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 27.dp),
        ) {
            Text(
                text = displayYear.toString(),
                fontSize = 64.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSecondary
            )
            Text(
                text = "$percentage%",
                fontSize = 32.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSecondary.copy(alpha = 0.3f)
            )
        }

    }
}