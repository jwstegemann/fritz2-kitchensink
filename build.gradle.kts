plugins {
    kotlin("multiplatform") version "1.4.10"
}

repositories {
    jcenter()
    maven("https://oss.jfrog.org/artifactory/jfrog-dependencies")
    maven(url = "https://kotlin.bintray.com/kotlinx/") // soon will be just jcenter()
}

kotlin {
    js(LEGACY).browser()
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
                implementation("dev.fritz2:core:0.8-SNAPSHOT")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.1.0")
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
                implementation("dev.fritz2:styling:0.8-SNAPSHOT")
                implementation("dev.fritz2:components:0.8-SNAPSHOT")
                implementation("dev.fritz2:datatable:0.8-SNAPSHOT")
            }
        }
        val jsTest by getting {
            dependencies {
                implementation(kotlin("test-js"))
            }
        }
    }
}
