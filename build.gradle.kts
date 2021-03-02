plugins {
    id("dev.fritz2.fritz2-gradle") version "0.9"
    kotlin("multiplatform") version "1.4.10"
}

repositories {
    //mavenLocal()
    jcenter()
    maven("https://dl.bintray.com/jwstegemann/fritz2")
    maven(url = "https://kotlin.bintray.com/kotlinx/") // soon will be just jcenter()
}

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
                implementation("dev.fritz2:components:0.9")
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
        val jsMain by getting {
            dependencies {
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}
