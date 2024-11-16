plugins {
    alias(libs.plugins.ksp)
    id("st.android-library")
    id("st.compose")
    id("st.kotlin-test")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.sessionplayer.api"
}

dependencies {

    // Navigation
    api(libs.androidX.navigation)

    // DI
    implementation(libs.hilt.core)
    ksp(libs.hilt.compiler)
}