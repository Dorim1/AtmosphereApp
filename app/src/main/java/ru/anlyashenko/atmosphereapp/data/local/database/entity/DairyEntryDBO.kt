package ru.anlyashenko.atmosphereapp.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "diary_entries")
data class DairyEntryDBO(
    @PrimaryKey
    @ColumnInfo("date") val date: LocalDate,
    @ColumnInfo("moodId") val moodId: Int? = null,
    @ColumnInfo("note") val note: String? = null,
)
