plugins {
    alias(libs.plugins.atmosphere.android.application)
    alias(libs.plugins.atmosphere.android.application.compose)
    alias(libs.plugins.atmosphere.hilt)
    alias(libs.plugins.kotlinx.serialization)

}

android {
    namespace = "ru.anlyashenko.atmosphereapp"

    defaultConfig {
        applicationId = "ru.anlyashenko.atmosphereapp"
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    packaging { resources { excludes.add("/META-INF/{AL2.0,LGPL2.1}") } }

}

dependencies {
    // Feature modules
    implementation(project(":feature:yearlystats:api"))
    implementation(project(":feature:yearlystats:impl"))

    implementation(project(":feature:settings:api"))
    implementation(project(":feature:settings:impl"))

    implementation(project(":feature:profile:api"))
    implementation(project(":feature:profile:impl"))

    implementation(project(":feature:onboarding:api"))
    implementation(project(":feature:onboarding:impl"))

    implementation(project(":feature:home:api"))
    implementation(project(":feature:home:impl"))

    implementation(project(":feature:calendar:api"))
    implementation(project(":feature:calendar:impl"))

    // Core модули
    implementation(project(":core:common"))
    implementation(project(":core:presentation"))
    implementation(project(":core:designsystem"))
    implementation(project(":core:data"))
    implementation(project(":core:model"))

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

    debugImplementation(libs.androidx.compose.ui.test.manifest)
}