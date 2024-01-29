plugins {
    alias(libs.plugins.ksp)
    id("st.android-library")
    id("st.ktlint")
    id("st.kotlin-test")
    id("st.android-test")
}

android {
    namespace = AppConfig.applicationId + ".feature.sessionoverview"
}

dependencies {
    implementation(project(":core:theme"))
    implementation(project(":core:database"))
    implementation(project(":core:usecases"))

    api(libs.androidX.navigation)

    implementation(platform(libs.androidX.compose.bom))
    implementation(libs.androidX.compose.ui)
    implementation(libs.androidX.compose.ui.tooling.preview)
    implementation(libs.androidX.compose.material3)
    debugImplementation(libs.androidX.compose.ui.tooling)

    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
    ksp(libs.hilt.android.compiler)

    implementation(libs.androidX.lifecycle.viewmodel)
    implementation(libs.androidX.lifecycle.viewmodel.compose)
    implementation(libs.androidX.lifecycle.compose)
}
