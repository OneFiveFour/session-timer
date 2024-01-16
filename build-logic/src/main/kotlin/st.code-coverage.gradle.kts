import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

plugins {
    id("org.jetbrains.kotlinx.kover")
}

subprojects {
    apply(plugin = "org.jetbrains.kotlinx.kover")

    pluginManager.withPlugin("com.android.library") {
        koverAndroidMerge("debug")
    }
    pluginManager.withPlugin("com.android.application") {
        koverAndroidMerge("debug")
    }

    rootProject.dependencies.add("kover", this)
}

koverReport {
    filters {
        excludes {
            classes(
                // common
                "*ComposableSingletons*",
                "*Hilt_*",
                "*_HiltModules*",
                "*BuildConfig",
                "*_Factory",

                // database
                "net.onefivefour.sessiontimer.database.Database",
                "net.onefivefour.sessiontimer.database.database.*",
                "net.onefivefour.sessiontimer.database.domain.model.*",
                "net.onefivefour.sessiontimer.database.Task*",
                "net.onefivefour.sessiontimer.database.TaskGroup*",
                "net.onefivefour.sessiontimer.database.Session*"
            )
            packages(
                // common
                "hilt_aggregated_deps",
                "dagger.hilt.internal.aggregatedroot.codegen",
                "net.onefivefour.sessiontimer.theme",
                "*.di",
                "io.redandroid.navigator"
            )
        }
    }
}

fun Project.koverAndroidMerge(buildVariant: String) {
    koverReport {
        defaults {
            mergeWith(buildVariant)
        }
    }
}


