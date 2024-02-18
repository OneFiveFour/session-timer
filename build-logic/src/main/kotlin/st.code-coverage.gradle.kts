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
                "net.onefivefour.sessiontimer.core.database.Database",
                "net.onefivefour.sessiontimer.core.database.database.*",
                "net.onefivefour.sessiontimer.core.database.domain.model.*",
                "net.onefivefour.sessiontimer.core.database.Task*",
                "net.onefivefour.sessiontimer.core.database.TaskGroup*",
                "net.onefivefour.sessiontimer.core.database.Session*"
            )
            packages(
                // common
                "hilt_aggregated_deps",
                "dagger.hilt.internal.aggregatedroot.codegen",
                "net.onefivefour.sessiontimer.core.theme",
                "*.di",
                "de.onecode.navigator"
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


