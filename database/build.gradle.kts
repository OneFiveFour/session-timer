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
                "net.onefivefour.sessiontimer.Blip",
                "net.onefivefour.sessiontimer.Blop",
                "net.onefivefour.sessiontimer.Session",
                "net.onefivefour.sessiontimer.BlipQueries*",
                "net.onefivefour.sessiontimer.BlopQueries*",
                "net.onefivefour.sessiontimer.SessionQueries*",

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


