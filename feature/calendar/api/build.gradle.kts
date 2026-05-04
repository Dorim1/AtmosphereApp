plugins {
    alias(libs.plugins.atmosphere.android.feature.api)
}

android {
    namespace = "ru.anlyashenko.feature.calendar.api"
}

dependencies {
    api(projects.core.navigation)
}
