plugins {
    kotlin("multiplatform")
    id("com.android.application")
    id("org.jetbrains.compose")
}

kotlin {
    android()
    sourceSets {
        val androidMain by getting {
            dependencies {
                implementation(project(":shared"))
            }
        }
    }
}

android {
    namespace = "com.coooldoggy.emotionaloutlet"
    compileSdk = 34
    defaultConfig {
        applicationId = "com.coooldoggy.emotionaloutlet"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.5"
    }
    buildFeatures {
        compose = true
    }
}
dependencies {
    implementation("androidx.compose.material3:material3:1.1.1")
}
