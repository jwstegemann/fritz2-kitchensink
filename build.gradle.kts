plugins {
    id("dev.fritz2.fritz2-gradle") version "0.12"
    kotlin("multiplatform") version "1.5.30"
}

repositories {
    //mavenLocal()
    mavenCentral()
}

val appStatus = "alpha"
val fritz2Version = "0.12"


val propertiesDir = "${buildDir}/properties"

kotlin {
    js(IR) {
        browser()
    }.binaries.executable()

    sourceSets {
        all {
            languageSettings.apply {
                optIn("kotlin.ExperimentalStdlibApi")
                optIn("kotlinx.coroutines.ExperimentalCoroutinesApi")
                optIn("kotlinx.coroutines.InternalCoroutinesApi")
                optIn("kotlinx.coroutines.FlowPreview")
            }
        }

        val commonMain by getting {
            dependencies {
                implementation("dev.fritz2:components:$fritz2Version")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.2.0")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(kotlin("test-common"))
                implementation(kotlin("test-junit"))
                implementation(kotlin("test-annotations-common"))
            }
        }

        val properties by creating {
            resources.srcDir(propertiesDir)
        }

        val jsMain by getting {
            dependencies {
                dependsOn(properties)
            }
        }


        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}

tasks {
    val propertiesTask= create("generateProperties") {
        doLast {
            val propertiesFile = file("$propertiesDir/properties.js")
            propertiesFile.parentFile.mkdirs()
            propertiesFile.writeText("""
                window.document.fritz2Version = '$fritz2Version';
                window.document.appStatus = '$appStatus';
            """.trimIndent())
        }
    }
    findByName("jsProcessResources")?.dependsOn(propertiesTask)
}