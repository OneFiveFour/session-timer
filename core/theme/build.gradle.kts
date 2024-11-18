plugins {
    id("st.android.library")
    id("st.android.compose")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.theme"
}
