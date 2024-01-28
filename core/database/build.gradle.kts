plugins {
    alias(libs.plugins.sqlDelight)
    alias(libs.plugins.ksp)
    id("st.kotlin-library")
    id("st.kotlin-test")
    id("st.ktlint")
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("net.onefivefour.sessiontimer.core.database")
        }
    }
}

dependencies {

    implementation(project(":core:di"))

    // Database
    implementation(libs.sqlDelight.coroutines)

    // Dependency Injection
    implementation(libs.hilt.core)
    ksp(libs.hilt.compiler)

    // Coroutines
    implementation(libs.coroutines.core)

    // Date Time
    implementation(libs.kotlinx.datetime)

    // Testing
    testImplementation(libs.sqlDelight.test)
}
