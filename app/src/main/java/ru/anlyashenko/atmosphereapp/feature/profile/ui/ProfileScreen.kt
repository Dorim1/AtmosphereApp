package ru.anlyashenko.atmosphereapp.feature.profile.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.LocalFireDepartment
import androidx.compose.material.icons.rounded.Settings
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.anlyashenko.atmosphereapp.core.design_system.theme.AtmosphereAppTheme

data class DailyMoodStat(
    val dayName: String,
    val level: Int,
    val color: Color
)

data class MoodCountItem(
    val name: String,
    val count: Int,
    val color: Color
)

val MoodLevel5Color = Color(0xFF0A6C60) // Темно-зеленый (Отлично)
val MoodLevel4Color = Color(0xFF8BB13B) // Салатовый (Хорошо)
val MoodLevel3Color = Color(0xFFFFC107) // Желтый (Нормально)
val MoodLevel2Color = Color(0xFFFF5722) // Оранжевый (Плохо)
val MoodLevel1Color = Color(0xFFD32F2F) // Красный (Ужасно)

val weekData = listOf(
    DailyMoodStat("Пн", 5, MoodLevel5Color),
    DailyMoodStat("Вт", 2, MoodLevel2Color),
    DailyMoodStat("Ср", 4, MoodLevel4Color),
    DailyMoodStat("Чт", 3, MoodLevel3Color),
    DailyMoodStat("Пт", 5, MoodLevel5Color),
    DailyMoodStat("Сб", 3, MoodLevel3Color),
    DailyMoodStat("Вс", 1, MoodLevel1Color),
)

@Composable
@Preview
private fun ProfileScreenPreview() {
    AtmosphereAppTheme() {
        ProfileScreen(
            totalEntries = 64,
            currentStreak = 27,
            longestStreak = 36,
            onYearlyStatsClick = {}
        )
    }
}

@Composable
fun ProfileScreen(
    totalEntries: Int,
    currentStreak: Int,
    longestStreak: Int,
    onYearlyStatsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 7.dp)
    ) {
        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(9.dp)
        ) {
            TotalMarkCard(
                totalEntries,
                modifier = Modifier.weight(1f)
            )
            CurrentStreakCard(
                longestStreak,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(8.dp))
        LongestStreakCard(
            currentStreak = currentStreak,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))
        AverageMoodCard()

        Spacer(Modifier.height(8.dp))
        MoodCounterCard()

        Spacer(Modifier.height(8.dp))
        YearlyStatsCard(
            percentage = 67,
            onClick = onYearlyStatsClick,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))
        SettingsCard(
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        )

    }
}



@Composable
fun TotalMarkCard(
    total: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .clip(RoundedCornerShape(30.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp),
        color = MaterialTheme.colorScheme.primary
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    imageVector = Icons.Rounded.Check,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Всего\nОтметок",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 16.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Text(
                text = total.toString(),
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 96.sp,
                fontWeight = FontWeight.SemiBold,
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
            .clip(RoundedCornerShape(30.dp))
            .background(MaterialTheme.colorScheme.secondary)
            .padding(16.dp),
        color = MaterialTheme.colorScheme.secondary
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier.size(48.dp),
                    imageVector = Icons.Rounded.LocalFireDepartment,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondary,
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = "Дней\nподряд",
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontSize = 16.sp,
                    lineHeight = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
            Text(
                text = total.toString(),
                color = MaterialTheme.colorScheme.onSecondary,
                fontSize = 96.sp,
                fontWeight = FontWeight.SemiBold,
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
            .clip(RoundedCornerShape(30.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 16.dp, vertical = 32.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = currentStreak.toString(),
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 96.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 96.sp
            )
            Spacer(Modifier.width(44.dp))
            Text(
                text = "Самая длинная\nсерия",
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 24.sp
            )
        }
    }
}

@Composable
fun AverageMoodCard(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 27.dp)
        ) {
            Text(
                text = "Среднее настроение",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(Modifier.height(25.dp))
            MoodBarChart(data = weekData, modifier = Modifier.height(230.dp))

            Spacer(Modifier.height(12.dp))
            Text(
                text = "В Пн у вас чаще всего Отлично",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(37.dp))
            TimeToggleSwitch()
        }
    }
}

@Composable
fun MoodBarChart(data: List<DailyMoodStat>, modifier: Modifier = Modifier) {
    var startAnimation by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { startAnimation = true }

    val moodColors = listOf(
        MoodLevel5Color,
        MoodLevel4Color,
        MoodLevel3Color,
        MoodLevel2Color,
        MoodLevel1Color,
    )
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {
        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            moodColors.forEach { color ->
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
                                    .background(MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f))
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
                            text = stat.dayName,
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
fun TimeToggleSwitch() {
    val option = listOf("День", "Неделя", "Месяц")
    var selectedOption by remember { mutableStateOf(option[0]) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        option.forEach { option ->
            val isSelected = selectedOption == option
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(50.dp))
                    .background(if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent)
                    .clickable { selectedOption = option }
                    .padding(horizontal = 25.dp, vertical = 8.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = option,
                    fontSize = 14.sp,
                    lineHeight = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface
                )
            }
        }
    }
}

@Composable
fun MoodCounterCard(modifier: Modifier = Modifier) {
    val items = listOf(
        MoodCountItem("Отлично", 17, Color(0xFF8BB13B)), // Салатовый
        MoodCountItem("Хорошо", 8, Color(0xFF0A6C60)),   // Темно-зеленый
        MoodCountItem("Нормально", 5, Color(0xFFFFC107)),// Желтый
        MoodCountItem("Плохо", 12, Color(0xFFFF5722)),   // Оранжевый
        MoodCountItem("Ужасно", 1, Color(0xFFD32F2F))    // Красный
    )

    val totalCount = items.sumOf { it.count }

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Счётчик настроения",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = totalCount.toString(),
                fontSize = 96.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            StackedMoodBar(items = items, modifier = Modifier.height(48.dp))
            Spacer(Modifier.height(38.dp))
            LegendGrid(items = items, totalCount = totalCount)
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
    val percentage = if (totalCount > 0) (item.count * 100) / totalCount else 0

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
                text = item.name,
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
                .background(MaterialTheme.colorScheme.secondary)
                .padding(horizontal = 17.dp, vertical = 5.dp)
        ) {
            Text(
                modifier = Modifier.width(32.dp),
                textAlign = TextAlign.Center,
                text = "$percentage%",
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
    onClick:() -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        onClick = onClick,
        modifier = modifier
//            .clip(RoundedCornerShape(30.dp))
//            .background(MaterialTheme.colorScheme.secondary)
//            .padding(horizontal = 16.dp, vertical = 28.dp),
            .fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
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
                text = "Статистика\nза год",
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
        Canvas(modifier = Modifier
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
//            .clip(RoundedCornerShape(30.dp))
//            .background(MaterialTheme.colorScheme.primary)
//            .clickable { onClick() }
//            .padding(horizontal = 16.dp, vertical = 28.dp),
        shape = RoundedCornerShape(30.dp),
        color = MaterialTheme.colorScheme.primary
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 28.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(96.dp),
                tint = MaterialTheme.colorScheme.onPrimary,
                imageVector = Icons.Rounded.Settings,
                contentDescription = null,
            )
            Spacer(Modifier.width(44.dp))
            Text(
                text = "Настройки",
                color = MaterialTheme.colorScheme.onPrimary,
                fontSize = 32.sp,
                fontWeight = FontWeight.Medium,
                lineHeight = 32.sp
            )
        }
    }
}

