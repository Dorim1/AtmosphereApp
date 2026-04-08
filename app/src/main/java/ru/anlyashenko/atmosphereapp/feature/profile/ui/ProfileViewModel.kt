package ru.anlyashenko.atmosphereapp.feature.profile.ui

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.anlyashenko.atmosphereapp.core.mvi.BaseViewModel
import ru.anlyashenko.atmosphereapp.domain.repository.DiaryRepository
import ru.anlyashenko.atmosphereapp.feature.home.models.DiaryRecordUiModel
import ru.anlyashenko.atmosphereapp.feature.home.models.MoodUiModel
import ru.anlyashenko.atmosphereapp.feature.profile.models.DailyMoodStat
import ru.anlyashenko.atmosphereapp.feature.profile.models.MoodCountItem
import ru.anlyashenko.atmosphereapp.feature.profile.ui.ProfileEffect.NavigateToYearlyStats
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository
) : BaseViewModel<ProfileEvent, ProfileState, ProfileEffect>() {

    init {
        observeStatistics()
    }

    override fun createInitialState(): ProfileState = ProfileState()

    private fun observeStatistics() {
        viewModelScope.launch {
            combine(
                diaryRepository.getAllRecordsFlow(),
                diaryRepository.availableMoods
            ) { records, availableMoods ->
                Pair(records, availableMoods)
            }.collect { (records, availableMoods) ->
                val total = records.size

                val sortedDates = records.map { it.date }.sortedDescending()
                val (currentStreak, longestStreak) = calculateStreaks(sortedDates)

                val moodCounts = calculateMoodCounts(records, availableMoods)

                val currentYear = LocalDate.now().year
                val entriesThisYear = records.count { it.date.year == currentYear }
                val daysInYear = if (LocalDate.now().isLeapYear) 366 else 365
                val yearlyProgress = ((entriesThisYear.toFloat() / daysInYear) * 100).toInt()

                val (chartData, chartInsight) = calculateChartData(records, availableMoods)

                setState {
                    copy(
                        totalEntries = total,
                        currentStreak = currentStreak,
                        longestStreak = longestStreak,
                        chartData = chartData,
                        moodCounts = moodCounts,
                        yearlyProgress = yearlyProgress,
                        chartInsight = "В ПН у вас чаще всего Отлично" // TODO: Написать логику для подведения статистики
                    )
                }
            }
        }
    }

    private fun calculateMoodCounts(
        records: List<DiaryRecordUiModel>,
        availableMoods: List<MoodUiModel>
    ): List<MoodCountItem> {
        val grouped = records.mapNotNull { it.mood }.groupingBy { it.id }.eachCount()

        return availableMoods
            .sortedBy { it.level }
            .map { mood ->
            MoodCountItem(
                name = mood.label,
                count = grouped[mood.id] ?: 0,
                color = mood.color,
            )
        }
    }

    private fun calculateStreaks(datesDescending: List<LocalDate>): Pair<Int, Int> {
        if (datesDescending.isEmpty()) return Pair(0, 0)

        var currentStreak = 0
        var longestStreak = 0
        var tempStreak = 1

        val today = LocalDate.now()
        val yesterday = today.minusDays(1)

        var isActiveStreak = datesDescending.first() == today || datesDescending.first() == yesterday
        if (isActiveStreak) currentStreak = 1

        for (i in 0 until datesDescending.size - 1) {
            val current = datesDescending[i]
            val next = datesDescending[i + 1]

            if (current.minusDays(1) == next) {
                tempStreak++
                if (isActiveStreak) currentStreak = tempStreak
            } else if (current != next) {
                if (tempStreak > longestStreak) longestStreak = tempStreak
                tempStreak = 1
                isActiveStreak = false
            }
        }
        if (tempStreak > longestStreak) longestStreak = tempStreak

        return Pair(currentStreak, longestStreak)
    }

    private fun calculateChartData(
        records: List<DiaryRecordUiModel>,
        availableMoods: List<MoodUiModel>,
    ) : Pair<List<DailyMoodStat>, String> {
        val recordsWithMood = records.filter { it.hasMood }
        val dayNames = listOf("Пн", "Вт", "Ср", "Чт", "Пт", "Сб", "Вс")

        if (recordsWithMood.isEmpty() || availableMoods.isEmpty()) {
            val emptyData = dayNames.map { DailyMoodStat(it, 0f, Color.Transparent) }
            return Pair(emptyData, "Недостаточно данных для статистики")
        }

        val chartData = DayOfWeek.values().mapIndexed { index, dayOfWeek ->
            val daysRecords = recordsWithMood.filter { it.date.dayOfWeek == dayOfWeek }

            val avgLevel = if (daysRecords.isNotEmpty()) {
                daysRecords.map { it.mood!!.level }.average().toFloat()
            } else {
                0f
            }

            val roundedLevel = avgLevel.roundToInt()
            val color = availableMoods.find { it.level == roundedLevel }?.color ?: Color.Transparent

            DailyMoodStat(
                dayName = dayNames[index],
                level = avgLevel,
                color = color
            )
        }

        val bestDay = chartData.maxByOrNull { it.level }
        val insightText = if (bestDay != null && bestDay.level > 0f) {
            val moodLabel = availableMoods.find { it.level == bestDay.level.roundToInt() }?.label ?: "Настроение"
            "В ${bestDay.dayName} у вас чаще всего «\$moodLabel»"
        } else {
            "Недостаточно данных"
        }

        return Pair(chartData, insightText)
    }



    override fun handleEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.OnSettingsClick -> setEffect { ProfileEffect.NavigateToSettings }
            ProfileEvent.OnYearlyStatsClick -> setEffect { NavigateToYearlyStats }
        }
    }
}