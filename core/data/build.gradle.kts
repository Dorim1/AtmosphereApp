plugins {
    alias(libs.plugins.atmosphere.android.library)
    alias(libs.plugins.atmosphere.hilt)
}

android {
    namespace = "ru.anlyashenko.core.data"
}

dependencies {
    api(projects.core.common)
    api(projects.core.database)
    api(projects.core.network)
    api(projects.core.model)

    implementation(libs.androidx.datastore.preferences)

    testImplementation(libs.junit)

}
