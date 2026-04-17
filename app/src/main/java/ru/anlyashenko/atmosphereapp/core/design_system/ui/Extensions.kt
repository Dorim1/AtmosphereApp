package ru.anlyashenko.atmosphereapp.core.design_system.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.anlyashenko.atmosphereapp.feature.home.models.MoodUiModel


fun Int.toTwoDigits(): String = "%02d".format(this)

// todo: думаю в UiText можно
@Composable
fun MoodUiModel.getDisplayName(): String {
    return this.customLabel ?: stringResource(id = this.defaultLabelRes)
}