package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.RootStore
import dev.fritz2.binding.invoke
import dev.fritz2.binding.watch
import dev.fritz2.components.*
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.tracking.tracker
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay

@ExperimentalCoroutinesApi
fun RenderContext.buttonDemo(): Div {

    val modal = modal({
        minHeight { "0" }
    }) {
        size { small }
        closeButton()
        content {
            lineUp {
                items {
                    icon({ color { success } }) { fromTheme { circleCheck } }
                    p { +"Your data has been saved successfully." }
                }
            }
        }
    }

    val buttonStore = object : RootStore<Int>(0) {
        val loading = tracker()

        val showMsg = handle { model ->
            loading.track("running...") {
                delay(3000)
                modal()
            }
            model
        }
    }
    buttonStore.watch()

    return contentFrame {

        showcaseHeader("Button")

        paragraph {
            +"""
            Use a button to trigger an action which can then be handled by a store or by another
            component, for example launching a modal dialog. 
            """.trimIndent()
        }

        // todo: create extra event handling example, because this one is already packed and is still missing the pushButton events
        showcaseSection("Usage")
        paragraph {
            +"Define your button by adding text and/or an icon to its content and setting the color. A "
            c("pushButton")
            +" gives you full control over the underlying HTML-button. The "
            c("clickButton")
            +" exposes the Flow of click-events, so you can conveniently connect it to a handler or to another component."
        }

        componentFrame {
            lineUp(switchLayoutSm) {
                items {
                    clickButton { text("Show Modal") } handledBy modal

                    pushButton ({
                        background { color { primaryEffect }}
                    }) {
                        icon { fromTheme { arrowLeft } }
                        text("Previous")
                    }

                    pushButton ({
                        background { color { secondary }}
                    }) {
                        icon { fromTheme { arrowRight } }
                        iconRight()
                        text("Next")
                    }

                    pushButton({
                        background { color { secondaryEffect } }
                    }) {
                        icon { fromTheme { check } }
                        events {
                            clicks handledBy modal
                        }
                    }
                }
            }
        }
        playground {
            source(
                """
                    clickButton { text("Show Modal") } handledBy modal

                    pushButton ({
                        background { color { primaryEffect }}
                    }) {
                        icon { fromTheme { arrowLeft } }
                        text("Previous")
                    }

                    pushButton ({
                        background { color { secondary }}
                    }) {
                        icon { fromTheme { arrowRight } }
                        iconRight()
                        text("Next")
                    }

                    pushButton({
                        background { color { secondaryEffect } }
                    }) {
                        icon { fromTheme { check } }
                        events {
                            clicks handledBy modal
                        }
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
        playground {
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
        playground {
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
                        loading(buttonStore.loading)
                    } handledBy buttonStore.showMsg

                    clickButton {
                        text("Play")
                        loading(buttonStore.loading)
                        loadingText("Playing..")
                        variant { outline }
                    } handledBy buttonStore.showMsg

                    clickButton {
                        icon { fromTheme { play } }
                        text("Play")
                        loading(buttonStore.loading)
                    } handledBy buttonStore.showMsg

                    clickButton {
                        icon { fromTheme { play } }
                        variant { ghost }
                        loading(buttonStore.loading)
                    } handledBy buttonStore.showMsg
                }
            }
        }
        playground {
            source(
                """
                    val buttonStore = object : RootStore<Int>(0) {
                        val loading = tracker()
                    
                        val showMsg = handle { model ->
                            loading.track("running...") {
                                delay(3000)
                                modal()
                            }
                            model
                        }
                    }

                    clickButton { text("Play") } handledBy buttonStore.showMsg

                    clickButton {
                        icon { fromTheme { play } }
                        text("Play")
                        loadingText("Playing..")
                    } handledBy buttonStore.showMsg

                    clickButton {
                        icon { fromTheme { play } }
                        loading(buttonStore.loading)
                        variant { outline }
                    } handledBy buttonStore.showMsg
                """
            )
        }
    }
}