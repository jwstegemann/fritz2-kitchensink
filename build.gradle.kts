plugins {
    id("dev.fritz2.fritz2-gradle") version "0.9"
    kotlin("multiplatform") version "1.4.30"
}

repositories {
    mavenLocal()
    mavenCentral()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven(url = "https://kotlin.bintray.com/kotlinx/") // soon will be just jcenter()
}

val fritz2Version = "0.10-SNAPSHOT"


val propertiesDir = "${buildDir}/properties"

kotlin {
    js(IR) {
        browser()
    }.binaries.executable()

    sourceSets {
        all {
            languageSettings.apply {
                enableLanguageFeature("InlineClasses") // language feature name
                useExperimentalAnnotation("kotlin.ExperimentalUnsignedTypes") // annotation FQ-name
                useExperimentalAnnotation("kotlin.ExperimentalStdlibApi")
                useExperimentalAnnotation("kotlinx.coroutines.ExperimentalCoroutinesApi")
                useExperimentalAnnotation("kotlinx.coroutines.InternalCoroutinesApi")
                useExperimentalAnnotation("kotlinx.coroutines.FlowPreview")
            }
        }

        val commonMain by getting {
            dependencies {
                implementation("dev.fritz2:components:$fritz2Version")
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
    val propertiesTask= create("generateVersionProperties") {
        doLast {
            val propertiesFile = file("$propertiesDir/properties.js")
            propertiesFile.parentFile.mkdirs()
            propertiesFile.writeText("""
                window.document.fritz2Version = '$fritz2Version';
            """.trimIndent())
        }
    }
    findByName("jsProcessResources")?.dependsOn(propertiesTask)
}