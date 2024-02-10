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

    implementation(project(":core:common"))
    implementation(project(":core:theme"))
    implementation(project(":core:usecases"))

    // Navigation
    api(libs.androidX.navigation)
    implementation(libs.compose.navigator.api)
    implementation(libs.compose.navigator.runtime)
    ksp(libs.compose.navigator.ksp)

    // Compose
    implementation(platform(libs.androidX.compose.bom))
    implementation(libs.androidX.compose.ui)
    implementation(libs.androidX.compose.ui.tooling.preview)
    implementation(libs.androidX.compose.material3)
    debugImplementation(libs.androidX.compose.ui.tooling)

    // DI
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
    ksp(libs.hilt.android.compiler)

    // Lifecycle
    implementation(libs.androidX.lifecycle.viewmodel)
    implementation(libs.androidX.lifecycle.viewmodel.compose)
    implementation(libs.androidX.lifecycle.compose)
}
