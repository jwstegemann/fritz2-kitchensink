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

val generatedVersionDir = "${buildDir}/generated-version"

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
                implementation("dev.fritz2:components:0.10-SNAPSHOT")
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
        val versionProperty by creating {
            resources.srcDir(generatedVersionDir)
        }
        val jsMain by getting {
            dependencies {
                dependsOn(versionProperty)
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
    val generateVersionProperties= create("generateVersionProperties") {
        doLast {
            val propertiesFile = file("$generatedVersionDir/version.properties")
            propertiesFile.parentFile.mkdirs()
            properties {
                setProperty("version", rootProject.version.toString())
                renderer.setOutputFile(propertiesFile)
            }
        }
    }
    findByName("jsProcessResources")?.dependsOn(generateVersionProperties)
}