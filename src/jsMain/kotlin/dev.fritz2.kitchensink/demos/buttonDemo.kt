package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.RootStore
import dev.fritz2.binding.storeOf
import dev.fritz2.components.*
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.styling.p
import dev.fritz2.styling.theme.ColorScheme
import dev.fritz2.tracking.tracker
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay

@ExperimentalCoroutinesApi
fun RenderContext.buttonDemo(): Div {

    val modal = modal({
        minHeight { "0" }
    }) {
        width { small }
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
                    p({
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
                        icon { arrowLeft }
                        text("Previous")
                        type { info }
                    }

                    pushButton {
                        type { warning }
                        icon { arrowRight }
                        iconPlacement { right }
                        text("Next")
                    }

                    pushButton({
                        radius { none }
                    }) {
                        type { danger }
                        icon { close }
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
                    } handledBy successToast

                    pushButton {
                        icon { arrowLeft }
                        text("Previous")
                        type { info }
                    }

                    pushButton {
                        type { warning }
                        icon { arrowRight }
                        iconPlacement { right }
                        text("Next")
                    }

                    pushButton({
                        radius { none }
                    }) {
                        type { danger }
                        icon { close }
                        events {
                            clicks handledBy successToast
                        }
                    }
                """
            )
        }

        showcaseSection("Types")
        paragraph {
            +"fritz2 predefined following "
            c("colorScheme")
            +" to give your Button a special look."
            br {  }
            c("primary")
            +", "
            c("secondary")
            +", "
            c("info")
            +", "
            c("success")
            +", "
            c("warning")
            +", "
            c("danger")
            br {  }
            +" If you need something else use a different "
            c("colorScheme")
        }
        componentFrame {
            lineUp({
                alignItems { center }
            }) {
                items {
                    clickButton {
                        text("primary")
                        type { primary }
                    }
                    clickButton {
                        text("secondary")
                        type { secondary }
                    }
                    clickButton {
                        text("info")
                        type { info }
                    }
                    clickButton {
                        text("success")
                        type { success }
                    }
                    clickButton {
                        text("warning")
                        type { warning }
                    }
                    clickButton {
                        text("danger")
                        type { danger }
                    }
                    clickButton {
                        text("custom")
                        type { ColorScheme("#00A848","#2D3748","#E14F2A", "#2D3748") }
                    }
                }
            }
        }
        highlight {
            source(
                """
                    clickButton {
                        text("primary")
                        type { primary }
                    }
                    clickButton {
                        text("secondary")
                        type { secondary }
                    }
                    clickButton {
                        text("info")
                        type { info }
                    }
                    clickButton {
                        text("success")
                        type { success }
                    }
                    clickButton {
                        text("warning")
                        type { warning }
                    }
                    clickButton {
                        text("danger")
                        type { danger }
                    }
                    clickButton {
                        text("custom")
                        type { ColorScheme("#00A848","#2D3748","#E14F2A", "#2D3748") }
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
                        icon { play }
                        text("Play")
                        loading(buttonStore.loading.data)
                    } handledBy buttonStore.showMsg

                    clickButton {
                        icon { play }
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
                    icon { play }
                    text("Play")
                    loading(buttonStore.loading.data)
                } handledBy buttonStore.showMsg

                clickButton {
                    icon { play }
                    variant { ghost }
                    loading(buttonStore.loading.data)
                } handledBy buttonStore.showMsg
                """
            )
        }

        showcaseSection("Link")
        paragraph {
            +"There is also a special button for links which uses an "
            c("<a>")
            +" element instead of "
            c("<button>")
            +" element. Besides the "
            c("linkButton")
            +" component offers everything that "
            c("pushButton")
            +" has and adds extra functions to set a "
            c("href")
            +" and a "
            c("target")
            +" attribute directly."
        }
        componentFrame {

            val hrefStore = storeOf("//www.fritz2.dev")
            val targetStore = storeOf("_blank")

            formControl {
                label("Url")
                inputField(value = hrefStore)
            }

            formControl({ margins { bottom { large } } }) {
                label("Target")
                selectField(items = listOf("_blank", "_self"),value = targetStore)
            }

            lineUp {
                items {
                    linkButton {
                        text("Open")
                        href(hrefStore.data)
                        target(targetStore.data)

                    }
                    linkButton {
                        text("Open")
                        icon { externalLink }
                        href(hrefStore.data)
                        target(targetStore.data)
                    }
                    linkButton {
                        text("Open")
                        variant { link }
                        href(hrefStore.data)
                        target(targetStore.data)
                    }
                    linkButton {
                        icon { externalLink }
                        variant { link }
                        href(hrefStore.data)
                        target(targetStore.data)
                    }
                }
            }
        }
        highlight {
            source(
                """
                linkButton {
                    text("Open")
                    href(hrefStore.data)
                    target(targetStore.data)

                }
                
                linkButton {
                    text("Open")
                    icon { externalLink }
                    href(hrefStore.data)
                    target(targetStore.data)
                }
                
                linkButton {
                    text("Open")
                    variant { link }
                    href(hrefStore.data)
                    target(targetStore.data)
                }
                
                linkButton {
                    icon { externalLink }
                    variant { link }
                    href(hrefStore.data)
                    target(targetStore.data)
                }
                """
            )
        }
    }
}