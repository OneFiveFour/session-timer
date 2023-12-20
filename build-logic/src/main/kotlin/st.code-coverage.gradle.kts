import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

plugins {
    id("org.jetbrains.kotlinx.kover")
}

subprojects {
    apply(plugin = "org.jetbrains.kotlinx.kover")

    pluginManager.withPlugin("com.android.library") {
        koverAndroidMerge("debug")
        koverAndroidFilter()
    }
    pluginManager.withPlugin("com.android.application") {
        koverAndroidMerge("debug")
    }
    pluginManager.withPlugin("st.kotlin-library") {
        println("Found a module with kotlin lib ${project.name}")
        koverKotlinFilter()
    }

    rootProject.dependencies.add("kover", this)
}

fun Project.koverAndroidMerge(buildVariant: String) {
    koverReport {
        defaults {
            mergeWith(buildVariant)
        }
    }
}

fun Project.koverAndroidFilter() {
    koverReport {
        androidReports("debug") {

        }
    }
}

fun Project.koverKotlinFilter() {
    koverReport {
        filters {
            excludes {
                classes(
                    "*_Factory"
                )
                packages(
                    "*di",
                    "hilt_aggregated_deps"
                )
            }
        }
    }
}
