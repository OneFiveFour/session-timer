plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        // Basics
        register("ktlint") {
            id = "st.ktlint"
            implementationClass = "KtlintPlugin"
        }
        // TODO add code coverage plugin

        // Kotlin
        register("kotlin") {
            id = "st.kotlin"
            implementationClass = "KotlinPlugin"
        }
        register("kotlinLibrary") {
            id = "st.kotlin.library"
            implementationClass = "KotlinLibraryPlugin"
        }
        register("kotlinTest") {
            id = "st.kotlin.test"
            implementationClass = "KotlinTestPlugin"
        }
        register("kotlinHilt") {
            id = "st.kotlin.hilt"
            implementationClass = "KotlinHiltPlugin"
        }

        // Android
        register("androidLibrary") {
            id = "st.android.library"
            implementationClass = "AndroidLibraryPlugin"
        }
        register("uiTest") {
            id = "st.android.test"
            implementationClass = "AndroidTestPlugin"
        }
        register("androidCompose") {
            id = "st.android.compose"
            implementationClass = "AndroidComposePlugin"
        }
        register("androidHilt") {
            id = "st.android.hilt"
            implementationClass = "AndroidHiltPlugin"
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