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
            packageName.set("net.onefivefour.sessiontimer.database")
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


