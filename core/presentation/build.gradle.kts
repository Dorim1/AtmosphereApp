plugins {
    alias(libs.plugins.atmosphere.android.library)
}

android {
    namespace = "ru.anlyashenko.core.presentation"
}

dependencies {
    api(projects.core.model)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
}
