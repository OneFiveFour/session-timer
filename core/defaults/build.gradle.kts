plugins {
    id("st.android.library")
    id("st.android.hilt")
    id("st.kotlin.test")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.defaults"
}

dependencies {

    implementation(platform(libs.androidX.compose.bom))
    implementation(libs.androidX.compose.runtime)

    implementation(project(":core:common"))
    implementation(project(":core:database"))
}
