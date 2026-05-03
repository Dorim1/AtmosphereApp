plugins {
    alias(libs.plugins.atmosphere.android.feature.impl)
    alias(libs.plugins.atmosphere.android.library.compose)
}

android {
    namespace = "ru.anlyashenko.feature.settings.impl"
}

dependencies {
    implementation(projects.feature.settings.api)

    implementation(projects.core.data)
    implementation(projects.core.presentation)
    implementation(projects.core.notifications)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

}
