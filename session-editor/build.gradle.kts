plugins {
    id("st.android-library")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".sessioneditor"
}

dependencies {
    implementation(platform(libs.androidX.compose.bom))
    implementation(libs.androidX.compose.ui)
    debugImplementation(libs.androidX.compose.ui.tooling)
    implementation(libs.androidX.compose.ui.tooling.preview)
    implementation(libs.androidX.compose.material3)
}