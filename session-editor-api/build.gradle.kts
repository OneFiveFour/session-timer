plugins {
    id("st.android-library")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".sessioneditor.api"
}

dependencies {
    api(project(":core"))

}