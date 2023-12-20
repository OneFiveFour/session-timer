plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.dagger.hilt.android) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.ktLint) apply false
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}



