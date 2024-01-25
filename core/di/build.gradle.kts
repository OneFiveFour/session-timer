plugins {
    alias(libs.plugins.ksp)
    id("st.android-library")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.di"
}

dependencies {

    implementation(libs.coroutines.core)

    api(libs.androidX.navigation)

    implementation(libs.hilt.core)
    ksp(libs.hilt.android.compiler)
}
