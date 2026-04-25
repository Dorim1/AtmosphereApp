package ru.anlyashenko.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.anlyashenko.core.model.DiaryEntry
import ru.anlyashenko.core.model.Mood
import java.time.LocalDate

@Entity(tableName = "diary_entries")
data class DiaryEntryDBO(
    @PrimaryKey
    @ColumnInfo("date") val date: LocalDate,
    @ColumnInfo("moodId") val moodId: Int? = null,
    @ColumnInfo("note") val note: String? = null,
)

fun DiaryEntryDBO.asExternalModel(): DiaryEntry = DiaryEntry(
    date = date,
    moodId = moodId,
    note = note,
)