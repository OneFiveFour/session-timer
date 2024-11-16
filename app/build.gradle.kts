import java.util.Properties

plugins {
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlin.serialization)
    id("com.android.application")
    id("st.kotlin")
    id("st.ktlint")
    kotlin("android")
}

val signingProperties = Properties()
val signingFile = file("keystore/sessiontimer.properties")
if (signingFile.exists()) {
    signingProperties.load(signingFile.inputStream())
}

android {
    compileSdk = AppConfig.compileSdk
    namespace = AppConfig.applicationId

    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName
        vectorDrawables.useSupportLibrary = true
    }

    signingConfigs {
        create("signing") {
            enableV3Signing = true
            enableV4Signing = true
            storeFile = file(signingProperties.getProperty("signing.storeFilePath"))
            storePassword = signingProperties.getProperty("signing.storePassword")
            keyAlias = signingProperties.getProperty("signing.keyAlias")
            keyPassword = signingProperties.getProperty("signing.keyPassword")
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
        }
        release {
            signingConfig = signingConfigs.getByName("signing")
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_17
        sourceCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        buildConfig = true
        compose = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/*.kotlin_module"
        }
    }
}

dependencies {

    val modules = listOf(
        ":core:common",
        ":core:database",
        ":core:di",
        ":core:theme",
        ":core:timer-api",
        ":core:timer",
        ":core:usecases",
        ":feature:session-editor",
        ":feature:session-editor-api",
        ":feature:session-overview",
        ":feature:session-overview-api",
        ":feature:session-player",
        ":feature:session-player-api",
        ":feature:taskgroup-editor",
        ":feature:taskgroup-editor-api"
    )
    for (module in modules) {
        implementation(project(module))
    }

    // Database
    implementation(libs.sqlDelight.android)

    // Android
    implementation(libs.androidX.activity)
    implementation(libs.androidX.activity.compose)
    implementation(libs.android.material)

    // Compose
    implementation(platform(libs.androidX.compose.bom))
    implementation(libs.androidX.compose.ui)
    implementation(libs.androidX.compose.ui.tooling.preview)
    implementation(libs.androidX.compose.material3)

    // DI
    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)

    // Navigation
    implementation(libs.androidX.navigation)
    implementation(libs.kotlinx.serialization.json)
}


