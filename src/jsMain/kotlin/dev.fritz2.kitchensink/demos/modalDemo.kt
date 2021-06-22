package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.SimpleHandler
import dev.fritz2.binding.storeOf
import dev.fritz2.components.*
import dev.fritz2.components.datatable.ColumnsContext
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.styling.params.FlexParams
import dev.fritz2.styling.theme.Property
import dev.fritz2.styling.*
import dev.fritz2.styling.params.Shadow
import dev.fritz2.styling.params.ShadowProperty
import dev.fritz2.styling.theme.Shadows
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map


@ExperimentalCoroutinesApi
fun RenderContext.modalDemo(): Div {

    val loremIpsum = """Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy  
                        |tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. 
                        |At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd 
                        |gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. 
                        |Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy 
                        |eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam 
                        |voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet 
                        |clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit 
                        |amet.""".trimMargin()

    // Call this once if you don't need to dynamically change overlay.
    //ModalComponent.setOverlayHandler(DefaultOverlay(OverlayMethod.CoveringEach))

    return contentFrame {
        fun createDeepDialogs(count: Int, width: Property): SimpleHandler<Unit> {
            if (count < 2) {
                return modal {
                    width { width }
                    content {
                        h1 { +"Final Dialog" }
                    }
                }
            } else {
                return modal {
                    width { width }
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
                                } handledBy createDeepDialogs(count - 1, width)
                            }
                        }
                    }
                }
            }
        }

        showcaseHeader("Modal Dialog")
        paragraph {
            +"A modal dialog is a pop up window which opens as a new layer on screen. It demands the user's attention "
            +"because the application below is not accessible until the dialog is closed. "
        }

        showcaseSection("Usage")
        paragraph {
            +"The fritz2 default modal can be opened with a simple clickButton - just connect it to the button's click "
            +"event and add some arbitrary "
            c("content")
            +". Without content the modal is just a blank layer."
        }
        componentFrame {
            lineUp {
                items {
                    clickButton {
                        text("Open Dialog")
                    } handledBy modal {
                        content {
                            p { +"You can put any HTML element or component into a modal." }
                        }
                    }

                    clickButton {
                        text("Blank Dialog")
                    } handledBy modal { }

                    clickButton {
                        text("Static Height")
                    } handledBy modal({
                        minHeight { "30rem" }
                    }) { }
                }
            }
        }
        highlight {
            source(
                """
                clickButton {
                    text("Open Dialog")
                } handledBy modal {
                    content {
                        p { +"You can put any HTML element or component into a modal." }
                    }
                }

                clickButton {
                    text("Open Default Dialog")
                } handledBy modal { }
                
                clickButton {
                    text("Static Height")
                } handledBy modal({
                    // use styling parameter to set a height
                    minHeight { "30rem" }
                }) { }                
                """
            )
        }

        paragraph {
            +"The height of the modal fits to the content, so it will grow if the content gets longer."
        }
        componentFrame {
            val increaser = storeOf(1)

            clickButton {
                text("Growing Content")
            } handledBy modal {
                content {
                    stackUp {
                        items {
                            clickButton {
                                text(increaser.data.map { if (it == 1) "Increase" else "Decrease" })
                            }.map { if (increaser.current == 1) 3 else 1 } handledBy increaser.update
                            increaser.data.render {
                                repeat(it) {
                                    p {
                                        +loremIpsum
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        showcaseSection("Width")
        paragraph {
            +"There are four predefined widths for your modal to choose from: "
            c("small")
            +", "
            c("normal")
            +" (default), "
            c("large")
            +", and "
            c("full")
            +"."
        }
        paragraph {
            +"But you can also pass an arbitrary sizing value if this fits your needs better (try "
            c("380px")
            +", "
            c("20rem")
            +", "
            c("90vw")
            +", "
            c("20%")
            +" and so on"
            +")"
        }
        componentFrame {
            stackUp {
                items {

                    lineUp({
                        alignItems { start }
                    }) {
                        items {
                            clickButton {
                                text("Small")
                            } handledBy createDeepDialogs(30, ModalComponent.WidthContext.small)
                            clickButton {
                                text("Normal")
                            } handledBy createDeepDialogs(30, ModalComponent.WidthContext.normal)
                            clickButton {
                                text("Large")
                            } handledBy createDeepDialogs(30, ModalComponent.WidthContext.large)
                            clickButton {
                                text("Full")
                            } handledBy createDeepDialogs(30, ModalComponent.WidthContext.full)
                        }
                    }
                    formControl {
                        val widthStore = storeOf("380px")
                        label("Custom width:")
                        inputField(value = widthStore) {
                            events {
                                blurs.events.map { } handledBy modal({
                                    this as FlexParams
                                    display { flex }
                                    alignItems { center }
                                    justifyContent { center }
                                }) {
                                    width { widthStore.current }
                                    content {
                                        h1 { +"width: ${widthStore.current}" }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        highlight {
            source(
                """
                clickButton {
                    text("full")
                } handledBy modal {
                    width { full }
                }
            """.trimIndent()
            )
        }

        showcaseSection("Vertical Placement")
        paragraph {
            +"In order to place the modal along the vertical axis, the "
            c("placement")
            +" option can be used. The following values are available: "
            c("top")
            +" (default), "
            c("center")
            +", "
            c("bottom")
            +", and "
            c("stretch")
            +"."
        }
        componentFrame {
            lineUp {
                items {
                    clickButton {
                        text("Top")
                    } handledBy modal {
                        width { small }
                        placement { top }
                        content {
                            p { +loremIpsum }
                        }
                    }

                    clickButton {
                        text("Center")
                    } handledBy modal {
                        width { small }
                        placement { center }
                        content {
                            p { +loremIpsum }
                        }
                    }

                    clickButton {
                        text("Bottom")
                    } handledBy modal {
                        width { small }
                        placement { bottom }
                        content {
                            p { +loremIpsum }
                        }
                    }
                }
            }

        }
        highlight {
            source(
                """
                clickButton {
                    text("Center")
                } handledBy modal {
                    placement { center }
                    content {
                        p { +"Some content..." }
                    }
                }
                """.trimIndent()
            )
        }

        paragraph {
            +"The "
            c("stretch")
            +" option enlarges the dialog vertically from top to bottom independently from the content."
        }
        componentFrame {
            clickButton {
                text("Stretch")
            } handledBy modal {
                width { small }
                placement { stretch }
                content {
                    p { +loremIpsum }
                }
            }
        }
        paragraph {
            +"This is extremely useful if there are different contents within the modal depending on user actions. "
            +" With a "
            em { +"stretched" }
            +" modal, there will be no flickering on the vertical axis, as the size stays the same"
        }
        componentFrame {
            val stretched = storeOf(true)

            data class Step(val heading: String, val content: String)

            val steps = listOf(
                Step("Step 1", loremIpsum),
                Step("Step 2", loremIpsum.repeat(5)),
                Step("Step 3", loremIpsum.repeat(3)),
            )
            val stepStore = storeOf(steps[0])

            lineUp({
                alignItems { center }
            }) {
                items {
                    switch(value = stretched) { label(stretched.data.map { if (it) "stretch" else "top" }) }
                    clickButton {
                        text("Dynamic Content")
                    } handledBy modal {
                        width { large }
                        placement { if (stretched.current) stretch else top }
                        content {
                            stackUp {
                                items {
                                    stepStore.data.render { currentStep ->
                                        h1 { +currentStep.heading }
                                        p { +currentStep.content }
                                        clickButton { text("Next Step") }.map {
                                            steps[(steps.indexOf(currentStep) + 1) % steps.size]
                                        } handledBy stepStore.update
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        showcaseSection("Scrolling")
        paragraph {
            +"If the content is longer than the screen size, vertical scrolling gets enabled automatically. "
            +"It depends on the vertical placement, whether the scrolling will be handled outside or inside of "
            +"the modal dialog:"
            ul {
                li {
                    +"If the modal is placed "
                    c("top")
                    +" then the scrolling is handled outside per default."
                }
                li {
                    +"For any other option the scrolling will be handled inside of the dialog. "
                    +"If the content of the dialog is more complex - think of a layout with header, content and "
                    +"footer - just place an "
                    c("overflow { auto }")
                    +" style inside the content container to delegate the scrolling into this element."
                }
            }
        }
        componentFrame {
            lineUp {
                items {
                    clickButton { text("External") } handledBy modal {
                        // placement { top } is default
                        content {
                            repeat(30, { p { +loremIpsum } })
                        }
                    }

                    clickButton { text("Internal") } handledBy modal {
                        placement { center }
                        content {
                            repeat(30, { p { +loremIpsum } })
                        }
                    }

                    val fixedBox: RenderContext.(String, Shadows.() -> Property) -> Unit = { title, shadow ->
                        flexBox({
                            background { color { tertiary.main } }
                            color { tertiary.mainContrast }
                            height { "4rem" }
                            justifyContent { center }
                            alignItems { center }
                            boxShadow { shadow() }
                        }) {
                            h1 { +title }
                        }
                    }

                    clickButton { text("Delegated Internal") } handledBy modal({
                        padding { none }
                    }) {
                        placement { center }
                        content {
                            fixedBox("Header", { raised })
                            div({
                                overflow { auto }
                                maxHeight { "calc(100vh - 10rem)" }
                                padding { normal }
                            }) {
                                repeat(30, { p { +loremIpsum } })
                            }
                            fixedBox("Footer", { top })
                        }
                    }
                }
            }
        }
        highlight {
            source(
                """
                clickButton { text("External") } handledBy modal {
                    // placement { top } is default
                    content {
                        repeat(30, { p { +loremIpsum } })
                    }
                }

                clickButton { text("Internal") } handledBy modal {
                    placement { center }
                    content {
                        repeat(30, { p { +loremIpsum } })
                    }
                }

                // mini component for header and footer
                val fixedBox: RenderContext.(String) -> Unit = { title ->
                    flexBox({
                        background { color { gray300 } }
                        height { "4rem" } // 2 times for both -> 8rem!
                        justifyContent { center }
                        alignItems { center }
                    }) {
                        h1 { +title }
                    }
                }

                clickButton { text("Delegated Internal") } handledBy modal({
                    padding { none }
                }) {
                    placement { center }
                    content {
                        fixedBox("Header")
                        div({
                            overflow { auto }
                            // set a max height for enforce the internal scrolling
                            // subtract the sum of header and footer and the default margin 
                            // outside, which is top and bottom each "1rem" from the
                            // default theme
                            maxHeight { "calc(100vh - 10rem)" }
                            padding { normal }
                        }) {
                            repeat(30, { p { +loremIpsum } })
                        }
                        fixedBox("Footer")
                    }
                }                    
                """
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
        highlight {
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
            +"You can customize the close button by overriding the default rendering via the "
            c("closeButtonRendering")
            +" property:"
        }
        componentFrame {
            lineUp {
                items {
                    clickButton {
                        text("Customized Close Button")
                    } handledBy modal {
                        closeButtonRendering {
                            clickButton({
                                background { color { danger.main } }
                                color { neutral.main }
                                css("transform: rotate(-20deg) translateX(-.5rem)")
                            }) {
                                size { small }
                                text("Nope")
                                iconPlacement { right }
                            }
                        }
                    }
                }
            }
        }

        highlight {
            source(
                """
                clickButton {
                    text("Customized  Close Button")
                } handledBy modal {
                    closeButtonRendering {
                        clickButton({
                            background { color { danger.main } }
                            color { neutral }
                            position { absolute { top { normal } } }
                            css("transform: rotate(-20deg) translateX(-.5rem)")
                        }) {
                            size { small }
                            text("Nope")
                            iconPlacement { right }
                        }
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
                                color { neutral.main }
                                background { color { warning.main } }
                            }) {
                                icon { logOut }
                            } handledBy close
                        }
                    }
                }
            }
        }
        highlight {
            source(
                """
                clickButton {
                    text("Custom Close Button")
                } handledBy modal { close -> // use the close handler provided by the modal
                    hasCloseButton(false)
                    content {
                        clickButton({
                            color { neutral }
                            background { color { warning.main } }
                        }) {
                            icon { logOut }
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
                    width { "100vw" }
                    height { "2000%" }
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
                    radioGroup(value = ModalComponent.overlay, items = overlayVariants.values.toList()) {
                        orientation { horizontal }
                        label { overlay ->
                            overlayVariants.filter { it.value == overlay }.map {
                                it.key
                            }[0]
                        }
                    }

                    clickButton {
                        text("Stack'em")
                    } handledBy createDeepDialogs(30, ModalComponent.WidthContext.normal)
                }
            }
        }
    }
}