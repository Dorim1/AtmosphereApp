package ru.anlyashenko.core.designsystem.ext

import androidx.compose.material3.DatePickerColors
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.anlyashenko.core.model.CornerRadiusMode

// todo: если тебе нужно применить этот радиус глобально в теме приложения (Shapes.kt), ты просто берешь текущее значение из стейта/настроек и делаешь RoundedCornerShape(state.cornerRadius.dpValue).
val CornerRadiusMode.dpValue: Dp
    get() = when (this) {
        CornerRadiusMode.SMALL -> 12.dp
        CornerRadiusMode.MODERATE -> 20.dp
        CornerRadiusMode.BIG -> 30.dp
    }

fun Int.toTwoDigits(): String = "%02d".format(this)
fun Int.toThreeDigits(): String = "%03d".format(this)