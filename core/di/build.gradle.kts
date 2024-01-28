plugins {
    alias(libs.plugins.ksp)
    id("st.kotlin-library")
    id("st.ktlint")
}

dependencies {

    implementation(libs.coroutines.core)

    implementation(libs.hilt.core)
    ksp(libs.hilt.compiler)
}
