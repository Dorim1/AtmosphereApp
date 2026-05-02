plugins {
    alias(libs.plugins.atmosphere.jvm.library)
    alias(libs.plugins.atmosphere.hilt)
}

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.jakarta.inject.api)
}

