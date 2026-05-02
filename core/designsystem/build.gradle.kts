plugins {
    alias(libs.plugins.atmosphere.android.library)
    alias(libs.plugins.atmosphere.android.library.compose)
}

android {
    namespace = "ru.anlyashenko.core.designsystem"
}

dependencies {
    api(libs.androidx.compose.foundation)
    api(libs.androidx.compose.material3)
    api(libs.androidx.compose.runtime)

    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
    testImplementation(libs.junit)

    implementation(project(":core:model"))

}