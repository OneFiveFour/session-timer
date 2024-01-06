plugins {
    id("st.android-library")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".sessionoverview.api"
}

dependencies {
    api(project(":core"))

}