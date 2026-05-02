plugins {
    alias(libs.plugins.atmosphere.android.feature.api)
}

android {
    namespace = "ru.anlyashenko.feature.profile.api"
}

dependencies {
    api(project(":core:navigation"))
}