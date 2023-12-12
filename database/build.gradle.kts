plugins {
    alias(libs.plugins.kover)
    alias(libs.plugins.sqlDelight)
    alias(libs.plugins.ksp)
    id("st.kotlin-library")
    id("st.kotlin-test")
    id("st.ktlint")
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("net.onefivefour.sessiontimer.database")
        }
    }
}

koverReport {
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
            packages(
                "*.di",
                "net.onefivefour.sessiontimer.database.domain.*",
                "net.onefivefour.sessiontimer.database.domain.model.*"
            )
        }
    }
}

dependencies {

    implementation(project(":core"))

    // Database
    implementation(libs.sqlDelight.coroutines)

    // Dependency Injection
    implementation(libs.hilt.core)
    ksp(libs.hilt.android.compiler)

    // Coroutines
    implementation(libs.coroutines.core)

    // Date Time
    implementation(libs.kotlinx.datetime)

    // Testing
    testImplementation(libs.sqlDelight.test)
}


