package dev.fritz2.kitchensink.demos

import dev.fritz2.components.lineUp
import dev.fritz2.components.tooltip
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.styling.span
import dev.fritz2.styling.theme.Theme
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
fun RenderContext.tooltipDemo(): Div {
    return div {
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

            coloredBox(Theme().colors.danger) {
                +"Devices with touchscreen might not be able to demonstrate the features on this page."
            }

            showcaseSection("Usage")
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
                            tooltip("Visit us on www.fritz2.dev") ()
                        }) { +"Tooltip" }

                        span({
                            tooltip("Visit us!", "", "www.fritz2.dev") ()
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
                    }
                }
            }
            highlight {
                source("""
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

