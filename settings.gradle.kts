pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "AtmosphereApp"

include(":app")
include(":core:network")
include(":core:database")
include(":core:data")
include(":core:model")
include(":core:common")
include(":core:designsystem")
include(":core:presentation")
include(":core:notifications")
include(":feature:profile")
include(":core:navigation")

include(":feature:onboarding:api")
include(":feature:onboarding:impl")

include(":feature:home:api")
include(":feature:home:impl")

include(":feature:calendar:api")
include(":feature:calendar:impl")

include(":feature:profile:api")
include(":feature:profile:impl")

include(":feature:settings:api")
include(":feature:settings:impl")

include(":feature:yearlystats:api")
include(":feature:yearlystats:impl")

check(JavaVersion.current().isCompatibleWith(JavaVersion.VERSION_17)) {
    """
    Now in Android requires JDK 17+ but it is currently using JDK ${JavaVersion.current()}.
    Java Home: [${System.getProperty("java.home")}]
    https://developer.android.com/build/jdks#jdk-config-in-studio
    """.trimIndent()
}