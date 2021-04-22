package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.RootStore
import dev.fritz2.components.*
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.styling.params.styled
import dev.fritz2.styling.theme.ColorScheme
import dev.fritz2.tracking.tracker
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay

@ExperimentalCoroutinesApi
fun RenderContext.buttonDemo(): Div {

    val modal = modal({
        minHeight { "0" }
    }) {
        size { small }
        content {
            lineUp {
                items {
                    icon({ color { success.main } }) { fromTheme { circleCheck } }
                    p { +"Your data has been saved successfully." }
                }
            }
        }
    }

    val buttonStore = object : RootStore<Unit>(Unit) {
        val loading = tracker()

        val showMsg = handle {
            loading.track("running...") {
                delay(3000)
                modal()
            }
        }
    }

    return contentFrame {

        showcaseHeader("Button")

        paragraph {
            +"""
            Use a button to trigger an action which can then be handled by a store or by another
            component, for example launching a modal dialog. 
            """.trimIndent()
        }

        showcaseSection("Usage")
        paragraph {
            +"Define your button by adding text and/or an icon to its content and setting the color. A "
            c("pushButton")
            +" gives you full control over the underlying HTML-button. The "
            c("clickButton")
            +" exposes the Flow of click-events, so you can conveniently connect it to a handler or to another component."
        }

        componentFrame {
            val successToast = toast {
                content {
                    (::p.styled {
                        color { gray100 }
                        margin { small }
                    }) { +"Your data has been saved successfully." }
                }
            }
            lineUp(switchLayoutSm) {
                items {
                    clickButton {
                        text("Show Toast")
                    } handledBy successToast

                    pushButton {
                        icon { fromTheme { arrowLeft } }
                        text("Previous")
                    }

                    pushButton {
                        type { warning }
                        icon { fromTheme { arrowRight } }
                        iconPlacement { right }
                        text("Next")
                    }

                    pushButton({
                        radius { none }
                    }) {
                        type { danger }
                        icon { fromTheme { close } }
                        events {
                            clicks handledBy successToast
                        }
                    }
                }
            }
        }
        highlight {
            source(
                """
                    clickButton {
                        text("Show Toast")
                    } handledBy toast {
                        content { "Your data has been saved successfully." }
                    }

                    pushButton {
                        color { info }
                        icon { fromTheme { arrowLeft } }
                        text("Previous")
                    }

                    pushButton {
                        color { warning }
                        icon { fromTheme { arrowRight } }
                        iconPlacement { right }
                        text("Next")
                    }

                    pushButton({
                        radius { none }
                    }) {
                        color { danger }
                        icon { fromTheme { close } }
                        events {
                            clicks handledBy toast {
                                content { "Your data has been saved successfully." }
                            }
                        }
                    }
                """
            )
        }

        showcaseSection("Sizes")
        paragraph {
            +"Choose from the three predefined sizes "
            c("small")
            +", "
            c("normal")
            +" (default), or  "
            c("large")
            +", or scale your button to your needs using the styling parameter."
        }
        componentFrame {
            lineUp({
                alignItems { center }
            }) {
                items {
                    clickButton {
                        text("small")
                        size { small }
                    }
                    clickButton {
                        text("normal")
                    }
                    clickButton {
                        text("large")
                        size { large }
                    }
                }
            }
        }
        highlight {
            source(
                """
               clickButton {
                    text("small")
                    size { small }
                }
                clickButton {
                    text("normal")
                }
                clickButton {
                    text("large")
                    size { large }
                }
                """
            )
        }

        showcaseSection("Variants")
        paragraph {
            +"fritz2 offers three different flavors of buttons for various use cases: "
            c("solid")
            +" (default), "
            c("outline")
            +", "
            c("ghost")
            +", and "
            c("link")
            +"."
        }
        componentFrame {
            lineUp {
                items {
                    clickButton {
                        text("solid")
                    }
                    clickButton {
                        text("outline")
                        variant { outline }
                    }
                    clickButton {
                        text("ghost")
                        variant { ghost }
                    }
                    clickButton {
                        text("link")
                        variant { link }
                    }
                }
            }
        }
        highlight {
            source(
                """
                clickButton { 
                    text("solid")
                }
                
                clickButton {
                    text("outline")
                    variant { outline }
                }
                
                clickButton {
                    text("ghost")
                    variant { ghost } 
                }
                
                clickButton {
                    text("link")
                    variant { link } 
                }
                """
            )
        }


        showcaseSection("Loading State")
        paragraph {
            +"Connect a button to a "
            c("Tracker")
            +" to show its loading state. You can specify a loading text to replace the button text during loading."
        }
        componentFrame {
            lineUp(switchLayoutSm) {
                items {
                    clickButton {
                        text("Play")
                        loading(buttonStore.loading.data)
                    } handledBy buttonStore.showMsg

                    clickButton {
                        text("Play")
                        loading(buttonStore.loading.data)
                        loadingText("Playing..")
                        variant { outline }
                    } handledBy buttonStore.showMsg

                    clickButton {
                        icon { fromTheme { play } }
                        text("Play")
                        loading(buttonStore.loading.data)
                    } handledBy buttonStore.showMsg

                    clickButton {
                        icon { fromTheme { play } }
                        variant { ghost }
                        loading(buttonStore.loading.data)
                    } handledBy buttonStore.showMsg
                }
            }
        }
        highlight {
            source(
                """
                val buttonStore = object : RootStore<Unit>(Unit) {
                    val loading = tracker()
            
                    val showMsg = handle {
                        loading.track("running...") {
                            delay(3000)
                            modal()
                        }
                    }
                }

                clickButton {
                    text("Play")
                    loading(buttonStore.loading.data)
                } handledBy buttonStore.showMsg

                clickButton {
                    text("Play")
                    loading(buttonStore.loading.data)
                    loadingText("Playing...")
                    variant { outline }
                } handledBy buttonStore.showMsg

                clickButton {
                    icon { fromTheme { play } }
                    text("Play")
                    loading(buttonStore.loading.data)
                } handledBy buttonStore.showMsg

                clickButton {
                    icon { fromTheme { play } }
                    variant { ghost }
                    loading(buttonStore.loading.data)
                } handledBy buttonStore.showMsg
                """
            )
        }
    }
}