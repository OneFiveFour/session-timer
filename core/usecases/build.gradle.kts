plugins {
    id("st.android.library")
    id("st.android.hilt")
    id("st.kotlin.test")
    id("st.ktlint")
}

android {
    namespace = AppConfig.applicationId + ".core.usecases"
}

dependencies {

    implementation(platform(libs.androidX.compose.bom))
    implementation(libs.androidX.compose.runtime)

    implementation(project(":core:database-api"))
    implementation(project(":core:defaults"))
    implementation(project(":core:common"))
    implementation(project(":core:timer-api"))
    implementation(project(":core:usecases-api"))

    testImplementation(project(":core:database-test"))
}
