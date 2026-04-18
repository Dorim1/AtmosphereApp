package ru.anlyashenko.atmosphereapp.core.utils

import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import ru.anlyashenko.atmosphereapp.feature.settings.ui.AppLanguage

object LanguageManager {

    fun setLanguage(language: AppLanguage) {
        val localeCode = when (language) {
            AppLanguage.RUSSIAN -> "ru"
            AppLanguage.ENGLISH -> "en"
        }
        val localeList = LocaleListCompat.forLanguageTags(localeCode)
        AppCompatDelegate.setApplicationLocales(localeList)
    }

    fun getCurrentLanguage(): AppLanguage {
        val currentLocales = AppCompatDelegate.getApplicationLocales()
        val languageTag = currentLocales.toLanguageTags()

        return if (languageTag.contains("ru")) {
            AppLanguage.RUSSIAN
        } else {
            AppLanguage.ENGLISH
        }
    }

    fun getDefaultLanguage(): AppLanguage {
        val systemLocales = LocaleListCompat.getDefault()
        val systemLang = systemLocales[0]?.language

        return if (systemLang == "ru") {
            AppLanguage.RUSSIAN
        } else {
            AppLanguage.ENGLISH
        }
    }
}