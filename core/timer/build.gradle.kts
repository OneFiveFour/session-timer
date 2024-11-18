plugins {
    alias(libs.plugins.ksp)
    id("st.kotlin.library")
    id("st.kotlin.hilt")
    id("st.kotlin.test")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.timer"
}

dependencies {

    implementation(project(":core:di"))
    implementation(project(":core:timer-api"))

    // Coroutines
    implementation(libs.coroutines.core)
}
