plugins {
    kotlin("multiplatform")
    id("com.android.application")
    id("org.jetbrains.compose")
}

kotlin {
    androidTarget()
    sourceSets {
        androidMain.dependencies {
            implementation(project(":shared"))
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.material3)
            implementation("androidx.activity:activity-compose:1.8.1")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
            implementation("io.insert-koin:koin-core:3.5.0")

            // Third Party
            implementation("io.github.pablichjenkov:amadeus-api:0.3.4")
            implementation("io.github.pablichjenkov:component-toolkit:0.5.10")
        }
    }
}

android {
    namespace = "com.macaosoftware.sdui.app"
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        applicationId = "com.macaosoftware.sdui.app"
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
        versionCode = 1
        versionName = "1.0"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    packaging {
        resources {
            // excludes += "/META-INF/{AL2.0,LGPL2.1}"
            pickFirsts.apply {
                add("META-INF/kotlinx_coroutines_core.version")
                add("META-INF/INDEX.LIST")
                add("META-INF/versions/9/previous-compilation-data.bin")
            }
        }
    }
}
