plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.dagger.hilt.android) apply false
    alias(libs.plugins.ktLint) apply false
    id("org.jetbrains.kotlinx.kover") version "0.7.5"
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}

kover {
    useJacoco()
}

