import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.atmosphere.android.application)
    alias(libs.plugins.atmosphere.android.application.compose)
    alias(libs.plugins.atmosphere.hilt)
    alias(libs.plugins.kotlinx.serialization)

}

val keystorePropertiesFile = rootProject.file("keystore.properties")
val keystoreProperties = Properties()
if (keystorePropertiesFile.exists()) {
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))
}

android {
    namespace = "ru.anlyashenko.atmosphereapp"

    defaultConfig {
        applicationId = "ru.anlyashenko.atmosphereapp"
        versionCode = 1
        versionName = "1.0"

    }

    signingConfigs {
        create("release") {
            if (keystorePropertiesFile.exists()) {
                storeFile = rootProject.file(keystoreProperties.getProperty("storeFile"))
                storePassword = keystoreProperties.getProperty("storePassword")
                keyAlias = keystoreProperties.getProperty("keyAlias")
                keyPassword = keystoreProperties.getProperty("keyPassword")
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            signingConfig = signingConfigs.getByName("release")

            ndk {
                //noinspection ChromeOsAbiSupport
                abiFilters += setOf("armeabi-v7a", "arm64-v8a")
            }
        }
    }

    packaging {
        resources {
            excludes.add("/META-INF/{AL2.0,LGPL2.1}")
            excludes.add("/okhttp3/internal/publicsuffix/NOTICE")
            excludes.add("/kotlin/**")
            excludes.add("/META-INF/androidx.*.version")
            excludes.add("/META-INF/com.google.*.version")
            excludes.add("/META-INF/kotlinx_*.version")
        }
    }

}

dependencies {
    implementation(projects.feature.yearlystats.api)
    implementation(projects.feature.yearlystats.impl)

    implementation(projects.feature.settings.api)
    implementation(projects.feature.settings.impl)

    implementation(projects.feature.profile.api)
    implementation(projects.feature.profile.impl)

    implementation(projects.feature.onboarding.api)
    implementation(projects.feature.onboarding.impl)

    implementation(projects.feature.home.api)
    implementation(projects.feature.home.impl)

    implementation(projects.feature.calendar.api)
    implementation(projects.feature.calendar.impl)

    implementation(projects.core.common)
    implementation(projects.core.presentation)
    implementation(projects.core.designsystem)
    implementation(projects.core.data)
    implementation(projects.core.model)

    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation3.ui)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.compose.lifecycle)
    implementation(libs.androidx.lifecycle.viewModel.navigation3)
    implementation(libs.kotlinx.serialization.converter)

    ksp(libs.hilt.compiler)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
}
