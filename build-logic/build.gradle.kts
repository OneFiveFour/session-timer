plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("androidCompose") {
            id = "st.compose"
            implementationClass = "AndroidComposeConventionPlugin"
        }
        register("ktlint") {
            id = "st.ktlint"
            implementationClass = "KtlintPlugin"
        }
        register("kotlin") {
            id = "st.kotlin"
            implementationClass = "KotlinPlugin"
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