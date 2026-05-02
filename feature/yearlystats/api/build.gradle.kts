plugins {
    alias(libs.plugins.atmosphere.android.feature.api)
}

android {
    namespace = "ru.anlyashenko.feature.yearlystats.api"
}

dependencies {
    api(project(":core:navigation"))
}