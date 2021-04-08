package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.RootStore
import dev.fritz2.components.*
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.P
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.styling.params.styled
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow


private fun RenderContext.basicStyledToastContent(title: String = "Toast"): P {
    return (::p.styled {
        margins {
            top { small }
            left { small }
            bottom { small }
            right { "80px" }
        }
        color { neutral }
    }) {
        +title
    }
}


@ExperimentalCoroutinesApi
fun RenderContext.toastDemo(): Div {
    return contentFrame {
        showcaseHeader("Toast")
        paragraph {
            +"As known from mobile devices, a toast provides feedback in form of an alert after some action has"
            +" taken place."
            br { }
            +""
        }

        showcaseSection("Usage")

        paragraph {
            +"A basic toast can created by defining just the "
            c("content")
            +" property."
            +" This will create a toast with the given content, a default timer with 5000 ms, as well as a background"
            +" color matching the "
            c("info")
            +" color defined in the theme."
        }
        componentFrame {
            lineUp {
                items {
                    clickButton {
                        text("Show")
                    } handledBy toast {
                        content {
                            (::p.styled {
                                margin { normal }
                                color { neutral }
                            }) {
                                +"This is a basic toast."
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
                        text("Show")
                    } handledBy toast {
                        content {
                            (::p.styled {
                                margin { normal }
                                color { neutral }
                            }) {
                                +"This is a basic toast."
                            }
                        }
                    }
                """.trimIndent()
            )
        }

        showcaseSubSection("Use Alerts As Content")
        paragraph {
            +"A typical use case is to show an "
            c("alert")
            +" within a toast. Just place the alert into the "
            c("content")
            +" property of the toast:"
        }
        componentFrame {
            clickButton {
                text("Create An Alert-Toast")
            } handledBy toast {
                content {
                    alert {
                        title("AlertToast!")
                        content("This is an alert in a toast.")
                    }
                }
            }
        }
        highlight {
            source(
                """
                clickButton {
                    text("Create An Alert-Toast")
                } handledBy toast {
                    content {
                        alert {
                            title("AlertToast!")
                            content("This is an alert in a toast.")
                        }
                    }
                }
                """.trimIndent()
            )
        }



        showcaseSection("Toast Handling")

        paragraph {
            +"There are two ways to handle your toasts. You can create a toast by binding it to a component that"
            +" offers a Flow of events or actions, a clickButton for example."
            +" Every time you click the button, a toast is created. In this case the "
            c("toast")
            +" method is used to create that toast as it returns a handler."
            br { }
            +"The second option is to create a so called standalone toast. For a standalone toast, call the "
            c("render")
            +" function on any Flow and simply call the regular "
            c("showToast")
            +" method from there."
        }


        showcaseSubSection("Handle A Components Flow ")
        paragraph {
            +"Most of the time, you want to use a button whose events"
            +" are handled by the toast component: "
        }
        componentFrame {
            lineUp {
                items {
                    clickButton {
                        text("Show")
                    } handledBy toast {
                        content {
                            basicStyledToastContent()
                        }
                    }
                }
            }
        }
        highlight {
            source(
                """
                lineUp {
                    items {
                        clickButton {
                            text("Show")
                        } handledBy toast {
                            content {
                                // content omitted for readability
                            }
                        }
                    }
                }
                """.trimIndent()
            )
        }

        showcaseSubSection("Handle A Flow Directly")
        paragraph {
            +" For this use case you have to set the property"
            c("standAlone")
            +" to"
            c("true")
            +". Keep in mind that pressing the button is only necessary for demonstration purposes."

        }

        componentFrame {
            val list = listOf("Toast created", "for every", "element of a flow ")
            val someFlow = flow {
                for (element in list) {
                    delay(1000)
                    emit(element)
                }
            }
            val store = object : RootStore<Boolean>(false) {
                val render = handle {
                    !it
                }
            }
            lineUp {
                items {
                    clickButton {
                        text("Trigger Flow")
                    } handledBy store.render
                }
            }
            store.data.filter { it }.render {
                someFlow.render { flowItem ->
                    showToast {
                        placement { bottomRight }
                        content {
                            // Styled for better readability; any other content is okay as well!
                            (::p.styled {
                                margin { normal }
                                color { neutral }
                            }) {
                                +flowItem
                            }
                        }
                    }
                }
            }

        }
        highlight {
            source(
                """
                val list = listOf("Toast created", "for every", "element of a flow ")
                val someFlow = flow {
                    for (element in list) {
                        delay(1000)
                        emit(element)
                    }
                }
        
                someFlow.render { flowItem ->
                    showToast {
                        placement { bottomRight }
                        content {
                            // Styled for better readability; any other content is okay as well!
                            (::p.styled {
                                margin { normal }
                                color { neutral }
                            }) {
                                +flowItem
                            }
                        }
                    }
                }
                """.trimIndent()
            )
        }


        showcaseSection("Placing your Toast")

        paragraph {
            +"You can configure the placement in which your toast will be displayed."
            br { }
            +"Predefined placements are: "
            c("bottom")
            +", "
            c("bottomLeft")
            +", "
            c("bottomRight")
            +" (default), "
            c("top")
            +", "
            c("topLeft")
            +", and "
            c("topRight")
            +"."
        }
        componentFrame {
            lineUp {
                items {
                    clickButton {
                        text("Top-Left")
                    } handledBy toast {
                        placement { topLeft }
                        content {
                            basicStyledToastContent("Top-Left")
                        }
                    }
                    clickButton {
                        text("Top")
                    } handledBy toast {
                        placement { top }
                        content {
                            basicStyledToastContent("Top")
                        }
                    }
                    clickButton {
                        text("Top-Right")
                    } handledBy toast {
                        placement { topRight }
                        content {
                            basicStyledToastContent("Top-Right")
                        }
                    }
                    clickButton {
                        text("Bottom-Right")
                    } handledBy toast {
                        content {
                            basicStyledToastContent("Bottom-Right")
                        }
                    }
                    clickButton {
                        text("Bottom")
                    } handledBy toast {
                        placement { bottom }
                        content {
                            basicStyledToastContent("Bottom")
                        }
                    }
                    clickButton {
                        text("Bottom-Left")
                    } handledBy toast {
                        placement { bottomLeft }
                        content {
                            basicStyledToastContent("Bottom-Left")
                        }
                    }
                }
            }
        }
        highlight {
            source(
                """
                showToast {
                    placement { top }
                    content {
                        // Content omitted
                    }
                }
            """.trimIndent()
            )
        }


        showcaseSection("Styling")

        paragraph {
            +"Both the background color and the look of a toast's close-button can be customized."
        }

        showcaseSubSection("Background")
        paragraph {
            +"The toast's background color can be changed via the "
            c("background")
            +" property."
        }
        componentFrame {
            clickButton {
                text("Show")
            } handledBy toast {
                background { gray600 }
                content {
                    basicStyledToastContent("Toast with custom background color")
                }
            }
        }
        highlight {
            source(
                """
                showToast {
                    background { gray600 }
                    content {
                        // Content omitted
                    }
                }
                """.trimIndent()
            )
        }

        showcaseSubSection("Close Button")
        paragraph {
            +"The close button can optionally be customized via the"
            c("closeButtonStyle")
            +", "
            c("closeButtonIcon")
            +" or ultimately by the "
            c("closeButtonRendering")
            +" properties."
        }
        componentFrame {
            clickButton {
                text("Show")
            } handledBy toast {
                closeButtonStyle {
                    margins { right { small } }
                    background { color { warning } }
                }
                closeButtonIcon { fritz2 }
                content {
                    basicStyledToastContent()
                }
            }
        }
        highlight {
            source(
                """
                    showToast {
                        closeButtonStyle {
                            margins { right { small } }
                            background { color { warning } }
                        }
                        closeButtonIcon { fritz2 }
                        content {
                            basicStyledToastContent()
                        }
                    }
                """.trimIndent()
            )
        }


        showcaseSection("Duration")

        paragraph {
            +"By default a toast is automatically dismissed after 5000 ms. You can change the duration by setting the "
            c("duration")
            +" property in ms."
        }
        componentFrame {
            lineUp {
                items {
                    clickButton {
                        text("Default")
                    } handledBy toast {
                        content {
                            basicStyledToastContent("Default")
                        }
                    }

                    clickButton {
                        text("2000ms")
                    } handledBy toast({
                        background {
                            color { secondary.base }
                        }
                    }) {
                        duration(2000)
                        content {
                            basicStyledToastContent("2000ms")
                        }
                    }
                }
            }
        }
        highlight {
            source(
                """
                toast {
                    duration { 2000 }
                    content {
                        // Content omitted
                    }
                }
                """.trimIndent()
            )
        }


        showcaseSection("Closing Toasts Manually")
        paragraph {
            +"Toasts can manually be dismissed by clicking on it's close button which is part of every toast"
        }

        showcaseSubSection("Close Button")
        paragraph {
            +"Toasts can manually be dismissed by clicking on the close buttons which are part of every toast"
            +" by default. The close button can be enabled/disabled via the "
            c("hasCloseButton")
            +" property."
        }
        componentFrame {
            clickButton {
                text("Show")
            } handledBy toast {
                hasCloseButton(false)
                content {
                    basicStyledToastContent()
                }
            }
        }
        highlight {
            source(
                """
                    showToast {
                        hasCloseButton(false)
                        content {
                            basicStyledToastContent()
                        }
                    }
                """.trimIndent()
            )
        }

        showcaseSubSection("Dismissing Multiple Toasts")
        paragraph {
            +"Apart from closing single toasts with their close buttons or waiting for them to close themselves, "
            +" there are two more options to close toasts: "
            c("ToastComponent.closeLastToast()")
            +" and "
            c("ToastComponent.closeAllToasts()")
            +"."
            +"You can also remove the close button so that the toast can't be closed by the user. In this case it will"
            +" still disappear when the timer runs out."
        }
        var counter: Int = 0

        componentFrame {
            lineUp {
                items {
                    clickButton {
                        text("Add Toast")
                    } handledBy toast {
                        content {
                            basicStyledToastContent("Toast #$counter")
                            counter++
                        }
                    }
                    clickButton {
                        text("No Close Button")
                    } handledBy toast({
                        background {
                            color { danger }
                        }
                    }) {
                        hasCloseButton(false)
                        content {
                            basicStyledToastContent("Toast #$counter")
                            counter++
                        }
                    }
                    clickButton {
                        text("Close Latest")
                    } handledBy ToastComponent.closeLastToast()

                    clickButton {
                        text("Close All")
                    } handledBy ToastComponent.closeAllToasts()
                }
            }
        }
        highlight {
            source(
                """
                    clickButton {
                        text("Add Toast")
                    } handledBy toast {
                        content {
                            // content.. 
                        }
                    }
                    clickButton {
                        text("No Close Button")
                    } handledBy toast {
                        hasCloseButton { false }
                        content {
                            // content.. 
                        }
                    }
                    clickButton {
                        text("Close Latest")
                    } handledBy ToastComponent.closeLastToast()

                    clickButton {
                        text("Close All")
                    } handledBy ToastComponent.closeAllToasts()
                """.trimIndent()
            )
        }
    }
}
