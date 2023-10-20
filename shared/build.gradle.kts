plugins {
    kotlin("multiplatform")
    id("org.jetbrains.compose")
    id("com.github.gmazzo.buildconfig")
}

version = "1.0.0"

kotlin {
    // IOS
    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries {
            framework {
                baseName = "AmadeusDemoKt"
                isStatic = true
            }
        }
    }

    // JS
    js(IR) {
        browser()
    }

    // JVM
    jvm()

    sourceSets {
        val commonMain by getting {
            dependencies {
                val ktorVersion = "2.3.5"
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.ui)
                implementation(compose.material3)
                implementation(project(":amadeus-api"))
                implementation("io.github.pablichjenkov:component-toolkit:0.5.10-rc02")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
                implementation("org.jetbrains.compose.components:components-resources:1.5.3")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.1")
                implementation("io.insert-koin:koin-core:3.5.0")

                // ktor
                implementation("io.ktor:ktor-client-core:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.5")
                implementation("io.ktor:ktor-client-content-negotiation:$ktorVersion")
                implementation("io.ktor:ktor-serialization-kotlinx-json:$ktorVersion")
                implementation("io.ktor:ktor-client-logging:$ktorVersion")
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation("io.insert-koin:koin-test:3.5.0")
            }
        }
        // IOS
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
        val iosArm64Test by getting
        val iosSimulatorArm64Test by getting
        val iosTest by creating {
            dependsOn(commonTest)
            iosArm64Test.dependsOn(this)
            iosSimulatorArm64Test.dependsOn(this)
        }
    }

}

buildConfig {
    useKotlinOutput { internalVisibility = true }
    packageName("com.pablichj.incubator.amadeus.demo")

    val amadeusApiKey = extra["amadeus.apiKey"] as String
    require(amadeusApiKey.isNotEmpty()) {
        "Register your api key from amadeus and place it in local.properties as `amadeus.apiKey`"
    }

    val amadeusApiSecret = extra["amadeus.apiSecret"] as String
    require(amadeusApiKey.isNotEmpty()) {
        "Register your api secret from amadeus and place it in local.properties as `amadeus.apiSecret`"
    }

    buildConfigField(
        "String",
        "AMADEUS_API_KEY", amadeusApiKey
    )

    buildConfigField(
        "String",
        "AMADEUS_API_SECRET", amadeusApiSecret
    )

}