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
        color { base }
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
            +"property."
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
                                color { base }
                            }) {
                                +"This is a basic toast."
                            }
                        }
                    }
                }
            }

        }
        playground {
            source(
                """
                    clickButton {
                        text("Show")
                    } handledBy toast {
                        content {
                            (::p.styled {
                                margin { normal }
                                color { base }
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
            alert {
                icon { circleInformation }
                variant { leftAccent }
                content {
                    p {
                        +"Since the toast component offers a minimal API only which requires you to build the toast's "
                        +" content manually, it is recommended to use an alert as content."
                    }
                }
            }
            br {  }
            +"This way it is much"
            +" easier to create rich contents with less lines of code. You can either pass the alert yourself or use"
            +" one of the provided convenience functions "
            c("showAlertToast") // todo: there is no example on how to use this function and what it does
            +" and "
            c("alertToast")
            +"."
        }
        componentFrame {
            clickButton {
                text("Create An Alert-Toast")
            } handledBy alertToast {
                title("AlertToast!")
                content("This is an alert in a toast.")
            }
        }
        playground {
            source(
                """
                clickButton {
                    text("Create An Alert-Toast")
                } handledBy alertToast {
                    title("AlertToast!")
                    content("This is an alert in a toast.")
                }
                """.trimIndent()
            )
        }



        showcaseSection("Toast Handling")

        paragraph {
            +"To connect a toast, we offer two options. First, you can create a toast by binding it to an component that"
            +" offers a flow of events or actions, a clickButton for example:"
            +" Every time you click the button, a toast is created. In this case the "
            c("toast")
            +" method is used to create that toast as it returns a handler."
            br { }
            +"The second option is to create a so called standalone toast. For a standalone toast, call the render"
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
        playground {
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
                        clicks handledBy store.render
                    }
                }
            }
            store.data.filter { it }.render {
                someFlow.render { flowItem ->
                    showToast {
                        position { bottomRight }
                        content {
                            // Styled for better readability; any other content is okay as well!
                            (::p.styled {
                                margin { normal }
                                color { base }
                            }) {
                                +flowItem
                            }
                        }
                    }
                }
            }

        }
        playground {
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
                        position { bottomRight }
                        content {
                            // Styled for better readability; any other content is okay as well!
                            (::p.styled {
                                margin { normal }
                                color { base }
                            }) {
                                +flowItem
                            }
                        }
                    }
                }
                """.trimIndent()
            )
        }


        showcaseSection("Positioning Your Toast")

        paragraph {
            +"You can configure the position in which your toast will be displayed."
            br { }
            +"Predefined positions are: "
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
                        position { topLeft }
                        content {
                            basicStyledToastContent("Top-Left")
                        }
                    }
                    clickButton {
                        text("Top")
                    } handledBy toast {
                        position { top }
                        content {
                            basicStyledToastContent("Top")
                        }
                    }
                    clickButton {
                        text("Top-Right")
                    } handledBy toast {
                        position { topRight }
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
                        position { bottom }
                        content {
                            basicStyledToastContent("Bottom")
                        }
                    }
                    clickButton {
                        text("Bottom-Left")
                    } handledBy toast {
                        position { bottomLeft }
                        content {
                            basicStyledToastContent("Bottom-Left")
                        }
                    }
                }
            }
        }
        playground {
            source(
                """
                showToast {
                    position { top }
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
                background { darkerGray }
                content {
                    basicStyledToastContent("Toast with custom background color")
                }
            }
        }
        playground {
            source(
                """
                showToast {
                    background { darkerGray }
                    content {
                        // Content omitted
                    }
                }
                """.trimIndent()
            )
        }

        showcaseSubSection("Close-Button")
        paragraph {
            +"The close button's style can optionally be overridden via the "
            c("closeButtonStyle")
            +" property."
        }
        componentFrame {
            clickButton {
                text("Show")
            } handledBy toast {
                closeButtonStyle {
                    background { color { lightGray } }
                    color { darkGray }
                }
                content {
                    basicStyledToastContent()
                }
            }
        }
        playground {
            source(
                """
                    showToast {
                        closeButtonStyle {
                            background { color { lightGray } }
                            color { darkGray }
                        }
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
                            basicStyledToastContent()
                        }
                    }

                    clickButton {
                        text("2000ms")
                    } handledBy toast {
                        duration { 2000 }
                        content {
                            basicStyledToastContent()
                        }
                    }
                }
            }
        }
        playground {
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

        showcaseSubSection("Close-Button")
        paragraph {
            +"Toasts can manually be dismissed by clicking on the close buttons which are part of every toast"
            +" by default. The close button can be enabled/disabled via the "
            c("isCloseable")
            +" property."
        }
        componentFrame {
            clickButton {
                text("Show")
            } handledBy toast {
                isCloseable(false)
                content {
                    basicStyledToastContent()
                }
            }
        }
        playground {
            source(
                """
                    showToast {
                        isCloseable(false)
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
        componentFrame {
            lineUp {
                items {
                    clickButton {
                        text("Add Toast")
                    } handledBy toast {
                        content {
                            basicStyledToastContent()
                        }
                    }
                    clickButton {
                        text("No Close Button")
                    } handledBy toast {
                        isCloseable { false }
                        content {
                            basicStyledToastContent()
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
        playground {
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
                        isCloseable { false }
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
