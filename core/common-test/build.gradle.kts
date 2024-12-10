plugins {
    id("st.kotlin.library")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.common.test"
}

dependencies {
    implementation(project(":core:common"))
}
