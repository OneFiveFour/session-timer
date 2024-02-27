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
    implementation(libs.hilt.core)
    ksp(libs.hilt.compiler)

    // Coroutines
    implementation(libs.coroutines.core)
}
