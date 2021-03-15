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
            +"The Popover is a non-modal dialog that floats around another element. It's used to display contextual information"
            +" to the user, and should be paired with a clickable toggle element. Use the regular styling parameter to "
            +" change its appearance."
        }

        showcaseSection("Usage")
        paragraph {
            +"To define a popover, follow these steps create a toggle. This can be a simple HTMLElement or a fritz2 component, e.g an icon or button. "
            +"For a simple version, add a "
            c("content")
            +" area to your popover with a Div inside and a "
            c("PushButton ")
            +" as toggle."


        }

        componentFrame {
            flexBox {
                popover {
                    toggle {
                        pushButton { text("Toggle") }
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
                    toggle {
                        pushButton { text("Toggle") }
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
                            toggle {
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
                    toggle {
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
                        toggle {
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
                        toggle {
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
                        toggle {
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
                        toggle {
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
                    toggle {
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







        showcaseSection("Close Button")


        paragraph {

            +"The popover has a default close button which you can hide using "
            c("hasCloseButton(false)")
            +" - this way, the popover can only be closed by clicking the toggle again. Alternatively, you can change"
            +" the icon of the close button with the "
            c("closeButtonIcon")
            +" and the styling via the "
            c("closeButtonStyling")
            +" properties offered by the component's context. You can even override the whole rendering with the "
            c("closeButtonRendering")
            +" property."
        }
        componentFrame {
            lineUp {
                items {
                    popover {
                        toggle {
                            icon({ size { huge } }) { fromTheme { circleInformation } }
                        }
                        hasCloseButton(false)
                        content {
                            div {
                                +"Click the toggle again to close this popover."
                            }
                        }
                    }

                    popover {
                        toggle {
                            icon({ size { huge } }) { fromTheme { eye } }
                        }
                        closeButtonIcon { eyeOff }
                        closeButtonStyle { color { danger } }
                        content {
                            div {
                                +"A custom icon for the button. Click the toggle or the custom close button to close this popover."
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
                    toggle {
                        icon({ size { huge } }) { fromTheme { circleInformation } }
                    }
                    hasCloseButton(false)
                    content {
                        div {
                            +"Click the toggle again to close this popover."
                        }
                    }
                }

                popover {
                    toggle {
                        icon({ size { huge } }) { fromTheme { eye } }
                    }
                    closeButtonIcon { eyeOff }
                    closeButtonStyle { color { danger } }                    
                    content {
                        div {
                            +"A custom icon for the button."
                        }
                    }
                }
            """.trimIndent()
            )
        }

        showcaseSection("Marker")
        paragraph {
            +"""
            By default, the toggle is marked by an arrow pointing to it from the popover. You can disable this arrow if you wish.
            We also applied some additional styling to the following popover.
            """.trimIndent()
        }

        componentFrame {
            lineUp {
                items {
                    popover(
                        {
                            border {
                                color { secondary.base }
                                style { dashed }
                                width { fat }
                            }
                            radius { "1.5rem" }
                        }
                    ) {
                        toggle {
                            pushButton { text("No Marker, Custom Styling") }
                        }
                        header("A Plain Text Header.")
                        content {
                            (::h4.styled {
                                color { info }
                                textShadow { glowing }
                                padding { huge }
                            }) { +"Popover, content, and footer have customized styling." }
                        }
                        footer {
                            (::h4.styled {
                                fontStyle { italic }
                                color { info }
                                padding { normal } // todo this should be default
                                fontSize { tiny }
                            }) { +"The marker was removed." }
                        }
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
                                color { secondary.base }
                                style {  dashed }
                                width { fat }
                            }
                            radius { "1.5rem" }
                        }
                    ) {
                        toggle {
                            pushButton { text("No Marker, Custom Styling") }
                        }
                        header("A Plain Text Header.")
                        content {
                            (::h4.styled {
                                color { info }
                                textShadow { glowing }
                                padding { huge }
                            }) { +"Popover, content, and footer have customized styling." }
                        }
                        footer { (::h4.styled {
                            fontStyle { italic }
                            color { info }
                            padding { normal }
                            fontSize { tiny }
                        }) { +"The marker was removed." } }
                        hasArrow(false)
                    }
            """.trimIndent()
            )
        }

        showcaseSection("Options")
        paragraph {
            +"By default, the popover closed automatically after click outside or tabbed out of it. "
            + "Pressing the Escape Key has the same effect. Use the options "
             c("closeOnBlur")
            +" and "
            c("closeOnEscape")
            +" to deactivate it."
        }
        componentFrame {
            lineUp {
                items {
                    popover {
                        closeOnBlur(false)
                        toggle {
                            pushButton { text("closeOnBlur deactivated") }
                        }
                        content {
                            div { +"Popover still be opened on blur" }
                        }
                    }
                    popover {
                        closeOnEscape(false)
                        toggle {
                            pushButton { text("closeOnEscape deactivated") }
                        }
                        content {
                            div { +"Popover still be opened by pressing the escape key" }
                        }
                    }
                }
            }
        }
        playground {
            source(
                """
                popover {
                        closeOnBlur(false)
                        toggle {
                           pushButton { text("closeOnBlur deactivated") }
                        }
                        content {
                            div { +"Popover still be opened on blur" }
                        }
                    }
                    
                    popover {
                        closeOnEscape(false)
                        toggle {
                             pushButton { text("closeOnEscape deactivated") }
                        }
                        content {
                            div { +"Popover still be opened by pressing the escape key" }
                        }
                    }
            """.trimIndent()
            )
        }
    }
}