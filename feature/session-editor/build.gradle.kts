plugins {
    id("st.android.library")
    id("st.android.compose")
    id("st.android.hilt")
    id("st.android.test")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".feature.sessioneditor"
}

dependencies {

    implementation(project(":core:common"))
    implementation(project(":core:theme"))
    implementation(project(":core:usecases-api"))
    implementation(project(":feature:session-editor-api"))

    implementation(libs.bundles.navigation)
}
