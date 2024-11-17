plugins {
    alias(libs.plugins.ksp)
    id("st.kotlin.library")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.di"
}

dependencies {

    implementation(libs.coroutines.core)

    implementation(libs.hilt.core)
    ksp(libs.hilt.compiler)
}
