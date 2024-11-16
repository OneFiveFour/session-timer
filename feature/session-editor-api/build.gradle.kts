plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    id("st.android-library")
    id("st.kotlin-test")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.sessioneditor.api"
}

dependencies {

    implementation(project(":feature:session-editor"))
    implementation(project(":feature:taskgroup-editor-api"))

    // Navigation
    api(libs.androidX.navigation)
    implementation(libs.kotlinx.serialization.json)
}