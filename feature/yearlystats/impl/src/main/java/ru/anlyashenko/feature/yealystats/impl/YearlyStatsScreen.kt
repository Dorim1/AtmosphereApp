package ru.anlyashenko.feature.yealystats.impl

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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.anlyashenko.core.designsystem.R
import ru.anlyashenko.core.designsystem.component.DragHandle
import ru.anlyashenko.core.designsystem.ext.toTwoDigits
import ru.anlyashenko.core.designsystem.theme.OnMoodColor
import ru.anlyashenko.feature.yealystats.impl.model.YearlyMoodUiModel
import ru.anlyashenko.feature.yealystats.impl.model.YearlyRecordUiModel
import java.time.LocalDate
import java.time.Year.isLeap
import java.time.YearMonth
import java.time.temporal.ChronoUnit
import java.util.Collections
import kotlin.collections.component1
import kotlin.collections.component2

@Composable
fun YearlyStatsScreen(
    viewModel: YearlyStatsViewModel = hiltViewModel(),
    onNavigateBack: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is YearlyStatsEffect.NavigateBack -> onNavigateBack()
            }
        }
    }

    YearlyStatsScreen(
        records = state.records,
        hasEnoughMoodData = state.hasEnoughMoodData
    )
}
@Composable
internal fun YearlyStatsScreen(
    modifier: Modifier = Modifier,
    records: List<YearlyRecordUiModel>,
    hasEnoughMoodData: Boolean,
) {
    val currentYear = LocalDate.now().year

    val moodMap = remember(records) {
        records.filter { it.hasMood }.associate { it.date to it.mood!! }
    }

    val availableYears = remember(moodMap) {
        val years = moodMap.keys.map { it.year }.toMutableSet()
        years.add(currentYear)
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
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .statusBarsPadding()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 7.dp)
    ) {
        Spacer(Modifier.height(6.dp))
        YearHeaderCard(displayYear = displayYear, percentage = percentage)
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
            hasEnoughData = hasEnoughMoodData
        )
        Spacer(Modifier.height(6.dp))
        YearlyStatsCards(
            year = displayYear,
            moodMap = moodMap,
            notesCount = records.count { it.date.year == displayYear && it.hasNote }
        )
        Spacer(Modifier.height(6.dp))
    }
}


@Composable
fun YearlyStatsPagerCard(
    pagerState: PagerState,
    availableYears: List<Int>,
    moodMap: Map<LocalDate, YearlyMoodUiModel>
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
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
    moodMap: Map<LocalDate, YearlyMoodUiModel>
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
                            val mood = moodMap[date]
                            val today = LocalDate.now()
                            val isFuture = date.isAfter(today)

                            val circleSize = if (mood != null) 15.dp else 5.dp
                            val circleColor = when {
                                mood != null -> mood.color
                                isFuture -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.15f)
                                else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                            }

                            Box(
                                modifier = Modifier
                                    .size(circleSize)
                                    .clip(CircleShape)
                                    .background(circleColor)
                            )
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
        shape = MaterialTheme.shapes.large,
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
    moodMap: Map<LocalDate, YearlyMoodUiModel>,
    hasEnoughData: Boolean,
    modifier: Modifier = Modifier
) {
    val topMoods = remember(year, moodMap) {
        val yearlyMoods = moodMap.filterKeys { it.year == year }.values
        val totalMoods = yearlyMoods.size

        if (totalMoods == 0) return@remember Collections.emptyList()

        yearlyMoods
            .groupingBy { it }
            .eachCount()
            .entries
            .sortedWith(
                compareByDescending<Map.Entry<YearlyMoodUiModel, Int>> { it.value }
                    .thenByDescending { it.key.level }
            )
            .take(3)
            .map { (color, count) ->
                val percentage = (count * 100) / totalMoods
                Pair(color, percentage)
            }
    }
    Column(modifier = modifier) {
        if (!hasEnoughData || topMoods.isEmpty()) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 160.dp),
                shape = MaterialTheme.shapes.large,
                color = MaterialTheme.colorScheme.surface
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = stringResource(R.string.yearly_not_enough_data),
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        textAlign = TextAlign.Center
                    )
                }

            }
        } else {
            topMoods.forEachIndexed { index, (mood, percentage) ->
                EmotionProgressBar(
                    mood = mood,
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
    mood: YearlyMoodUiModel,
    percentage: Int,
    modifier: Modifier = Modifier
) {
    val lightBackgroundColor = mood.color.copy(alpha = 0.55f)

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
            .clip(MaterialTheme.shapes.large)
            .background(lightBackgroundColor)
    ) {
        if (animatedFraction > 0f) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(fraction = animatedFraction)
                    .clip(RoundedCornerShape(16.dp))
                    .background(mood.color)
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
                color = OnMoodColor,
            )
            Text(
                text = mood.displayName.asString(),
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = OnMoodColor
            )
        }
    }
}

@Composable
fun YearlyStatsCards(
    modifier: Modifier = Modifier,
    year: Int,
    moodMap: Map<LocalDate, YearlyMoodUiModel>,
    notesCount: Int,
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
            shape = MaterialTheme.shapes.large,
            color = MaterialTheme.colorScheme.primary
        ) {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 21.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = marksCount.toTwoDigits(),
                    fontSize = 86.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                Text(
                    text = stringResource(R.string.yearly_mood_entries_title),
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
                title = stringResource(R.string.yearly_streak_title),
                value = maxStreak.toTwoDigits(),
                backgroundColor = MaterialTheme.colorScheme.secondary
            )
            SmallStatCard(
                title = stringResource(R.string.yearly_notes_title),
                value = notesCount.toTwoDigits(),
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
        shape = MaterialTheme.shapes.large,
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
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                )
                Spacer(Modifier.width(2.dp))
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