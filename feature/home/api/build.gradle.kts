plugins {
    alias(libs.plugins.atmosphere.android.feature.api)
}

android {
    namespace = "ru.anlyashenko.feature.home.api"
}

dependencies {
    api(project(":core:navigation"))
}