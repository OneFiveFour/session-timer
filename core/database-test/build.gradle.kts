plugins {
    id("st.kotlin.library")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.database.test"
}

dependencies {
    implementation(project(":core:common"))
}