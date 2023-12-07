plugins {
    id("st.kotlin-library")
    id("st.ktlint")
    alias(libs.plugins.ksp)
}

dependencies {

    implementation(libs.coroutines.core)

    implementation(libs.hilt.core)
    ksp(libs.hilt.android.compiler)

}