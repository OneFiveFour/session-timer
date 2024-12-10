plugins {
    id("st.kotlin.library")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.timer.api"
}

dependencies {

    // Coroutines
    implementation(libs.coroutines.core)
}
