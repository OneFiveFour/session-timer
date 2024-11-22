plugins {
    id("st.android.library")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.test"

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/*.kotlin_module"
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }
}

dependencies {
    implementation(libs.mockk)

    implementation(platform(libs.junit.bom))
    implementation(libs.junit)
    implementation(libs.androidX.lifecycle.viewmodel.compose)
    implementation(libs.androidX.navigation.common)

}
