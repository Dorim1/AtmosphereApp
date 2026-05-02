plugins {
    alias(libs.plugins.atmosphere.android.library)
    alias(libs.plugins.atmosphere.hilt)
}

android {
    namespace = "ru.anlyashenko.core.data"
}

dependencies {
    api(project(":core:common"))
    api(project(":core:database"))
    api(project(":core:network"))
    api(project(":core:model"))

    implementation(libs.androidx.datastore.preferences)

    testImplementation(libs.junit)

}