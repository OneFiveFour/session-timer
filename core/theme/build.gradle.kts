plugins {
    id("st.android.library")
    id("st.android.compose")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.theme"
}

dependencies {
    implementation(platform(libs.androidX.compose.bom))
    implementation(libs.androidX.compose.material3)
    implementation(libs.androidX.core)
}
