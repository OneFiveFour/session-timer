plugins {
    alias(libs.plugins.ksp)
    id("st.kotlin.library")
    id("st.kotlin.hilt")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.di"
}

dependencies {

    implementation(libs.coroutines.core)
}
