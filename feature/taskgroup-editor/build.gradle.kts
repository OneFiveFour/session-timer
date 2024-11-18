plugins {
    alias(libs.plugins.ksp)
    id("st.android.library")
    id("st.android.compose")
    id("st.android.hilt")
    id("st.kotlin.test")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.taskgroupeditor"
}

dependencies {

    implementation(project(":core:common"))
    implementation(project(":core:theme"))
    implementation(project(":core:usecases"))
    implementation(project(":feature:taskgroup-editor-api"))

    implementation(libs.bundles.navigation)
}
