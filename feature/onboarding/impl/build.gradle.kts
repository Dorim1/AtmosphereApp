plugins {
    alias(libs.plugins.atmosphere.android.feature.impl)
    alias(libs.plugins.atmosphere.android.library.compose)
}

android {
    namespace = "ru.anlyashenko.feature.onboarding.impl"
}

dependencies {
    implementation(project(":feature:onboarding:api"))
    implementation(project(":feature:home:api"))

    implementation(project(":core:data"))
    implementation(project(":core:notifications"))
    implementation(project(":core:presentation"))

    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)

}