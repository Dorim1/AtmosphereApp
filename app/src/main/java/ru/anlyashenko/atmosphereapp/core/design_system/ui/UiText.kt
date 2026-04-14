package ru.anlyashenko.atmosphereapp.core.design_system.ui

import androidx.annotation.StringRes

sealed class UiText {
    data class DynamicString(val value: String) : UiText()

    class StringResource(
        @param:StringRes val resId: Int,
        vararg val args: Any
    ) : UiText()
}