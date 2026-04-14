package ru.anlyashenko.atmosphereapp.feature.profile.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.anlyashenko.atmosphereapp.core.design_system.ui.UiText
import ru.anlyashenko.atmosphereapp.core.design_system.ui.UiText.DynamicString
import ru.anlyashenko.atmosphereapp.core.design_system.ui.UiText.StringResource

@Composable
fun UiText.asString(): String {
    return when (this) {
        is DynamicString -> value
        is StringResource -> {
            val resolvedArgs = args.map { arg ->
                if (arg is UiText) arg.asString() else arg
            }.toTypedArray()
            stringResource(resId, *resolvedArgs)
        }
    }
}