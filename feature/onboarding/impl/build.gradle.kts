plugins {
    alias(libs.plugins.atmosphere.android.feature.impl)
    alias(libs.plugins.atmosphere.android.library.compose)
}

android {
    namespace = "ru.anlyashenko.feature.onboarding.impl"
}

dependencies {
    implementation(projects.feature.onboarding.api)
    implementation(projects.feature.home.api)

    implementation(projects.core.data)
    implementation(projects.core.notifications)
    implementation(projects.core.presentation)

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

}
