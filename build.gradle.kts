plugins {
    id("dev.fritz2.fritz2-gradle") version "0.11"
    kotlin("multiplatform") version "1.5.10"
}

repositories {
    mavenLocal()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://s01.oss.sonatype.org/content/repositories/releases/")
    mavenCentral()
}

val appStatus = "alpha"
val fritz2Version = "0.11"


val propertiesDir = "${buildDir}/properties"

kotlin {
    js(IR) {
        browser()
    }.binaries.executable()

    sourceSets {
        all {
            languageSettings.apply {
                useExperimentalAnnotation("kotlin.ExperimentalStdlibApi")
                useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
                useExperimentalAnnotation("kotlinx.coroutines.InternalCoroutinesApi")
                useExperimentalAnnotation("kotlinx.coroutines.FlowPreview")
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