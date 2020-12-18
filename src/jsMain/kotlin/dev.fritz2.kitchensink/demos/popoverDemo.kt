package dev.fritz2.kitchensink.demos

import dev.fritz2.components.*
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.styling.params.styled
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
fun RenderContext.popoverDemo(): Div {

    return contentFrame {
        showcaseHeader("Popover")
        paragraph {
            +"The Popover is a non-modal dialog that floats around a trigger. It's used to display contextual information"
            +" to the user, and should be paired with a clickable trigger element. Use the regular styling parameter to "
            +" change its appearance."
        }

        showcaseSection("Usage")
        paragraph {
            +"To define a popover, follow these steps create a trigger. This can be a simple HTMLElement or a fritz2 component, e.g an icon or button. "
            +"For a simple version, add a "
            c("content")
            +" area to your popover with a Div inside and a "
            c("PushButton ")
            +" as trigger."


        }

        componentFrame {
            flexBox {
                popover {
                    trigger {
                        pushButton { text("Trigger") }
                    }
                    content {
                        div { +"Popover content" }
                    }
                }
            }
        }

        playground {
            source(
                """
                popover {
                    trigger {
                        pushButton { text("Trigger") }
                    }
                    content {  
                        div { +"Popover content" }
                  }
                }
            """.trimIndent()
            )
        }

        showcaseSection("Areas")
        paragraph {
            +"A popover must include at least one of the areas "
            c("header")
            +", "
            c("content")
            +", or"
            c("footer")
            +". Please note that there are two ways to add an area, either via a lambda or via a () function."
            +" Using the lambda, you must include a tag like Div, P, or a component - plain text will not render here."
            +" You can of course apply styling to the contents of the lambda like anywhere else."
            +" The () function accepts Strings only so that a simple text is added to the area: "
            c("footer(\"content\")")
            +". The following example adds all areas: "

            componentFrame {
                lineUp {
                    items {
                        popover {
                            trigger {
                                pushButton {
                                    text("Areas")
                                }
                            }
                            header("Header")
                            content {
                                div {
                                    +"Content"
                                }
                            }
                            footer("Footer")
                        }
                    }
                }
            }
        }
        playground {
            source(
                """
                 popover {
                    trigger {
                        pushButton { text("Areas") }
                    }
                    header("Header")
                    content {
                        div { +"Content" }
                    }
                    footer("Footer")
                }
            """.trimIndent()
            )
        }

        showcaseSection("Placement")
        paragraph {
            +"Choose one of four predefined placements for your popover: "
            c("right")
            +","
            c("top")
            +","
            c("bottom")
            +", "
            c("left")
            +", or "
            c("top")
            +" (default)."
        }

        componentFrame {
            lineUp {
                items {
                    popover {
                        trigger {
                            icon({ size { huge } }) { fromTheme { arrowRight } }
                        }
                        placement { right }
                        content {
                            div {
                                +"Content"
                            }
                        }
                    }

                    popover {
                        trigger {
                            icon({ size { huge } }) { fromTheme { arrowUp } }
                        }
                        placement { top }
                        content {
                            div {
                                +"Content"
                            }
                        }
                    }

                    popover {
                        trigger {
                            icon({ size { huge } }) { fromTheme { arrowLeft } }
                        }
                        placement { left }
                        content {
                            div {
                                +"Content"
                            }
                        }
                    }

                    popover {
                        trigger {
                            icon({ size { huge } }) { fromTheme { arrowDown } }
                        }
                        placement { bottom }
                        content {
                            div {
                                +"Content"
                            }
                        }
                    }

                }
            }
        }

        playground {
            source(
                """
                popover {
                    trigger {
                        icon({ size { huge } }) { fromTheme { arrowRight } }
                    }
                    placement { right }
                    content {
                        div {
                            +"Content"
                        }
                    }
                }
                """.trimIndent()
            )
        }







        showcaseSection("Close button")


        paragraph {

            +"The popover has a default close button which you can hide using "
            c("hasCloseButton(false)")
            +" - this way, the popover can only be closed by clicking the trigger again. Alternatively, you can create"
            +" your own close button with the "
            c("closeButton {}")
            +" function offered by the component's context."

        }
        componentFrame {
            lineUp {
                items {
                    popover {
                        trigger {
                            icon({ size { huge } }) { fromTheme { circleInformation } }
                        }
                        hasCloseButton(false)
                        content {
                            div {
                                +"Click the trigger again to close this popover."
                            }
                        }
                    }

                    popover {
                        trigger {
                            icon({ size { huge } }) { fromTheme { eye } }
                        }
                        closeButton({ size { normal } }) {
                            icon { fromTheme { eyeOff } }
                        }
                        content {
                            div {
                                +"A custom button replaces the default button."
                            }
                        }
                    }
                }
            }
        }

        playground {
            source(
                """
                popover {
                    trigger {
                        icon({ size { huge } }) { fromTheme { circleInformation } }
                    }
                    hasCloseButton(false)
                    content {
                        div {
                            +"Click the trigger again to close this popover."
                        }
                    }
                }

                popover {
                    trigger {
                        icon({ size { huge } }) { fromTheme { eye } }
                    }
                    closeButton({ size { normal } }) {
                        icon { fromTheme { eyeOff } }
                    }
                    content {
                        div {
                            +"A custom button replaces the default button."
                        }
                    }
                }
            """.trimIndent()
            )
        }

        showcaseSection("Marker")
        paragraph {
            +"""
            By default, the trigger is marked by an arrow pointing to it from the popover. You can disable this arrow if you wish.
            We also applied some additional styling to the following popover.
            """.trimIndent()
        }

        componentFrame {
            lineUp {
                items {
                    popover(
                        {
                            border {
                                color { tertiary }
                                style {  dashed }
                                width { fat }
                            }
                            radius { "1.5rem" }
                        }
                    ) {
                        trigger {
                            pushButton { text("No Marker, Custom Styling") }
                        }
                        header("A Plain Text Header.")
                        content {
                            (::h4.styled {
                                color { secondary }
                                textShadow { glowing }
                                padding { huge }
                            }) { +"Popover, content, and footer have customized styling." }
                        }
                        footer { (::h4.styled {
                            fontStyle { italic }
                            color { dark }
                            padding { normal } // todo this should be default
                            fontSize { tiny }
                        }) { +"The marker was removed." } }
                        hasArrow(false)
                    }
                }
            }
        }

        playground {
            source(
                """
                popover(
                    {
                        border {
                            color { tertiary }
                            style {  dashed }
                            width { fat }
                        }
                        radius { "1.5rem" }
                    }
                ) {
                    trigger {
                        pushButton { text("No Marker, Custom Styling") }
                    }
                    header("A Plain Text Header")
                    content {
                        (::h4.styled {
                            color { secondary }
                            textShadow { glowing }
                            padding { huge }
                        }) { +"Popover, content, and footer have customized styling." }
                    }
                    footer { (::h4.styled {
                        fontStyle { italic }
                        color { dark }
                        padding { normal } // todo this should be default
                        fontSize { tiny }
                    }) { +"The marker was removed." } }
                    hasArrow(false)
                }
            """.trimIndent()
            )
        }
    }
}