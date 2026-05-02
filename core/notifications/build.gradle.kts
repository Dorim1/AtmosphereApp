plugins {
    alias(libs.plugins.atmosphere.android.library)
    alias(libs.plugins.atmosphere.hilt)
}

android {
    namespace = "ru.anlyashenko.core.notifications"
}

dependencies {
    implementation(project(":core:data"))

    compileOnly(platform(libs.androidx.compose.bom))
}