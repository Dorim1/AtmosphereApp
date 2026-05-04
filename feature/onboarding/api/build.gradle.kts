plugins {
    alias(libs.plugins.atmosphere.android.feature.api)
}

android {
    namespace = "ru.anlyashenko.feature.onboarding.api"
}

dependencies {
    api(projects.core.navigation)
}
