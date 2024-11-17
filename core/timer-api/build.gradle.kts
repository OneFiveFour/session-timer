plugins {
    alias(libs.plugins.ksp)
    id("st.kotlin.library")
    id("st.kotlin.test")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.timer.api"
}

dependencies {

    // DI
    implementation(libs.hilt.core)
    ksp(libs.hilt.compiler)

    // Coroutines
    implementation(libs.coroutines.core)
}
