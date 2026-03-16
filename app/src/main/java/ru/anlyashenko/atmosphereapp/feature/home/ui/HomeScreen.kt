package ru.anlyashenko.atmosphereapp.feature.home.ui

import android.R.attr.maxHeight
import android.widget.Space
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.twotone.Settings
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import ru.anlyashenko.atmosphereapp.R
import ru.anlyashenko.atmosphereapp.core.designsystem.theme.AtmosphereAppTheme
import ru.anlyashenko.atmosphereapp.data.model.DayUI

@Composable
@Preview
private fun HomeScreenPreview() {
    AtmosphereAppTheme {
        HomeScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val peekHeight = maxHeight * 0.75f

        BottomSheetScaffold(
            sheetContent = {
                // Основной контент
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .padding(horizontal = 23.dp, vertical = 36.dp)
                ) {
                    Text(
                        text = "Моя Неделя",
                        style = MaterialTheme.typography.headlineLarge
                    )
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "с 9 марта, 2026 по 16 марта, 2026", // todo: Динамическая дата
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                WeekRow(days)
            },
            sheetPeekHeight = peekHeight,
            sheetShape = RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp),
            sheetContainerColor = MaterialTheme.colorScheme.background,
            sheetDragHandle = null,
            containerColor = MaterialTheme.colorScheme.primary
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 23.dp, vertical = 46.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "12.03.2026", // todo: Подтягивать в ресурсы текущую дату
                        color = Color.White.copy(alpha = 0.85f),
                        fontSize = 14.sp
                    )
                    Icon(
                        imageVector = Icons.Rounded.Settings, // todo: Поменять на Material Symbols
                        contentDescription = null,
                        tint = Color.White
                    )
                }

                Spacer(Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.home_afternoon_greeting), // todo: От времени выставлять нужное приветствие
                    color = Color.White,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(16.dp))
                Text(
                    text = "Добавить подпись",
                    color = Color.White.copy(alpha = 0.7f),
                    fontSize = 20.sp
                )
            }
        }
    }
}

// todo: Удалить
val days = listOf(
    DayUI(9, "марта", DayState.Last),
    DayUI(10, "марта", DayState.Last),
    DayUI(11, "марта", DayState.Today),
    DayUI(12, "марта", DayState.Next),
    DayUI(13, "марта", DayState.Next),
    DayUI(14, "марта", DayState.Next),
    DayUI(15, "марта", DayState.Next),
    DayUI(16, "марта", DayState.Next),

)

@Composable
fun WeekRow(days: List<DayUI>) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(26.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(days) { day ->
            DayItem(
                day = day.number,
                month = day.month,
                state = day.state,
                onClick = {  }
            )
        }
    }
}