package ru.anlyashenko.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.anlyashenko.core.model.Mood

@Entity(tableName = "moods")
data class MoodDBO(
    @PrimaryKey val id: Int,
    @ColumnInfo("level") val level: Int,
    @ColumnInfo("colorHex") val colorHex: String,
    @ColumnInfo("iconKey") val iconKey: String,
    @ColumnInfo("customName") val customName: String? = null
)

fun MoodDBO.asExternalModel() = Mood(
    id = id,
    level = level,
    colorHex = colorHex,
    iconKey = iconKey,
    customName = customName
)