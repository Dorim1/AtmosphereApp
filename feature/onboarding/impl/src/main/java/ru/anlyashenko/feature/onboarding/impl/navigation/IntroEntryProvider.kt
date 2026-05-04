package ru.anlyashenko.feature.onboarding.impl.navigation

import androidx.navigation3.runtime.EntryProviderScope
import androidx.navigation3.runtime.NavKey
import ru.anlyashenko.core.navigation.Navigator
import ru.anlyashenko.feature.home.api.navigateToHome
import ru.anlyashenko.feature.onboarding.api.IntroNavKey
import ru.anlyashenko.feature.onboarding.impl.IntroScreen

fun EntryProviderScope<NavKey>.introEntry(navigator: Navigator) {
    entry<IntroNavKey> {
        IntroScreen(
            onGetInClick = navigator::navigateToHome
        )
    }
}