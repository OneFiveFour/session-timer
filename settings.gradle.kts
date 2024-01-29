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
include(":core:defaults")
include(":core:di")
include(":core:theme")
include(":core:database")
include(":feature:session-editor")
include(":feature:session-overview")
include(":feature:taskgroup-editor")
