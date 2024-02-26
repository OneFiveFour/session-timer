plugins {
    alias(libs.plugins.ksp)
    id("st.kotlin-library")
    id("st.kotlin-test")
    id("st.ktlint")
}

dependencies {

    implementation(project(":core:di"))
    implementation(project(":core:timer-api"))

    // DI
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Coroutines
    implementation(libs.coroutines.core)
}
