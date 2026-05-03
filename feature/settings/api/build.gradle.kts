plugins {
    alias(libs.plugins.atmosphere.android.feature.api)
}
android {
    namespace = "ru.anlyashenko.feature.settings.api"
}

dependencies {
    api(projects.core.navigation)
}
