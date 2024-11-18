plugins {
    alias(libs.plugins.ksp)
    id("st.android.library")
    id("st.android.compose")
    id("st.android.hilt")
    id("st.kotlin.test")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".feature.sessionplayer"
}

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:theme"))
    implementation(project(":core:usecases"))
    implementation(project(":core:timer-api"))
    implementation(project(":feature:session-player-api"))
}
