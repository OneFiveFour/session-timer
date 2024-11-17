plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    id("st.android.library")
    id("st.kotlin.test")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.sessionoverview.api"
}

dependencies {

    implementation(project(":feature:session-overview"))
    implementation(project(":feature:session-editor-api"))
    implementation(project(":feature:session-player-api"))

    // Navigation
    api(libs.androidX.navigation)
    implementation(libs.kotlinx.serialization.json)
}