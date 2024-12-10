plugins {
    id("st.kotlin.library")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.timer.test"
}

dependencies {

    // Coroutines
    implementation(project(":core:timer-api"))
}
