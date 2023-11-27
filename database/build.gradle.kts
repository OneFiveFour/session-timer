plugins {
    id("st.kotlin-library")
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
    testImplementation(platform("org.junit:junit-bom:5.10.1"))

    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    testImplementation("io.mockk:mockk:1.13.8")

    testImplementation("app.cash.sqldelight:sqlite-driver:2.0.0")
}