plugins {
    alias(libs.plugins.ksp)
    id("st.android-library")
    id("st.kotlin-test")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".feature.sessioneditor"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:theme"))
    implementation(project(":core:usecases"))

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
