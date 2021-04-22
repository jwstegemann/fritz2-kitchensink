package dev.fritz2.kitchensink.demos

import dev.fritz2.components.clickButton
import dev.fritz2.components.modal
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.kitchensink.versionNumber
import dev.fritz2.kitchensink.versionStatus
import dev.fritz2.styling.theme.Theme
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
fun RenderContext.gettingStarted(): Div {

    return contentFrame {

        showcaseHeader("Getting Started")
        coloredBox(Theme().colors.warning) {
            +"This is a $versionStatus version of fritz2 components."
        }

        paragraph {
            +"You can use fritz2 components in any "
            externalLink("Kotlin JS", "https://kotlinlang.org/docs/reference/js-project-setup.html")
            +" project or "
            externalLink("Kotlin Multiplatform", "https://kotlinlang.org/docs/reference/mpp-intro.html")
            +" project."
        }

        showcaseSection("Installation")
        paragraph {
            +"To learn how to create a new fritz2 Multiplatform project, take a look at the "
            externalLink("fritz2 project setup docs", "https://docs.fritz2.dev/ProjectSetup.html")
        }
        paragraph {
            +"In order to use this component library add the following lines to your "
            c("build.gradle.kts")
            +" file in the "
            c("sourceSets")
            +" section:"
        }
        highlight {
            source(
                """
                kotlin {
                    // ...           
                    sourceSets {
                        val jsMain by getting {
                            dependencies {
                               implementation("dev.fritz2:components:$versionNumber")
                            }
                        }
                    }
                }
                """
            )
        }

        paragraph {
            +"Now you are ready to use the components like the following code "
            c("app.kt")
            +" shows:"
        }
        componentFrame {
            clickButton {
                text("Tell me about fritz2")
            } handledBy modal {
                content {
                    h1 { +"fritz2 components are awesome!" }
                }
            }
        }
        highlight {
            source(
                """
                fun main() {                
                    render {
                        clickButton {
                            text("Tell me about fritz2")
                        } handledBy modal {
                            content {
                                h1 { +"fritz2 components are awesome!" }
                            }
                        }
                    }
                }
                """
            )
        }

        showcaseSection("Pre-release versions")
        paragraph {
            +"To get the latest pre-release version of fritz2 components, take a look at the "
            externalLink("setup docs", "https://docs.fritz2.dev/ProjectSetup.html#pre-release-builds")
            +"."
        }
    }
}



