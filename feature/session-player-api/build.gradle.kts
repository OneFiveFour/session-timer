plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    id("st.android.library")
    id("st.android.compose")
    id("st.kotlin.test")
    id("st.kotlin.hilt")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.sessionplayer.api"
}

dependencies {

    // Navigation
    implementation(libs.bundles.navigation)
}
