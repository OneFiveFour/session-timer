plugins {
    alias(libs.plugins.ksp)
    id("st.kotlin.library")
    id("st.kotlin.hilt")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.usecases.test"
}

dependencies {

    implementation(libs.coroutines.core)
    implementation(libs.mockk)

    implementation(project(":core:timer-api"))
    implementation(project(":core:usecases-api"))
}
