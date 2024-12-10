plugins {
    id("st.android.library")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.test"
}

dependencies {
    implementation(libs.junit)
    implementation(libs.mockk)

    implementation(libs.coroutines.test)
    implementation(libs.androidX.lifecycle.viewmodel.compose)
    implementation(libs.androidX.navigation.common)
}
