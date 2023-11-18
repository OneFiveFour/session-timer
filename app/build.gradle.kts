plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-conventions")
    id("test-conventions")
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
    }

    buildTypes {
        debug {
            isDebuggable = true
        }
        release {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        targetCompatibility = JavaVersion.VERSION_17
        sourceCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    coreLibraryDesugaring(libs.desugar)
    implementation(libs.androidX.coreKtx)
    implementation(libs.androidX.appCompat)
    implementation(libs.androidX.constraintLayout)
    implementation(libs.androidX.navigation.fragment)
    implementation(libs.androidX.navigation.ui)
    implementation(libs.google.material)
}