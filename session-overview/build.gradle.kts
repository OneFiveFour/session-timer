plugins {
    alias(libs.plugins.ksp)
    id("st.android-library")
    id("st.ktlint")
    id("st.kotlin-test")
    id("st.android-test")
}

android {
    namespace = AppConfig.applicationId + ".sessionoverview"
}

dependencies {
    implementation(project(":theme"))
    implementation(project(":database"))

    implementation(platform(libs.androidX.compose.bom))
    implementation(libs.androidX.compose.ui)
    implementation(libs.androidX.compose.ui.tooling.preview)
    implementation(libs.androidX.compose.material3)
    debugImplementation(libs.androidX.compose.ui.tooling)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    implementation(libs.androidX.lifecycle.viewmodel)
    implementation(libs.androidX.lifecycle.viewmodel.compose)
    implementation(libs.androidX.lifecycle.compose)


}