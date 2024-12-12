plugins {
    id("st.kotlin.library")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.database.api"
}

dependencies {

    implementation(project(":core:common"))

    // Coroutines
    implementation(libs.coroutines.core)
}
