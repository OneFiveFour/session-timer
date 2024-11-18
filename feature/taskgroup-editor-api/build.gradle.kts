plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    id("st.android.library")
    id("st.kotlin.test")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.taskgroupeditor.api"
}

dependencies {

    // Navigation
    implementation(libs.bundles.navigation)
}
