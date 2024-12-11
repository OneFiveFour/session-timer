plugins {
    id("st.kotlin.library")
    id("st.kotlin.test")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.common"
}

dependencies {
    testImplementation(project(":core:common-test"))
}
