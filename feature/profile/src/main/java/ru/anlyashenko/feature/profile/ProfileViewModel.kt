package ru.anlyashenko.feature.profile

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import ru.anlyashenko.core.data.repository.DiaryRepository
import ru.anlyashenko.core.data.repository.SettingsRepository
import ru.anlyashenko.core.designsystem.R
import ru.anlyashenko.core.designsystem.theme.MoodPalettes
import ru.anlyashenko.core.designsystem.util.UiText
import ru.anlyashenko.core.presentation.mvi.BaseViewModel
import ru.anlyashenko.feature.profile.model.DailyMoodStat
import ru.anlyashenko.feature.profile.model.MoodCountItem
import ru.anlyashenko.feature.profile.model.ProfileMoodUiModel
import ru.anlyashenko.feature.profile.model.ProfileRecordUiModel
import ru.anlyashenko.feature.profile.model.toUiModel
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository,
    private val settingsRepository: SettingsRepository
) : BaseViewModel<ProfileEvent, ProfileState, ProfileEffect>() {

    init {
        observeStatistics()
    }

    override fun createInitialState(): ProfileState = ProfileState()

    private fun observeStatistics() {
        viewModelScope.launch {
            combine(
                diaryRepository.getAllRecordsFlow(),
                diaryRepository.availableMoods,
                settingsRepository.selectedPaletteFlow
            ) { domainRecords, domainMoods, paletteId ->

                val activePalette = MoodPalettes.allPalettes.find { it.id == paletteId }
                    ?: MoodPalettes.allPalettes.first()

                val uiMoods = domainMoods.map { it.toUiModel(activePalette) }
                val uiRecords = domainRecords.map { it.toUiModel(activePalette) }

                Pair(uiRecords, uiMoods)

            }.collect { (records, availableMoods) ->
                val total = records.count { it.hasMood }

                val sortedDates = records.map { it.date }.sortedDescending()
                val (currentStreak, longestStreak) = calculateStreaks(sortedDates)

                val moodCounts = calculateMoodCounts(records, availableMoods)
                val hasEnoughMoodData = moodCounts.sumOf { it.count } >= 3

                val currentYear = LocalDate.now().year
                val entriesThisYear = records.count { it.date.year == currentYear }
                val daysInYear = if (LocalDate.now().isLeapYear) 366 else 365
                val yearlyProgress = ((entriesThisYear.toFloat() / daysInYear) * 100).toInt()

                val (chartData, chartInsight) = calculateChartData(records, availableMoods)

                val yAxisColors = availableMoods
                    .sortedByDescending { it.level }
                    .map { it.color }

                setState {
                    copy(
                        totalEntries = total,
                        currentStreak = currentStreak,
                        longestStreak = longestStreak,
                        chartData = chartData,
                        moodCounts = moodCounts,
                        yAxisColors = yAxisColors,
                        yearlyProgress = yearlyProgress,
                        chartInsight = chartInsight,
                        hasEnoughMoodData = hasEnoughMoodData
                    )
                }
            }
        }
    }

    private fun calculateMoodCounts(
        records: List<ProfileRecordUiModel>,
        availableMoods: List<ProfileMoodUiModel>
    ): List<MoodCountItem> {
        val grouped = records.mapNotNull { it.mood }.groupingBy { it.id }.eachCount()

        return availableMoods
            .sortedByDescending { it.level }
            .map { mood ->
                MoodCountItem(
                    name = mood.displayName,
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
        records: List<ProfileRecordUiModel>,
        availableMoods: List<ProfileMoodUiModel>,
    ) : Pair<List<DailyMoodStat>, UiText> {
        val recordsWithMood = records.filter { it.hasMood }

        if (recordsWithMood.isEmpty() || availableMoods.isEmpty()) {
            val emptyData = DayOfWeek.entries.map { DailyMoodStat(it, 0f, Color.Transparent) }
            return Pair(emptyData, UiText.StringResource(R.string.profile_not_enough_data_stats))
        }

        val chartData = DayOfWeek.entries.map { dayOfWeek ->
            val daysRecords = recordsWithMood.filter { it.date.dayOfWeek == dayOfWeek }

            val avgLevel = if (daysRecords.isNotEmpty()) {
                daysRecords.map { it.mood!!.level }.average().toFloat()
            } else {
                0f
            }

            val roundedLevel = avgLevel.roundToInt()
            val color = availableMoods.find { it.level == roundedLevel }?.color ?: Color.Transparent

            DailyMoodStat(
                dayOfWeek = dayOfWeek,
                level = avgLevel,
                color = color
            )
        }

        val bestDay = chartData.maxByOrNull { it.level }
        val insightText = if (bestDay != null && bestDay.level > 0f) {
            val originalLevel = bestDay.level.roundToInt()
            val mood = availableMoods.find { it.level == originalLevel }

            val localizedDayName = bestDay.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())

            if (mood != null) {
                UiText.StringResource(
                    R.string.profile_insight_pattern,
                    localizedDayName,
                    mood.displayName
                )
            } else {
                UiText.StringResource(
                    R.string.profile_insight_pattern,
                    localizedDayName,
                    UiText.StringResource(R.string.profile_fallback_mood)
                )
            }
        } else {
            UiText.StringResource(R.string.profile_not_enough_data)
        }

        return Pair(chartData, insightText)
    }

    override fun handleEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.OnSettingsClick -> setEffect { ProfileEffect.NavigateToSettings }
            ProfileEvent.OnYearlyStatsClick -> setEffect { ProfileEffect.NavigateToYearlyStats }
        }
    }
}