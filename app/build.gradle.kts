plugins {
    alias(libs.plugins.dagger.hilt.android)
    alias(libs.plugins.ksp)
    id("com.android.application")
    id("st.kotlin")
    id("st.ktlint")
    kotlin("android")
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
        kotlinCompilerExtensionVersion = libs.versions.androidxKotlinCompiler.get()
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/*.kotlin_module"
        }
    }
}

dependencies {
    // TODO use loop over list
    implementation(project(":theme"))
    implementation(project(":database"))
    implementation(project(":session-editor"))
    implementation(project(":session-overview"))

    implementation(libs.sqlDelight.android)

    implementation(libs.androidX.activity)
    implementation(libs.androidX.activity.compose)
    implementation(libs.android.material)

    implementation(platform(libs.androidX.compose.bom))
    implementation(libs.androidX.compose.ui)
    implementation(libs.androidX.compose.ui.tooling.preview)
    implementation(libs.androidX.compose.material3)

    implementation(libs.hilt.android)
    ksp(libs.hilt.android.compiler)
}