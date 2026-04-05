package ru.anlyashenko.atmosphereapp.data.local.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "moods")
data class MoodDBO(
    @PrimaryKey val id: Int,
    @ColumnInfo("label") val label: String,
    @ColumnInfo("level") val level: Int,
)