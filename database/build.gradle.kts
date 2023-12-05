plugins {
    kotlin("kapt")
    alias(libs.plugins.sqlDelight)
    id("st.kotlin-library")
    id("st.kotlin-test")
    id("st.ktlint")
    id("org.jetbrains.kotlinx.kover")
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("net.onefivefour.sessiontimer.database")
        }
    }
}

koverReport {
    // filters for all report types of all build variants
    filters {
        excludes {
            classes(
                "*_Factory",
                "hilt_aggregated_deps.*Module",
                "net.onefivefour.sessiontimer.database.Database*",
                "net.onefivefour.sessiontimer.database.database.DatabaseImpl*",
                "net.onefivefour.sessiontimer.database.database.DatabaseImplKt*",
                "net.onefivefour.sessiontimer.database.Task",
                "net.onefivefour.sessiontimer.database.TaskGroup",
                "net.onefivefour.sessiontimer.database.Session",
                "net.onefivefour.sessiontimer.database.TaskQueries*",
                "net.onefivefour.sessiontimer.database.TaskGroupQueries*",
                "net.onefivefour.sessiontimer.database.SessionQueries*",

            )
            packages("*.di")
        }
    }
}

dependencies {

    // Database
    implementation(libs.sqlDelight.coroutines)

    // Dependency Injection
    implementation(libs.hilt.core)
    kapt(libs.hilt.android.compiler)

    // Coroutines
    implementation(libs.coroutines.core)

    // Testing
    testImplementation(libs.sqlDelight.test)
}


