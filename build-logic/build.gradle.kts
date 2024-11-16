plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("androidCompose") {
            id = "st.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }
    }
}

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
}

dependencies {
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.android.gradlePlugin)
    implementation(libs.ktLint.gradlePlugin)
}