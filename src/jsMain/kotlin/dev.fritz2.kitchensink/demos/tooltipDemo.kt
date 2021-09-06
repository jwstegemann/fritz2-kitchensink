package dev.fritz2.kitchensink.demos

import dev.fritz2.components.*
import dev.fritz2.components.popup.Placement
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.styling.span
import dev.fritz2.styling.div
import dev.fritz2.styling.theme.Theme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*


@ExperimentalCoroutinesApi
fun RenderContext.tooltipDemo(): Div {
    return div {
        contentFrame({
            display(sm = { block }, md = { none })
        }) {
            coloredBox(Theme().colors.warning) {
                +"Tooltips do not work on small devices, so this demonstration is disabled."
                br {}
                +"Please view this demo on a medium sized display at least!"
            }
        }
        contentFrame({
            display(sm = { none }, md = { block })
        }) {
            showcaseHeader("Tooltip")

            paragraph {
                +"The "
                c("tooltip")
                +" is a more sophisticated title that appears when the user places the mouse over an element. It can be "
                +" styled like any other component and additionally offers some specific options."
            }

            coloredBox(Theme().colors.warning) {
                +"Devices with touchscreen might not be able to demonstrate the features on this page."
            }

            showcaseSection("Usage")
            paragraph {
                +"To apply a tooltip, use the appropriate factory function within any "
                c("Tag")
                +"'s content."
            }
            componentFrame {
                p {
                    +"Here is a paragraph with some "
                    strong {
                        +"tooltip trigger"
                        tooltip("This is the content of the Tooltip") {}
                    }
                    +" inside"
                }
            }
            highlight {
                source(
                    """
                    p {
                        +"Here is a paragraph with some "
                        strong {
                            +"tooltip trigger"
                            // just call the factory from inside some Tag!
                            tooltip("This is the content of the Tooltip") {}
                        }
                        +" inside"
                    }                        
                    """.trimIndent()
                )
            }

            showcaseSection("Text support")
            paragraph {
                +"As shown above Tooltip allows to pass one "
                c("String")
                +" directly as optional parameter into the factory, to support the most common use case."
            }
            paragraph {
                +"There is also a variation which accepts an arbitrary amount of "
                c("String")
                +" parameters to directly support multi line content, as each of the parameters will be rendered as "
                +"a separated line of text."
            }
            paragraph {
                +"Last but not least it is also possible to connect some "
                c("Flow<String>")
                +" or even "
                c("Flow<List<String>>")
                +" with the content rendering."
            }
            componentFrame {
                stackUp {
                    items {
                        span {
                            +"Hover me for one line tooltip "
                            tooltip("Here is only one line of text!") {}
                        }
                        span {
                            +"Hover me for multi line tooltip "
                            tooltip("First line of text!", "Second line of text!") {}
                        }

                        val fizzBuzz = generateSequence { listOf("", "", "Fizz") }.flatten()
                            .zip(generateSequence { listOf("", "", "", "", "Buzz") }.flatten()) { fizz, buzz ->
                                "$fizz$buzz"
                            }.zip(generateSequence(1) { count -> count + 1 })
                            .map { (fizzbuzz, count) -> fizzbuzz.ifBlank { count.toString() } }
                            .asFlow()
                            .onEach { delay(1000) }

                        span {
                            +"Hover me for some dynamic content with "
                            externalLink("FizzBuzz", "https://en.wikipedia.org/wiki/Fizz_buzz")
                            +" fun \uD83D\uDE00"
                            tooltip {
                                text(fizzBuzz)
                            }
                        }
                    }
                }
            }
            highlight {
                source(
                    """
                    span {
                        +"Hover me for one line tooltip "
                        tooltip("Here is only one line of text!") {}
                    }
                    
                    span {
                        +"Hover me for multi line tooltip "
                        tooltip("First line of text!", "Second line of text!") {}
                    }

                    // create some flow for demonstration
                    val fizzBuzz = generateSequence { listOf("", "", "Fizz") }.flatten()
                        .zip(generateSequence { listOf("", "", "", "", "Buzz") }.flatten()) { 
                            fizz, buzz -> "${'$'}fizz${'$'}buzz" 
                        }.zip(generateSequence(1) { count -> count + 1 })
                        .map { (fizzbuzz, count) -> fizzbuzz.ifBlank { count.toString() } }
                        .asFlow()
                        .onEach { delay(1000) }
                        
                    span {
                        +"Hover me for some dynamic content with fun \uD83D\uDE00"
                        tooltip {
                            text(fizzBuzz) // provide a flow for the text property
                        }
                    }                        
                    """.trimIndent()
                )
            }

            showcaseSection("Placement")
            paragraph {
                +"The Tooltip allows clients to place the overlay content explicitly to one of the values the "
                +"underlying "
                internalLink("Popup", "Popup")
                +" component supports."
            }
            paragraph {
                +"It is also intentional that the intelligent placement is activated by default, so the configured "
                +"placement might be overruled and the overlay rendered onto another, better fitting side."
            }
            componentFrame {
                val placements = listOf(
                    "top" to Placement.Top,
                    "topStart" to Placement.TopStart,
                    "topEnd" to Placement.TopEnd,
                    "bottom" to Placement.Bottom,
                    "bottomStart" to Placement.BottomStart,
                    "bottomEnd" to Placement.BottomEnd,
                    "left" to Placement.Left,
                    "leftStart" to Placement.LeftStart,
                    "leftEnd" to Placement.LeftEnd,
                    "right" to Placement.Right,
                    "rightStart" to Placement.RightStart,
                    "rightEnd" to Placement.RightEnd
                )
                gridBox({
                    columns { repeat(5) { "1fr" } }
                    areas {
                        row(".", "topStart", "top", "topEnd", ".")
                        row("leftStart", ".", ".", ".", "rightStart")
                        row("left", ".", ".", ".", "right")
                        row("leftEnd", ".", ".", ".", "rightEnd")
                        row(".", "bottomStart", "bottom", "bottomEnd", ".")
                    }
                }) {
                    placements.forEach { (name, placement) ->
                        div({
                            grid { area { name } }
                            display { flex }
                            justifyContent { center }
                            background { color { primary.main } }
                            color { primary.mainContrast }
                            padding { small }
                            margin { small }
                            radius { small }
                        }) {
                            +name
                            tooltip({
                                zIndex { appFrame.plus(10) }
                            }, "placed $name") {
                                placement { placement }
                            }
                        }
                    }
                }
            }

            showcaseSection("Integration with other Components")
            paragraph {
                +"Just as with the above shown usage with simple "
                c("Tag")
                +"s, the Tooltip component also works with the following supported components:"
                ul {
                    li { internalLink("Buttons", "Button") }
                    li { internalLink("InputField", "InputField") }
                    li { internalLink("SelectField", "SelectField") }
                    li { internalLink("TypeAhead", "TypeAhead") }
                    li { internalLink("Checkboxes", "Checkbox") }
                    li { internalLink("Radiogroup", "Radiogroup") }
                    li { internalLink("Textarea", "Textarea") }
                    li { internalLink("Switch", "Switch") }
                    li { internalLink("Slider", "Slider") }
                    li { internalLink("MenuEntry", "Menu") }
                    li { internalLink("MenuLink", "Menu") }
                    li { internalLink("CustomMenuEntry", "Menu") }
                }
            }
            paragraph {
                +"The configuration contexts offer each factory methods that mimic the known factory functions "
                +"documented in the former sections:"
            }
            componentFrame {
                clickButton {
                    text("Click me!")
                    tooltip(
                        {
                            zIndex { appFrame.plus(10) }
                        }, "You really just need to click on the button!"
                    ) {}
                }
            }
            highlight {
                source(
                    """
                    clickButton {
                        text("Click me!")
                        // just call the factory method within the configuration context!
                        tooltip("You really just need to click on the button!") {}
                    }                        
                    """.trimIndent()
                )
            }

            //
            // Deprecated sections of the old styling based implementation
            //

            div({
                margins { top { "10rem" } }
            }) { +" " }

            showcaseHeader("Deprecated Tooltip implementation")
            coloredBox(Theme().colors.danger) {
                +"This styling based implementation is deprecated and will be removed in future versions of fritz2."
                br {}
                +"You should refactor your code to the above explained new component!"
            }

            showcaseSection("Usage")
            coloredBox(Theme().colors.danger) {
                +"Deprecated! See main section for details!"
            }
            paragraph {
                +"In fritz2, tooltips can be added to each stylable element's styling parameter. A simple version"
                +" is created by passing a String to the "
                c("tooltip()")
                +" function. You can also make the tooltip multiline - just add"
                +" a list of Strings to it instead of a single String. Don't forget the additional () after the function call."
            }
            componentFrame {
                lineUp {
                    items {
                        span({
                            tooltip("Visit us on www.fritz2.dev")()
                        }) { +"Tooltip" }

                        span({
                            tooltip("Visit us!", "", "www.fritz2.dev")()
                        }) { +"Multiline Tooltip" }
                    }
                }
            }
            highlight {
                source(
                    """
                    span({ // add tooltip to styling parameter
                        tooltip("Visit us on www.fritz2.dev") ()
                    }) { +"Tooltip" } // span content 

                    span({ // empty Strings work for multiline as well
                        tooltip("Visit us!", "", "www.fritz2.dev") ()
                    }) { +"Multiline Tooltip" }
                """
                )
            }

            showcaseSection("Placement")
            coloredBox(Theme().colors.danger) {
                +"Deprecated! See main section for details!"
            }
            paragraph {
                +"The "
                c("tooltip")
                +" can be displayed either at the "
                c("top")
                +" (default),"
                c("right")
                +", "
                c("bottom")
                +", or"
                c("left")
                +" side of the styled element. The placement value goes directly into the component's context. Also, don't forget the additional (): "
            }
            componentFrame {
                lineUp {
                    items {
                        span({
                            tooltip("top placement") { top }()
                        }) { +"top" }

                        span({
                            tooltip("right side placement") { right }()
                        }) { +"right" }

                        span({
                            tooltip(" bottom placement") { bottom }()
                        }) { +"bottom" }

                        span({
                            tooltip("left side placement") { left }()
                        }) { +"left" }
                    }
                }
            }
            highlight {
                source(
                    """
                span({
                    tooltip("top placement") { top } ()
                }) { +"top" }
                
                span({
                    tooltip("right side placement") { right } ()
                }) { +"right" }
                
                span({
                    tooltip(" bottom placement") { bottom } ()
                }) { +"bottom" }
                
                span({
                    tooltip("left side placement") { left } ()
                }) { +"left" }
                """
                )
            }

        }
    }
}

