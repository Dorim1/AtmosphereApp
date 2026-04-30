package ru.anlyashenko.atmosphereapp.core.utils
// todo: ----
/*
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import ru.anlyashenko.atmosphereapp.feature.settings.ui.AppLanguage

object LanguageManager {

    fun setLanguage(language: AppLanguage) {
        val localeCode = when (language) {
            AppLanguage.RUSSIAN -> "ru"
            AppLanguage.ENGLISH -> "en"
            AppLanguage.JAPANESE -> "ja"
        }
        val localeList = LocaleListCompat.forLanguageTags(localeCode)
        AppCompatDelegate.setApplicationLocales(localeList)
    }

    fun getCurrentLanguage(): AppLanguage {
        val currentLocales = AppCompatDelegate.getApplicationLocales()
        val languageTag = currentLocales.toLanguageTags()

        return when {
            languageTag.startsWith("en") -> AppLanguage.ENGLISH
            languageTag.startsWith("ru") -> AppLanguage.RUSSIAN
            languageTag.startsWith("ja") -> AppLanguage.JAPANESE
            else -> AppLanguage.ENGLISH
        }
    }

    fun getDefaultLanguage(): AppLanguage {
        val systemLocales = LocaleListCompat.getDefault()
        val systemLang = systemLocales[0]?.language

        return when (systemLang) {
            "ru" -> AppLanguage.RUSSIAN
            "ja" -> AppLanguage.JAPANESE
            "en" -> AppLanguage.ENGLISH
            else -> AppLanguage.ENGLISH
        }
    }
}*/
