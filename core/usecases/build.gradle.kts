plugins {
    alias(libs.plugins.ksp)
    id("st.android-library")
    id("st.kotlin-test")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.usecases"
}

dependencies {

    implementation(platform(libs.androidX.compose.bom))
    implementation(libs.androidX.compose.runtime)

    implementation(project(":core:database"))
    implementation(project(":core:defaults"))

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
}