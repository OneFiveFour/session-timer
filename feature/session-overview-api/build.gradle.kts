plugins {
    alias(libs.plugins.kotlin.serialization)
    id("st.kotlin.library")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.sessionoverview.api"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)
}
