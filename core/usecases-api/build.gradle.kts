plugins {
    id("st.kotlin.library")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.usecases.api"
}

dependencies {

    implementation(libs.coroutines.core)

    implementation(project(":core:common"))
    implementation(project(":core:timer-api"))
}
