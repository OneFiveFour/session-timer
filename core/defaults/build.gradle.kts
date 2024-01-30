plugins {
    alias(libs.plugins.ksp)
    id("st.android-library")
    id("st.ktlint")
    id("st.kotlin-test")
}

android {
    namespace = AppConfig.applicationId + ".core.defaults"
}

dependencies {

    implementation(platform(libs.androidX.compose.bom))
    implementation(libs.androidX.compose.runtime)

    implementation(project(":core:database"))

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
}
