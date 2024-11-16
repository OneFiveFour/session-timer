plugins {
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)
    id("st.android-library")
    id("st.kotlin-test")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.taskgroupeditor.api"
}

dependencies {

    implementation(project(":feature:taskgroup-editor"))

    // Navigation
    api(libs.androidX.navigation)
    implementation(libs.kotlinx.serialization.json)
}