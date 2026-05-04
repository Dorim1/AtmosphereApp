plugins {
    alias(libs.plugins.atmosphere.android.library)
    alias(libs.plugins.atmosphere.hilt)
    alias(libs.plugins.kotlinx.serialization)
}
android {
    namespace = "ru.anlyashenko.core.network"
}

dependencies {
    api(projects.core.model)

    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.converter)
}
