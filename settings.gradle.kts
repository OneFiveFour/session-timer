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
include(":core:common")
include(":core:defaults")
include(":core:di")
include(":core:theme")
include(":core:database")
include(":core:usecases")
include(":feature:session-editor")
include(":feature:session-overview")
include(":feature:session-player")
include(":feature:taskgroup-editor")
