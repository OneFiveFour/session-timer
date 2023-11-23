plugins {
    id("st.kotlin-library")
    kotlin("kapt")
    alias(libs.plugins.sqlDelight)
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("net.onefivefour.sessiontimer")
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
}