plugins {
    alias(libs.plugins.ksp)
    id("st.android-library")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".sessioneditor"
}

dependencies {
    implementation(project(":theme"))
    implementation(project(":database"))
    implementation(project(":session-editor-api"))

    api(libs.androidX.navigation)

    implementation(platform(libs.androidX.compose.bom))
    implementation(libs.androidX.compose.ui)
    debugImplementation(libs.androidX.compose.ui.tooling)
    implementation(libs.androidX.compose.ui.tooling.preview)
    implementation(libs.androidX.compose.material3)

    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
    ksp(libs.hilt.android.compiler)
    
    implementation(libs.androidX.lifecycle.compose)
}
