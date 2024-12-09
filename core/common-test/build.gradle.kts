plugins {
    alias(libs.plugins.ksp)
    id("st.kotlin.library")
    id("st.kotlin.hilt")
    id("st.kotlin.test")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.common.test"
}

dependencies {
    implementation(project(":core:common"))
}
