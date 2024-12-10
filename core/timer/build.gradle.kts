plugins {
    id("st.kotlin.library")
    id("st.android.hilt")
    id("st.kotlin.test")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.timer"
}

dependencies {

    // Core
    implementation(project(":core:di"))
    implementation(project(":core:timer-api"))

    // Coroutines
    implementation(libs.coroutines.core)
}
