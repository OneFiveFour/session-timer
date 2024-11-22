plugins {
    alias(libs.plugins.ksp)
    id("st.android.library")
    id("st.android.compose")
    id("st.android.hilt")
    id("st.kotlin.test")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".feature.sessioneditor"
}

dependencies {

    implementation(project(":core:common"))
    implementation(project(":core:theme"))
    implementation(project(":core:usecases"))
    implementation(project(":feature:session-editor-api"))

    implementation(libs.bundles.navigation)

    testImplementation(project(":core:test"))
}
