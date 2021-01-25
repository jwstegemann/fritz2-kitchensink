package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.SimpleHandler
import dev.fritz2.components.*
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.styling.params.BackgroundBlendModes.normal
import dev.fritz2.styling.params.BasicParams
import dev.fritz2.styling.params.Style
import dev.fritz2.styling.params.styled
import dev.fritz2.styling.theme.Theme
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
fun RenderContext.modalDemo(): Div {

    // Call this once if you don't need to dynamically change overlay.
    //ModalComponent.setOverlayHandler(DefaultOverlay(OverlayMethod.CoveringEach))

    return contentFrame {
        fun createDeepDialogs(count: Int, size: Style<BasicParams>): SimpleHandler<Unit> {
            if (count < 2) {
                return modal {
                    size { size }
                    content {
                        h1 { +"Final Dialog" }
                    }
                }
            } else {
                return modal({
//                    background { color { base } }
                }) {
                    size { size }
                    variant { auto }
                    closeButton()
                    content {
                        h1 { +"Modal Dialog" }
                        paragraph { +"Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet." }
                        lineUp({
                            margins { top { normal } }
                            justifyContent { end }
                        }) {
                            spacing { small }
                            items {
                                clickButton {
                                    text("Open Another Modal")
                                } handledBy createDeepDialogs(count - 1, size)
                            }
                        }
                    }
                }
            }
        }

        showcaseHeader("Modal Dialogs")
        paragraph {
            +"A modal dialog is a pop up window which opens as a new layer on screen. It demands the user's attention because"
            +" the application below is not accessible until the dialog is closed. "
        }

        showcaseSection("Usage")
        paragraph {
            +"The fritz2 default modal can be opened with a simple clickButton - just connect it to the button's click event."
        }
        componentFrame {
            lineUp {
                items {
                    clickButton {
                        text("Default Modal Dialog")
                    } handledBy modal {
                        content {
                            p { +"You can put any HTML element, component or structure into a modal." }
                        }
                    }
                }
            }
        }
        playground {
            source(
                """
                clickButton {
                    text("Default Modal Dialog")
                } handledBy modal {
                    content {
                        p { +"You can put any HTML element, component or structure into a modal." }
                    }
                }
                """.trimIndent()
            )
        }

        showcaseSection("Sizes")
        paragraph {
            +"There are four predefined sizes for your modal to choose from: "
            c("small")
            +", "
            c("normal")
            +" (default), "
            c("large")
            +", and "
            c("full")
            +". The additional option "
            c("variant { verticalFilled }")
            +" creates a modal of your chosen size which uses the entire height of the viewport."
        }
        componentFrame {
            lineUp({
                alignItems { start }
            }) {
                items {
                    clickButton {
                        text("Small")
                    } handledBy createDeepDialogs(30, Theme().modal.sizes.small)
                    clickButton {
                        text("Normal")
                    } handledBy createDeepDialogs(30, Theme().modal.sizes.normal)
                    clickButton {
                        text("Large")
                    } handledBy createDeepDialogs(30, Theme().modal.sizes.large)
                    clickButton {
                        text("Full")
                    } handledBy createDeepDialogs(30, Theme().modal.sizes.full)
                }
            }
        }

        playground {
            source(
                """
                clickButton {
                    text("full")
                } handledBy modal {
                    size { full }
                }
            """.trimIndent()
            )
        }

        paragraph {
            +"In addition to the "
            c("size")
            +" option, fritz2 offers the variant function "
            c("variant { verticalFilled }")
            +" for creating a modal which uses the entire height of the viewport."
        }
        componentFrame {

            // todo next row for space issues
            clickButton {
                text("Small, VerticalFilled Modal")
            } handledBy modal {
                variant { verticalFilled }
                size { small }
                content {
                    p { +"This small modal takes all vertical space within the viewport." }
                }
            }

        }

        playground {
            source(
                """
                clickButton {
                    text("Small, VerticalFilled Modal")
                } handledBy modal {
                    variant { verticalFilled }
                    size { small }
                    content {
                        p { +"This small modal takes all vertical space within the viewport." }
                    }
                }
                """.trimIndent()
            )
        }

        showcaseSection("Layered Modals")
        paragraph { +"Modals can be stacked over one another with only the topmost modal accepting input." }
        componentFrame {
            lineUp {
                items {
                    clickButton { text("Stack'em") } handledBy modal {
                        content {
                            h1 { +"Level 1" }
                            paragraph(
                                { paddings { bottom { larger } } }
                            ) { +"You can stack as many modals as you like." }
                            clickButton { text("Open Next Level") } handledBy modal {
                                content {
                                    h1 { +"Level 2" }
                                    paragraph(
                                        { paddings { bottom { larger } } }
                                    ) { +"But just because you can does not mean you should." }
                                    clickButton { text("Open Final Level") } handledBy modal {
                                        content {
                                            h1 { +"The End" }
                                            paragraph { +"This is the final dialog in our example." }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        playground {
            source(
                """
                clickButton { text("Stack'em") } handledBy modal {
                    content {
                    
                        h1 { +"Level 1" }
                        paragraph { +"You can stack as many modals as you like." }
                        clickButton { text("Open Next Level") } handledBy modal {
                            content {
                            
                                h1 { +"Level 2" }
                                paragraph { +"But just because you can does not mean you should." }
                                clickButton { text("Open Final Level") } handledBy modal {
                                    content {
                                    
                                        h1 { +"The End" }
                                        paragraph { +"This is the final dialog in our example." }
                                    }
                                }
                            }
                        }
                    }
                }
            """.trimIndent()
            )
        }

        showcaseSection("Customized Close Buttons")
        paragraph {
            +"You can customize the close button calling the modal's function "
            c("closeButton")
            +" with your own styling parameters:"
        }
        componentFrame {
            lineUp {
                items {
                    clickButton {
                        text("Styled Close Button")
                    } handledBy modal {
                        closeButton({
                            background { color { danger } }
                            color { base }
                            position {
                                absolute {
                                    top { normal }
                                }
                            }
                            css("transform: rotate(20deg) translateX(-.5rem)")
                        }) {
                            size { small }
                            text("Nope")
                            iconRight()
                        }
                    }
                }
            }
        }

        playground {
            source(
                """
                clickButton {
                    text("Styled Close Button")
                } handledBy modal {
                    closeButton({
                        background { color { danger } }
                        color { base }
                        position {
                            absolute {
                                top { normal }
                            }
                        }
                        css("transform: rotate(20deg) translateX(-.5rem)")
                    }) {
                        size { small }
                        text("Nope")
                        iconRight()
                    }
                }
            """.trimIndent()
            )
        }

        paragraph {
            +"You can also define your own close button. Use the modal's context to add your new button to the "
            c("content")
            +" area. Unless you want to have two close buttons, remember to remove the default button as well, using "
            c("hasCloseButton(false)")
            +" (this only removes the built-in close button). The modal provides a close handler"
            +" to connect to your button events."
        }
        componentFrame {
            lineUp {
                items {
                    clickButton {
                        text("Custom Close Button")
                    } handledBy modal { close ->
                        hasCloseButton(false)
                        content {
                            clickButton({
                                color { base }
                                background { color { warning } }
                            }) {
                                icon { fromTheme { logOut } }
                            } handledBy close
                        }
                    }
                }
            }
        }
        playground {
            source(
                """
                clickButton {
                    text("Custom Close Button")
                } handledBy modal { close -> // use the close handler provided by the modal
                    hasCloseButton(false)
                    content {
                        clickButton({
                            color { base }
                            background { color { warning } }
                        }) {
                            icon { fromTheme { logOut } }
                        } handledBy close
                    }
                }
                """.trimIndent()
            )
        }

        // todo this should have a code example
        showcaseSection("Overlay Variants")
        paragraph {
            +"You can decide what happens in the background when a modal opens. By default, the background receives an"
            +" overlay. Another option is to add another overlay for each level of dialog opened, or to use a single,"
            +" styled overlay. Select one of the variants below and try our stack example again to see the difference."
        }
        componentFrame {
            val overlayVariants = mapOf(
                Pair("Default Overlay", DefaultOverlay()),
                Pair("Overlay For Each Level", DefaultOverlay(OverlayMethod.CoveringEach)),
                Pair("Styled Overlay", DefaultOverlay(OverlayMethod.CoveringTopMost) {
                    width { "100%" }
                    height { "400%" }
                    position {
                        absolute {
                            horizontal { "0" }
                            vertical { "0" }
                        }
                    }
                    background {
                        image { "https://via.placeholder.com/150x50/?text=fritz2" }
                        repeat { repeat }
                    }
                    opacity { "0.8" }
                })
            )

            stackUp {
                items {
                    // todo this radio group should have default selected
                    radioGroup(store = ModalComponent.overlay) {
                        direction { row }
                        label { overlay ->
                            overlayVariants.filter { it.value == overlay }.map {
                                it.key
                            }[0]
                        }
                        items(overlayVariants.values.toList())

                    }

                    clickButton { text("Stack'em") } handledBy modal {
                        content {
                            paragraph(
                                { paddings { bottom { larger } } }
                            ) { +"Overlay Demonstration Layer 1" }
                            clickButton { text("Open Another Modal") } handledBy modal {
                                content {
                                    paragraph { +"Overlay Demonstration Layer 2" }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}