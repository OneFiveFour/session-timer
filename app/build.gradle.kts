plugins {
    id("com.android.application")
    kotlin("android")
    id("st.kotlin")
    id("st.test")
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
        vectorDrawables {
            useSupportLibrary = true
        }
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
        compose = true
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        // TODO reference version of version catalog
        kotlinCompilerExtensionVersion = "1.5.4"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidX.coreKtx)
    implementation(libs.androidX.appCompat)
    implementation(libs.androidX.constraintLayout)
    implementation(libs.androidX.navigation.fragment)
    implementation(libs.androidX.navigation.ui)

    implementation(libs.google.material)

    implementation(libs.lifecycle.runtime.ktx)

    implementation(platform(libs.androidX.compose.bom))
    implementation(libs.androidX.activity.compose)
    implementation(libs.androidX.compose.uiToolingPreview)
    implementation(libs.androidX.compose.material3)
    implementation(libs.androidX.compose.ui)
    implementation(libs.androidX.compose.ui.graphics)

    androidTestImplementation(platform(libs.androidX.compose.bom))
    androidTestImplementation(libs.androidX.compose.ui.test.junit4)

    debugImplementation(libs.androidX.compose.uiTooling)
    debugImplementation(libs.androidX.compose.ui.test.manifest)
}