plugins {
    alias(libs.plugins.sqlDelight)
    id("st.kotlin.library")
    id("st.kotlin.test")
    id("st.kotlin.hilt")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.database"
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("net.onefivefour.sessiontimer.core.database")
        }
    }
}

kover {
    reports {
        filters {
            excludes {
                classes(
                    "net.onefivefour.sessiontimer.core.database.Database",
                    "net.onefivefour.sessiontimer.core.database.database.*",
                    "net.onefivefour.sessiontimer.core.database.domain.model.*",
                    "net.onefivefour.sessiontimer.core.database.Task*",
                    "net.onefivefour.sessiontimer.core.database.TaskGroup*",
                    "net.onefivefour.sessiontimer.core.database.Session*"
                )
            }
        }
    }
}

dependencies {

    implementation(project(":core:common"))
    implementation(project(":core:database-api"))
    implementation(project(":core:di"))

    // Database
    implementation(libs.sqlDelight.coroutines)

    // Coroutines
    implementation(libs.coroutines.core)

    // Date Time
    implementation(libs.kotlinx.datetime)

    // Testing
    testImplementation(libs.sqlDelight.test)
}
