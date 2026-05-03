import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
    alias(libs.plugins.android.lint)
}

group = "ru.anlyashenko.atmosphereapp.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.compose.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
    compileOnly(libs.detekt.gradlePlugin)
    implementation(libs.room.gradlePlugin) // todo: Должен быть compileOnly но чёт не работает с ним
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = libs.plugins.atmosphere.android.application.compose.get().pluginId
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplication") {
            id = libs.plugins.atmosphere.android.application.asProvider().get().pluginId
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = libs.plugins.atmosphere.android.library.compose.get().pluginId
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = libs.plugins.atmosphere.android.library.asProvider().get().pluginId
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidFeatureImpl") {
            id = libs.plugins.atmosphere.android.feature.impl.get().pluginId
            implementationClass = "AndroidFeatureImplConventionPlugin"
        }
        register("androidFeatureApi") {
            id = libs.plugins.atmosphere.android.feature.api.get().pluginId
            implementationClass = "AndroidFeatureApiConventionPlugin"
        }
        register("hilt") {
            id = libs.plugins.atmosphere.hilt.get().pluginId
            implementationClass = "HiltConventionPlugin"
        }
        register("androidRoom") {
            id = libs.plugins.atmosphere.android.room.get().pluginId
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("jvmLibrary") {
            id = libs.plugins.atmosphere.jvm.library.get().pluginId
            implementationClass = "JvmLibraryConventionPlugin"
        }
        register("detekt") {
            id = libs.plugins.atmosphere.detekt.get().pluginId
            implementationClass = "DetektConventionPlugin"
        }
    }
}

