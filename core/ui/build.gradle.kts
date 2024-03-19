plugins {
    id("st.android-library")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.ui"
}

dependencies {

    implementation(project(":core:theme"))

    implementation(platform(libs.androidX.compose.bom))
    implementation(libs.androidX.compose.material3)
    implementation(libs.androidX.core)
    implementation(libs.androidX.compose.ui.tooling)
    implementation(libs.androidX.compose.ui.tooling.preview)
}
