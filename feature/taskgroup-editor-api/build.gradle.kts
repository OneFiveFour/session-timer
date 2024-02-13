plugins {
    alias(libs.plugins.ksp)
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
    implementation(libs.compose.navigator.api)
    implementation(libs.compose.navigator.runtime)
    ksp(libs.compose.navigator.ksp)
}