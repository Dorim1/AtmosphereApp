package ru.anlyashenko.atmosphereapp.feature.home.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Mood
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import ru.anlyashenko.atmosphereapp.R
import ru.anlyashenko.atmosphereapp.core.design_system.theme.AtmosphereAppTheme
import ru.anlyashenko.atmosphereapp.feature.home.models.WeatherUiModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
@Preview
private fun HomeScreenPreview() {
    AtmosphereAppTheme {
//        HomeScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: HomeViewModel = hiltViewModel()) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true) {
            viewModel.setEvent(HomeEvent.LoadWeather)
        }
    }

    LaunchedEffect(Unit) {
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
            )
        )
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 7.dp)
        ) {
            Spacer(Modifier.height(6.dp))
            WeatherCard(
                weather = state.weather,
                isLoading = state.isLoadingWeather
            )
            Spacer(Modifier.height(6.dp))

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                state.weekRecords.forEachIndexed { index, record ->
                    val isToday = index == 0

                    DayEntryCard(
                        record = record,
                        isToday = isToday
                    )

                    if (isToday) {
                        CurrentDayActionRow(
                            hasMood = record.hasMood,
                            onMoodClick = {
                                viewModel.setEvent(HomeEvent.OnMoodButtonClick)
                            },
                            onNoteClick = {
                                viewModel.setEvent(HomeEvent.OnNoteButtonClick)
                            }
                        )
                    }
                }
            }
            Spacer(Modifier.height(6.dp))
        }

        if (state.showMoodSheet) {
            MoodSelectionBottomSheet(
                onDismissRequest = { viewModel.setEvent(HomeEvent.DismissDialogs) },
                onMoodSelected = { selectedMood ->
                    viewModel.setEvent(HomeEvent.OnMoodSelected(selectedMood.id))
                }
            )
        }

        if (state.showNoteDialog) {
            AddNoteDialog(
                onDismiss = { viewModel.setEvent(HomeEvent.DismissDialogs) },
                onSave = { savedText ->
                    viewModel.setEvent(HomeEvent.OnSaveNote(savedText))
                }
            )
        }
    }

}



@Composable
fun WeatherCard(
    modifier: Modifier = Modifier,
    weather: WeatherUiModel?,
    isLoading: Boolean
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        color = MaterialTheme.colorScheme.primary
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier.padding(vertical = 60.dp),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.onPrimary)
            }
        } else if (weather != null) {
            WeatherSection(weather = weather)
        } else {
            Box(
                modifier = Modifier.padding(vertical = 60.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Нет данных о погоде",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}
@Composable
fun WeatherSection(weather: WeatherUiModel) {
    Box(
        modifier = Modifier.padding(horizontal = 28.dp, vertical = 39.dp),
    ) {
        Column() {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column() {
                    Text(
                        text = weather.temperature,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 64.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = weather.description,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
                Icon(
                    painter = painterResource(weather.iconResId),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(120.dp)
                )
            }
            Spacer(Modifier.height(44.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 13.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                weather.hourlyForecast.forEach { hourlyItem ->
                    WeatherHourlyItem(
                        time = hourlyItem.time,
                        icon = painterResource(hourlyItem.iconResId),
                        temp = hourlyItem.temperature
                    )
                }
            }
        }
    }
}


@Composable
fun WeatherHourlyItem(
    time: String,
    icon: Painter,
    temp: String,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.size(48.dp)
        )
        Text(
            text = time,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 16.sp,
            lineHeight = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        Text(
            text = temp,
            color = MaterialTheme.colorScheme.onPrimary,
            fontSize = 14.sp,
            lineHeight = 14.sp,
            fontWeight = FontWeight.SemiBold
        )

    }
}

@Composable
fun CurrentDayActionRow(
    hasMood: Boolean,
    onMoodClick:() -> Unit,
    onNoteClick:() -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(5.dp)
    ) {

        Surface(
            onClick = onMoodClick,
            shape = RoundedCornerShape(30.dp),
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_add_mood),
                    contentDescription = "Выбрать настроение",
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(102.dp)
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = if (hasMood) "Изменить\nнастроение" else "Выбрать\nнастроение",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )
            }
        }

        Surface(
            onClick = onNoteClick,
            shape = RoundedCornerShape(30.dp),
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .weight(1f)
                .aspectRatio(1f)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_edit),
                    contentDescription = "Выбрать настроение",
                    tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.size(102.dp)
                )
                Spacer(Modifier.height(6.dp))
                Text(
                    text = "Добавить\nзапись",
                    color = MaterialTheme.colorScheme.onSecondary,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )
            }
        }
    }
}

@Composable
fun DayEntryCard(
    record: DailyRecord,
    isToday: Boolean,
    modifier: Modifier = Modifier
) {
    val formatterDayOfWeek = DateTimeFormatter.ofPattern("EEEE", Locale("ru"))
    val formatterMonth = DateTimeFormatter.ofPattern("MMM", Locale("ru"))

    val dayOfWeek = record.date.format(formatterDayOfWeek).replaceFirstChar { it.uppercase() }
    val dayOfMonth = record.date.dayOfMonth.toString().padStart(2, '0')
    val month = record.date.format(formatterMonth).uppercase().removeSuffix(".")

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(30.dp),
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column() {
                Text(
                    text = dayOfWeek,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = dayOfMonth,
                    fontSize = 82.sp,
                    lineHeight = 82.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = month,
                    fontSize = 82.sp,
                    lineHeight = 82.sp,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface

                )
            }

            Column(horizontalAlignment = Alignment.End) {
                if (isToday && !record.hasMood) {
                    Text(
                        text = "Как прошёл ваш день?",
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.Start

                    )
                } else {
                    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                        if (record.hasNote) {
                            Icon(
                                imageVector = Icons.Rounded.Edit,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(32.dp)
                            )
                        }
                        if (record.hasMood) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .background(
                                        color = Color(0xFF8BB13B), // TODO: Передавать цвет из модели
                                        shape = CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Rounded.Mood,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.size(24.dp)
                                )
                            }

                        }
                    }
                }
            }
        }
    }
}

// todo: Удалить
data class DailyRecord(
    val date: LocalDate,
    val hasMood: Boolean = false,
    val hasNote: Boolean = false,
)

fun getDaysFromMondayToToday(): List<DailyRecord> {
    val today = LocalDate.now()
    val monday = today.with(DayOfWeek.MONDAY)

    val daysList = mutableListOf<DailyRecord>()
    var currentDate = today

    while (!currentDate.isBefore(monday)) {
        daysList.add(
            DailyRecord(
                date = currentDate,
                hasMood = currentDate != today,
                hasNote = currentDate.dayOfMonth % 2 == 0
            )
        )
        currentDate = currentDate.minusDays(1)
    }
    return daysList
}

@SuppressLint("MissingPermission")
fun getLocation(context: Context, onLocation: (Double, Double) -> Unit) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    val locationRequest = CurrentLocationRequest.Builder()
        .setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY)
        .setMaxUpdateAgeMillis(60_000L)
        .build()

    fusedLocationClient.getCurrentLocation(locationRequest, null)
        .addOnSuccessListener { location ->
            if (location != null) {
                onLocation(location.latitude, location.longitude)
            } else {
                Log.e("Weather", "Location is null")
            }
        }
        .addOnFailureListener { e ->
            Log.e("Weather", "Location error: ${e.message}")
        }
}


