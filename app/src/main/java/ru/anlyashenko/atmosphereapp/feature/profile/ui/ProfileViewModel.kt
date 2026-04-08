package ru.anlyashenko.atmosphereapp.feature.profile.ui

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import ru.anlyashenko.atmosphereapp.core.mvi.BaseViewModel
import ru.anlyashenko.atmosphereapp.domain.repository.DiaryRepository
import ru.anlyashenko.atmosphereapp.feature.home.models.DiaryRecordUiModel
import ru.anlyashenko.atmosphereapp.feature.home.models.MoodUiModel
import ru.anlyashenko.atmosphereapp.feature.profile.models.MoodCountItem
import ru.anlyashenko.atmosphereapp.feature.profile.ui.ProfileEffect.NavigateToYearlyStats
import java.time.LocalDate
import javax.inject.Inject

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

                setState {
                    copy(
                        totalEntries = total,
                        currentStreak = currentStreak,
                        longestStreak = longestStreak,
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


    override fun handleEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.OnTimeRangeChanged -> setState { copy(selectedTimeRange = event.range) }
            ProfileEvent.OnSettingsClick -> setEffect { ProfileEffect.NavigateToSettings }
            ProfileEvent.OnYearlyStatsClick -> setEffect { ProfileEffect.NavigateToYearlyStats }
        }
    }
}