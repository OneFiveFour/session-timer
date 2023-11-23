plugins {
    id("st.kotlin-library")
    kotlin("kapt")
    alias(libs.plugins.sqlDelight)
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("net.onefivefour.sessiontimer")
        }
    }
}

dependencies {
    implementation(libs.sqlDelight)
    implementation(libs.hilt.android)
    kapt(libs.hilt.android.compiler)
}