package ru.anlyashenko.atmosphereapp.feature.home.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.anlyashenko.atmosphereapp.core.designsystem.theme.AtmosphereAppTheme
import ru.anlyashenko.atmosphereapp.core.designsystem.theme.MainTitleColorLight
import ru.anlyashenko.atmosphereapp.core.designsystem.theme.SubtitleBlackColorLight

enum class DayState { Last, Today, Next }

@Composable
@Preview
private fun DayListItemPreview() {
    AtmosphereAppTheme {
        DayListItem(
            day = 13,
            month = "Марта",
            state = DayState.Next,
            {}
        )
    }

}

@Composable
fun DayListItem(
    day: Int,
    month: String,
    state: DayState,
    onClick: () -> Unit
) {
    val containerColor = when (state) {
        DayState.Last -> MaterialTheme.colorScheme.primary
        DayState.Today -> SubtitleBlackColorLight
        DayState.Next -> MaterialTheme.colorScheme.secondary
    }
    val contentColor = when (state) {
        DayState.Last -> MaterialTheme.colorScheme.onPrimary
        DayState.Today -> Color.White
        DayState.Next -> MaterialTheme.colorScheme.onSecondary
    }
    val borderStroke = if (state == DayState.Next) {
        BorderStroke(2.dp, MainTitleColorLight)
    } else null
    val circleSize = when (state) {
        DayState.Next -> 12.dp
        else -> 44.dp
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top,
        modifier = Modifier
            .width(54.dp)
            .height(100.dp)
            .clip(RoundedCornerShape(50.dp))
            .then(
                if (borderStroke != null)
                    Modifier.border(borderStroke, RoundedCornerShape(100.dp))
                else Modifier
            )
            .background(containerColor)
            .padding(top = 5.dp)
            .clickable { onClick() }
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.size(44.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(circleSize)
                    .clip(CircleShape)
                    .background(contentColor)
            )
        }
        Spacer(Modifier.height(5.dp))

        Text(
            text = day.toString(),
            color = contentColor,
            fontSize = 20.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = month,
            color = contentColor,
            fontSize = 10.sp,
            lineHeight = 10.sp
        )

    }
}

