plugins {
    kotlin("multiplatform") version "1.6.10"
    // KSP support
    id("com.google.devtools.ksp") version "1.6.10-1.0.2"}

repositories {
    mavenLocal()
    maven("https://s01.oss.sonatype.org/content/repositories/snapshots/")
    maven("https://s01.oss.sonatype.org/content/repositories/releases/")
    mavenCentral()
}

val appStatus = "discontinued"
val fritz2Version = "0.14.1"


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

/**
 * KSP support - start
 */
dependencies {
    add("kspMetadata", "dev.fritz2:lenses-annotation-processor:$fritz2Version")
}
kotlin.sourceSets.commonMain { kotlin.srcDir("build/generated/ksp/commonMain/kotlin") }
tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinCompile<*>>().all {
    if (name != "kspKotlinMetadata") dependsOn("kspKotlinMetadata")
}
// needed to work on Apple Silicon. Should be fixed by 1.6.20 (https://youtrack.jetbrains.com/issue/KT-49109#focus=Comments-27-5259190.0-0)
rootProject.plugins.withType<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootPlugin> {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension>().nodeVersion = "16.0.0"
}
/**
 * KSP support - end
 */