plugins {
    id("st.kotlin-library")
    id("st.kotlin-test")
    kotlin("kapt")
    alias(libs.plugins.sqlDelight)
}


sqldelight {
    databases {
        create("Database") {
            packageName.set("net.onefivefour.sessiontimer.database")
        }
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
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