package ru.anlyashenko.atmosphereapp.feature.profile.ui

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.anlyashenko.atmosphereapp.core.designsystem.theme.AtmosphereAppTheme
import ru.anlyashenko.atmosphereapp.core.designsystem.theme.MainTitleColorLight

data class DailyMoodStat(
    val dayName: String,
    val level: Int,
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
        )
    }
}

@Composable
fun ProfileScreen(
    totalEntries: Int,
    currentStreak: Int,
    longestStreak: Int,
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
                fontWeight = FontWeight.SemiBold,
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
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                repeat(5) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(MainTitleColorLight.copy(alpha = 0.1f))
                    )
                }
                Spacer(Modifier.height(20.dp))
            }
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
                                    .fillMaxHeight(animatedHeight)
                                    .width(20.dp)
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

