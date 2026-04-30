package ru.anlyashenko.feature.profile

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.anlyashenko.core.designsystem.R
import ru.anlyashenko.core.designsystem.ext.toTwoDigits
import ru.anlyashenko.core.designsystem.util.UiText
import ru.anlyashenko.feature.profile.model.DailyMoodStat
import ru.anlyashenko.feature.profile.model.MoodCountItem
import java.time.format.TextStyle
import java.util.Locale
import kotlin.collections.forEach
import androidx.compose.ui.platform.LocalLocale

@Composable
fun ProfileRoute(
    viewModel: ProfileViewModel = hiltViewModel(),
    onNavigateToSettings: () -> Unit,
    onNavigateToYearlyStats: () -> Unit,
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                ProfileEffect.NavigateToSettings -> onNavigateToSettings()
                ProfileEffect.NavigateToYearlyStats -> onNavigateToYearlyStats()
            }
        }
    }

    ProfileScreen(
        totalEntries = state.totalEntries,
        currentStreak = state.currentStreak,
        longestStreak = state.longestStreak,
        moodCounts = state.moodCounts,
        hasEnoughMoodData = state.hasEnoughMoodData,
        yearlyPercentage = state.yearlyProgress,
        chartData = state.chartData,
        chartInsight = state.chartInsight,
        onYearlyStatsClick = { viewModel.setEvent(ProfileEvent.OnYearlyStatsClick) },
        onSettingsClick = { viewModel.setEvent(ProfileEvent.OnSettingsClick) },
        yAxisColors = state.yAxisColors
    )
}

@Composable
fun ProfileScreen(
    totalEntries: Int,
    currentStreak: Int,
    longestStreak: Int,
    onYearlyStatsClick: () -> Unit,
    onSettingsClick: () -> Unit,
    moodCounts: List<MoodCountItem>,
    hasEnoughMoodData: Boolean,
    yearlyPercentage: Int,
    chartData: List<DailyMoodStat>,
    chartInsight: UiText,
    yAxisColors: List<Color>
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .statusBarsPadding()
            .padding(horizontal = 7.dp)
    ) {
        Spacer(Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            TotalEntriesCard(
                totalEntries,
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
            )
            CurrentStreakCard(
                longestStreak,
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1f)
            )
        }

        Spacer(Modifier.height(6.dp))
        LongestStreakCard(
            currentStreak = currentStreak,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(6.dp))
        AverageMoodCard(
            chartData = chartData,
            insightText = chartInsight,
            yAxisColors = yAxisColors
        )

        Spacer(Modifier.height(6.dp))
        MoodCounterCard(
            items = moodCounts,
            totalCount = totalEntries,
            hasEnoughData = hasEnoughMoodData
        )

        Spacer(Modifier.height(6.dp))
        YearlyStatsCard(
            percentage = yearlyPercentage,
            onClick = onYearlyStatsClick,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(6.dp))
        SettingsCard(
            onClick = onSettingsClick,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(6.dp))

    }
}


@Composable
fun TotalEntriesCard(
    total: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        color = MaterialTheme.colorScheme.primary
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(R.drawable.ic_check),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.profile_total_entries_title),
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 20.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Text(
                text = total.toTwoDigits(),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 96.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 96.sp,
            )
        }
    }
}

@Composable
fun CurrentStreakCard(
    total: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.secondary)
            .padding(16.dp),
        color = MaterialTheme.colorScheme.secondary
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    painter = painterResource(R.drawable.ic_heat),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondary,
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = stringResource(R.string.profile_current_streak_title),
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontSize = 20.sp,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.Medium
                )
            }
            Text(
                text = total.toTwoDigits(),
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 96.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 96.sp
            )
        }
    }
}



@Composable
fun LongestStreakCard(
    currentStreak: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp, vertical = 32.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = currentStreak.toTwoDigits(),
                    fontSize = 96.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 96.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Box(
                modifier = Modifier.weight(1f),
                Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.profile_longest_streak_title),
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Medium,
                    lineHeight = 32.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun AverageMoodCard(
    modifier: Modifier = Modifier,
    chartData: List<DailyMoodStat>,
    insightText: UiText,
    yAxisColors: List<Color>,
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 27.dp)
        ) {
            Text(
                text = stringResource(R.string.profile_average_mood_title),
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(25.dp))
            MoodBarChart(
                data = chartData,
                yAxisColors = yAxisColors,
                modifier = Modifier.height(230.dp)
            )

            Spacer(Modifier.height(12.dp))
            Text(
                text = insightText.asString(),
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(37.dp))
        }
    }
}

@Composable
fun MoodBarChart(
    data: List<DailyMoodStat>,
    yAxisColors: List<Color>,
    modifier: Modifier = Modifier,
) {
    var startAnimation by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { startAnimation = true }

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            yAxisColors.forEach { color ->
                Box(
                    modifier = Modifier
                        .size(16.dp)
                        .clip(CircleShape)
                        .background(color)
                )
            }
            Spacer(Modifier.height(20.dp))
        }
        Spacer(Modifier.width(16.dp))

        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Bottom
            ) {
                data.forEach { stat ->
                    val targetHeight = stat.level / 5f
                    val dayName =
                        stat.dayOfWeek.getDisplayName(TextStyle.SHORT, LocalLocale.current.platformLocale)
                    val animatedHeight by animateFloatAsState(
                        targetValue = if (startAnimation) targetHeight else 0f,
                        animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing),
                        label = "barHeight"
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(bottom = 8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight(1f)
                                    .width(42.dp) // 37.dp
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(
                                        MaterialTheme.colorScheme.onSurface.copy(
                                            alpha = 0.05f
                                        )
                                    )
                                    .align(Alignment.BottomCenter)
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxHeight(animatedHeight)
                                    .width(42.dp) // 37.dp
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(stat.color)
                                    .align(Alignment.BottomCenter)
                            )
                        }
                        Text(
                            text = dayName,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MoodCounterCard(
    modifier: Modifier = Modifier,
    totalCount: Int,
    items: List<MoodCountItem>,
    hasEnoughData: Boolean
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 160.dp),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 27.dp)
        ) {

            Text(
                text = stringResource(R.string.profile_mood_counter_title),
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(Modifier.height(16.dp))

            if (!hasEnoughData) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = stringResource(R.string.not_enough_data),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        text = stringResource(R.string.not_enough_data_description),
                        fontSize = 14.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            } else {
                Text(
                    text = totalCount.toString(),
                    fontSize = 96.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                StackedMoodBar(items = items, modifier = Modifier.height(48.dp))
                Spacer(Modifier.height(38.dp))

                LegendGrid(
                    items = items,
                    totalCount = totalCount
                )
            }
        }
    }
}

@Composable
fun StackedMoodBar(items: List<MoodCountItem>, modifier: Modifier = Modifier) {
    val visibleItems = items.filter { it.count > 0 }

    val totalCount = visibleItems.sumOf { it.count }
    val minWeight = totalCount * 0.1f

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.secondary),
    ) {
        visibleItems.forEachIndexed { index, item ->
            val isFirst = index == 0
            val isLast = index == visibleItems.lastIndex

            val shape = RoundedCornerShape(
                topStart = if (isFirst) 14.dp else 5.dp,
                bottomStart = if (isFirst) 14.dp else 5.dp,
                topEnd = if (isLast) 14.dp else 5.dp,
                bottomEnd = if (isLast) 14.dp else 5.dp
            )

            val safeWeight = maxOf(item.count.toFloat(), minWeight)

            Box(
                modifier = Modifier
                    .weight(safeWeight)
                    .fillMaxHeight()
                    .padding(
                        top = 2.dp,
                        bottom = 2.dp,
                        start = if (isFirst) 2.dp else 1.dp,
                        end = if (isLast) 2.dp else 1.dp
                    )
                    .clip(shape)
                    .background(item.color)
            )

        }
    }
}

@Composable
fun LegendGrid(
    items: List<MoodCountItem>,
    totalCount: Int
) {
    val rows = items.chunked(2)

    Column(modifier = Modifier.fillMaxWidth()) {
        rows.forEach { rowItems ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                LegendItem(
                    item = rowItems[0],
                    totalCount = totalCount,
                    modifier = Modifier.weight(1f)
                )
                Spacer(Modifier.width(30.dp))
                if (rowItems.size > 1) {
                    LegendItem(
                        item = rowItems[1],
                        totalCount = totalCount,
                        modifier = Modifier.weight(1f)
                    )
                } else {
                    Spacer(Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun LegendItem(
    item: MoodCountItem,
    totalCount: Int,
    modifier: Modifier = Modifier
) {

    val itemPercentage = if (totalCount > 0) (item.count * 100) / totalCount else 0

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(4.dp)
                .height(36.dp)
                .clip(RoundedCornerShape(50.dp))
                .background(item.color)
        )
        Spacer(Modifier.width(8.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = item.name.asString(),
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = item.count.toString(),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }

        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .background(
                    MaterialTheme.colorScheme.onSurface.copy(
                        alpha = 0.05f
                    )
                )
                .padding(horizontal = 17.dp, vertical = 5.dp)
        ) {
            Text(
                modifier = Modifier.width(42.dp),
                textAlign = TextAlign.Center,
                text = "$itemPercentage%",
                fontSize = 15.sp,
                lineHeight = 15.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun YearlyStatsCard(
    percentage: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.secondary,
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 28.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProgressCircle(
                percentage = percentage,
                modifier = Modifier.size(96.dp)
            )
            Spacer(Modifier.width(44.dp))
            Text(
                text = stringResource(R.string.profile_yearly_stats_button),
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 32.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 32.sp
            )
        }
    }
}

@Composable
fun ProgressCircle(
    percentage: Int,
    modifier: Modifier = Modifier
) {
    var animationPlayed by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { animationPlayed = true }

    val animatedPercentage by animateFloatAsState(
        targetValue = if (animationPlayed) percentage.toFloat() else 0f,
        animationSpec = tween(durationMillis = 1000),
        label = "progress"
    )

    val activeColor = MaterialTheme.colorScheme.onSecondary
    val inactiveColor = activeColor.copy(alpha = 0.15f)

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp)
        ) {
            val strokeWidth = 12.dp.toPx()

            drawCircle(
                color = inactiveColor,
                style = Stroke(width = strokeWidth)
            )

            val sweepAngle = (animatedPercentage / 100f) * 360f
            drawArc(
                color = activeColor,
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(
                    width = strokeWidth,
                    cap = StrokeCap.Round
                )
            )
        }

        Text(
            text = "$percentage%",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Composable
fun SettingsCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.primary
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 28.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(96.dp),
                tint = MaterialTheme.colorScheme.onPrimary,
                painter = painterResource(R.drawable.ic_settings),
                contentDescription = null,
            )
            Spacer(Modifier.width(44.dp))
            Text(
                text = stringResource(R.string.profile_settings_button),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 32.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 32.sp
            )
        }
    }
}