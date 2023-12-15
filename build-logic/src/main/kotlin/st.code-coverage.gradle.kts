plugins {
    id("org.jetbrains.kotlinx.kover")
}

koverReport {
    filters {
        excludes {
            classes(
                "*BuildConfig",
                "*_Factory",
                "*_HiltModules*",
                "hilt_aggregated_deps.*Module"
            )
            packages(
                "*.di"
            )
            annotatedBy(
                "*Composable*"
            )
        }
    }
}