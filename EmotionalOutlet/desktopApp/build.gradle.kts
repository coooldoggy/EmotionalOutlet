plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
}

kotlin {
    jvm {
        val main by compilations.getting {
            kotlinOptions {
                jvmTarget = "17"
            }
        }
    }
    sourceSets {
        val jvmMain by getting {
            dependencies {
                implementation(project(":shared"))
                implementation(compose.desktop.currentOs)
                implementation("io.github.xxfast:decompose-router:0.3.0")
                implementation("com.arkivanov.decompose:decompose:0.3.0")
                implementation("com.arkivanov.decompose:extensions-compose-jetbrains:0.3.0")
                implementation("com.arkivanov.essenty:parcelable:0.3.0")
                implementation("androidx.compose.material3:material3-window-size-class:1.1.1")
            }
        }
    }
}

compose.desktop {
    application {
        mainClass = "MainKt"
    }
}
