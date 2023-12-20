plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.dagger.hilt.android) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.kover)
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.ktLint) apply false
}

subprojects {

    apply {
        plugin(rootProject.libs.plugins.kover.get().pluginId)
    }

    pluginManager.withPlugin(rootProject.libs.plugins.kover.get().pluginId) {
        pluginManager.withPlugin(rootProject.libs.plugins.androidLibrary.get().pluginId) {
            koverReport {
                defaults {
                    mergeWith("debug")
                }
            }
        }

        pluginManager.withPlugin(rootProject.libs.plugins.androidApplication.get().pluginId) {
            koverReport {
                defaults {
                    mergeWith("debug")
                }
            }
        }
    }

}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}



