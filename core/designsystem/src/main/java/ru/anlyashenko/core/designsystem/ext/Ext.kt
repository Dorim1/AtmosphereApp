package ru.anlyashenko.core.designsystem.ext

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ru.anlyashenko.core.model.CornerRadiusMode

val CornerRadiusMode.dpValue: Dp
    get() = when (this) {
        CornerRadiusMode.SMALL -> 12.dp
        CornerRadiusMode.MODERATE -> 20.dp
        CornerRadiusMode.BIG -> 30.dp
    }

fun Int.toTwoDigits(): String = "%02d".format(this)
fun Int.toThreeDigits(): String = "%03d".format(this)