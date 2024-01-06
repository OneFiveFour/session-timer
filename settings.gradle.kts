pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "session-timer"

include(":app")
include(":core")
include(":session-overview")
include(":session-overview-api")
include(":theme")
include(":database")
include(":session-editor")
include(":session-editor-api")
