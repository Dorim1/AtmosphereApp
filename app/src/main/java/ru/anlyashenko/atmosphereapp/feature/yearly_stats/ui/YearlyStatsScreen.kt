package ru.anlyashenko.atmosphereapp.feature.yearly_stats.ui

import android.widget.Space
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material.icons.rounded.Equalizer
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import ru.anlyashenko.atmosphereapp.feature.yearly_stats.mapping.getLightColorForBackground
import ru.anlyashenko.atmosphereapp.feature.yearly_stats.mapping.getMoodNameFromColor
import java.time.LocalDate
import java.time.Year.isLeap
import java.time.YearMonth
import java.time.temporal.ChronoUnit
import java.util.Collections.emptyList


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
        Spacer(Modifier.height(6.dp))
        YearHeaderCard(
            displayYear = displayYear,
            percentage = percentage
        )

        Spacer(Modifier.height(6.dp))
        YearlyStatsPagerCard(
            pagerState = pagerState,
            availableYears = availableYears,
            moodMap = moodMap
        )

        Spacer(Modifier.height(6.dp))
        TopMoodsCard(
            year = displayYear,
            moodMap = moodMap,
        )
        Spacer(Modifier.height(6.dp))
        YearlyStatsCards(
            year = displayYear,
            moodMap = moodMap,
        )
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
                fontWeight = FontWeight.Medium,
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

@Composable
fun TopMoodsCard(
    year: Int,
    moodMap: Map<LocalDate, Color>,
    modifier: Modifier = Modifier
) {
    val topMoods = remember(year, moodMap) {
        val yearlyMoods = moodMap.filterKeys { it.year == year }.values
        val totalMoods = yearlyMoods.size

        if (totalMoods == 0) return@remember emptyList()

        yearlyMoods
            .groupingBy { it }
            .eachCount()
            .entries
            .sortedByDescending { it.value }
            .take(3)
            .map { (color, count) ->
                val percentage = (count * 100) / totalMoods
                Pair(color, percentage)
            }
    }
    Column() {
        if (topMoods.isEmpty()) {
            Text(
                text = "Пока мало данных для этого года",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
            )
        } else {
            topMoods.forEachIndexed { index, (color, percentage) ->
                EmotionProgressBar(
                    color = color,
                    percentage = percentage,
                )
                if (index < topMoods.lastIndex) {
                    Spacer(Modifier.height(6.dp))
                }
            }
        }
    }
}

@Composable
fun EmotionProgressBar(
    color: Color,
    percentage: Int,
    modifier: Modifier = Modifier
) {
    val moodName = getMoodNameFromColor(color)
    val lightBackgroundColor = getLightColorForBackground(color)

    var targetPercentage by remember { mutableIntStateOf(0) }

    LaunchedEffect(percentage) {
        targetPercentage = percentage
    }

    LaunchedEffect(Unit) {
        targetPercentage = percentage
    }

    val animatedFraction by animateFloatAsState(
        targetValue = targetPercentage / 100f,
        animationSpec = tween(
            durationMillis = 800,
            easing = FastOutSlowInEasing
        ),
        label = "ProgressBarAnimation"
    )

    val animatedPercentage by animateIntAsState(
        targetValue = targetPercentage,
        animationSpec = tween(
            durationMillis = 800,
            easing = FastOutSlowInEasing
        ),
        label = "ProgressTextAnimation"
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(165.dp)
            .clip(RoundedCornerShape(30.dp))
            .background(lightBackgroundColor)
    ) {
        if (animatedFraction > 0f) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(fraction = animatedFraction)
                    .clip(RoundedCornerShape(16.dp))
                    .background(color)
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 20.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "$animatedPercentage%",
                fontSize = 96.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimary,
            )
            Text(
                text = moodName,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
fun YearlyStatsCards(
    modifier: Modifier = Modifier,
    year: Int,
    moodMap: Map<LocalDate, Color>,
    entriesCount: Int? = null,
) {
    val (marksCount, maxStreak) = remember(year, moodMap) {
        val datesInYear = moodMap.keys.filter { it.year == year }.sorted()

        var currentStreak = 0
        var localMaxStreak = 0
        var previousDate: LocalDate? = null

        for (date in datesInYear) {
            if (previousDate == null || ChronoUnit.DAYS.between(previousDate, date) != 1L) {
                currentStreak = 1
            } else {
                currentStreak++
            }
            if (currentStreak > localMaxStreak) {
                localMaxStreak = currentStreak
            }
            previousDate = date
        }

        Pair(datesInYear.size, localMaxStreak)
    }

    val finalEntriesCount = entriesCount ?: marksCount

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Surface(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            shape = RoundedCornerShape(30.dp),
            color = MaterialTheme.colorScheme.primary
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 21.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = marksCount.toString(),
                    fontSize = 86.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                Text(
                    text = "Отметок\nв этом\nгоду",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            SmallStatCard(
                title = "Серия год",
                value = maxStreak.toString(),
                backgroundColor = MaterialTheme.colorScheme.secondary
            )
            SmallStatCard(
                title = "Записей в году",
                value = finalEntriesCount.toString(),
                backgroundColor = MaterialTheme.colorScheme.surface
            )
        }
    }
}

@Composable
fun SmallStatCard(
    title: String,
    value: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 23.dp, vertical = 21.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = value,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Spacer(Modifier.width(2.dp))
                Icon(
                    imageVector = Icons.Rounded.Equalizer,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
