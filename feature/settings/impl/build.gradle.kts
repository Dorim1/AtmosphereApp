plugins {
    alias(libs.plugins.atmosphere.android.feature.impl)
    alias(libs.plugins.atmosphere.android.library.compose)
}

android {
    namespace = "ru.anlyashenko.feature.settings.impl"
}

dependencies {
    implementation(project(":feature:settings:api"))

    implementation(project(":core:data"))
    implementation(project(":core:presentation"))
    implementation(project(":core:notifications"))

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

}