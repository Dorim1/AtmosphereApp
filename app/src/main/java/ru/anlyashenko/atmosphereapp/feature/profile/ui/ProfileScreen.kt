package ru.anlyashenko.atmosphereapp.feature.profile.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.anlyashenko.atmosphereapp.R
import ru.anlyashenko.atmosphereapp.core.designsystem.theme.AtmosphereAppTheme
import ru.anlyashenko.atmosphereapp.core.designsystem.theme.SecondaryGrayLight
import java.time.LocalDate

@Composable
@Preview
private fun ProfileScreenPreview() {
    AtmosphereAppTheme() {
        ProfileScreen()
    }
}

@Composable
fun ProfileScreen() {
    val moodEntries = remember {
        listOf(
            LocalDate.now().minusDays(6) to Color(0xFFE4F9D4),
            LocalDate.now().minusDays(5) to Color(0xFFE4F3D6),
            LocalDate.now().minusDays(4) to Color(0xFFFFEAC1),
            LocalDate.now().minusDays(3) to Color(0xFFD4D4FF),
            LocalDate.now().minusDays(2) to Color(0xFFFFD1DC),
            LocalDate.now().minusDays(1) to Color(0xFFE4F9D4),
            LocalDate.now() to Color(0xFFFFEAC1),
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 23.dp, vertical = 52.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            StatCard(
                modifier = Modifier.weight(1f),
                icon = painterResource(R.drawable.check_mark),
                title = "Всего\nОтметок",
                value = "30",
            )
            StatCard(
                modifier = Modifier.weight(1f),
                icon = painterResource(R.drawable.lighting_bolt),
                title = "Дней\nПодряд",
                value = "30"
            )
        }

        Spacer(Modifier.height(12.dp))

        Text(
            modifier = Modifier.padding(horizontal = 5.dp),
            text = "Самая длинная серия: 30",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(Modifier.height(44.dp))

        Text(
            text = "График настроения",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(Modifier.height(8.dp))
        MoodChart(entries = moodEntries)
    }

}

@Composable
fun MoodChart(entries: List<Pair<LocalDate, Color>>) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(2.dp, SecondaryGrayLight)
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(258.dp)
            ) {
                if (entries.isEmpty()) return@Canvas

                val dotRadius = 12.dp.toPx()
                val colWidth = size.width / entries.size
                val moodLevels = listOf(
                    Color(0xFFE4F9D4),
                    Color(0xFFE4F3D6),
                    Color(0xFFFFEAC1),
                    Color(0xFFD4D4FF),
                    Color(0xFFFFD1DC)
                )

                val points = entries.mapIndexed { index, (_, color) ->
                    val moodIndex = moodLevels.indexOf(color).takeIf { it >= 0 } ?: 2
                    val x = colWidth * index + colWidth / 2
                    val y = (moodIndex.toFloat() / (moodLevels.size - 1)) * size.height
                    Offset(x, y)
                }

                for (i in 0 until points.size - 1) {
                    drawLine(
                        color = Color.LightGray.copy(alpha = 0.5f),
                        start = points[i],
                        end = points[i + 1],
                        strokeWidth = 2.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                }

                points.forEachIndexed { index, offset ->
                    val color = entries[index].second
                    drawCircle(
                        color = color,
                        radius = dotRadius,
                        center = offset
                    )
                }
            }
            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                entries.forEach { (date, _) ->
                    Text(
                        text = date.dayOfMonth.toString(),
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        fontSize = 15.sp,
                        color = MaterialTheme.colorScheme.onBackground.copy(0.5f)
                    )
                }
            }
        }
    }
}

@Composable
fun StatCard(
    modifier: Modifier = Modifier,
    icon: Painter,
    title: String,
    value: String,
    iconTint: Color = MaterialTheme.colorScheme.primary
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 2.dp,
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = icon,
                contentDescription = null,
                tint = iconTint,
                modifier = Modifier.size(48.dp)
            )
            Column() {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}