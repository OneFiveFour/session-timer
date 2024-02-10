plugins {
    alias(libs.plugins.ksp)
    id("st.android-library")
    id("st.kotlin-test")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.taskgroupeditor"
}

dependencies {

    implementation(project(":core:common"))
    implementation(project(":core:theme"))
    implementation(project(":core:usecases"))

    // Compose
    implementation(platform(libs.androidX.compose.bom))
    implementation(libs.androidX.compose.ui)
    debugImplementation(libs.androidX.compose.ui.tooling)
    implementation(libs.androidX.compose.ui.tooling.preview)
    implementation(libs.androidX.compose.material3)

    // DI
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation)
    ksp(libs.hilt.android.compiler)

    // Navigation
    api(libs.androidX.navigation)
    implementation(libs.compose.navigator.api)
    implementation(libs.compose.navigator.runtime)
    ksp(libs.compose.navigator.ksp)

    // collectAsStateWithLifecycle
    implementation(libs.androidX.lifecycle.compose)
}
