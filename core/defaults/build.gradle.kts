plugins {
    alias(libs.plugins.ksp)
    id("st.android-library")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.defaults"
}

dependencies {

    implementation(project(":core:database"))

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
}