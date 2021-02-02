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
        margin { normal }
        color { lightGray }
    }) {
        +title
    }
}


@ExperimentalCoroutinesApi
fun RenderContext.toastDemo(): Div {
    return contentFrame {
        showcaseHeader("Toast")
        paragraph {
            +"As known from mobile devices, a toast provides some feedback in form of an alert after some action has"
            +" taken place."
            br { }
            +""
        }

        showcaseSection("Usage")

        paragraph {
            +"A basic toast can created by just defining the "
            c("content")
            +"property ."
            +"This will create a toast with the given content, a default timer with 5000 ms as well as a background"
            +" color matching the info color defined in the theme."
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
                                color { lightGray }
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
                showToast {
                        content {
                            // Styled for better readability; any other content is okay as well!
                            (::p.styled {
                                margin { normal }
                                color { lightGray }
                            }) {
                                +"This is a basic toast."
                            }
                        }
                    }
                """.trimIndent()
            )
        }

        showcaseSubSection("Use Alerts As The Toasts Content")
        paragraph {
            +"Since the toast component offers a minimal API only which requires you to build the toast's content on"
            +" manually all the time, it is recommended to use an alert as your toast's content. This way it is much"
            +" easier to create rich contents with less lines of code. You can either pass the alert yourself or use"
            +" one of the provided convenience functions "
            c("showAlertToast")
            +" and "
            c("alertToast")
            +"."
        }
        componentFrame {
            clickButton {
                text("Create An Alert-Toast")
            } handledBy alertToast {
                title("Alert")
                content("This is an alert in a toast.")
            }
        }
        playground {
            source(
                """
                    showToast {
                        content {
                            alert {
                                title("Alert")
                                content("This is an alert in a toast.")
                            }
                        }
                    }
                """.trimIndent()
            )
        }
        playground {
            source(
                """
                    showAlertToast {
                        title("Alert")
                        content("This is an alert in a toast.")
                    }
                """.trimIndent()
            )
        }

        showcaseSection("How To Handle The Toast Component")

        paragraph {
            +"To use a toast we offer two options. First, you can create a toast by binding it to an component that "
            +"offers a flow of events or actions, like for example a clickbutton."
            +" This way, every time you click the button, a toast is created. In this case the "
            c("toast")
            +" method is used to create that toast as it returns a handler."
            br { }
            +" The second option is to create so called standalone toast. Therefore you call the render function on any"
            +" Flow and call the regular "
            c("showToast")
            +" method from there."
        }

        showcaseSubSection("Handle A Components Flow ")
        paragraph {
            +"This is how we create a toast most of the time in this documentation. We use a button whose events"
            +" are handled by the toast component."
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
                clickButton {
                    text("Show")
                } handledBy toast {
                    content {
                        // Content omitted
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
            +". Keep in mind, that pressing the button is only necessary for demonstration purpose."

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
                                color { lightGray }
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
                                color { lightGray }
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
            +"Predefined positions are : "
            c("bottom ")
            +"|"
            c(" bottomLeft ")
            +"|"
            c(" bottomRight ")
            +"|"
            c("top ")
            +"|"
            c(" topLeft ")
            +"|"
            c(" topRight ")
            +" where bottomRight is the default."
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


        showcaseSection("Closing Toasts")

        // TODO: Add info about the close button, it's style and the toast's background
        paragraph {
            +"As mentioned before, toasts close themselves after a specified time or can be closed by the close button."
            br {}
            +"But you have two more possibilities to close toasts: Namely "
            c("ToastComponent.closeLastToast()")
            +" and "
            c("ToastComponent.closeAllToasts()")
            +"."
            br { }
            +"You can also remove the close button so that the toast can't be closed by the user. In this case it will"
            +" still disappear when the timer hits zero."
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
                    toast {
                        isCloseable { false }
                        content {
                            // Content omitted
                        }
                    }
                    
                    clickButton {
                        text("Close Latest")
                    } handledBy ToastComponent().closeLastToast()

                    clickButton {
                         text("Close All")
                    } handledBy ToastComponent().closeAllToasts()
                """.trimIndent()
            )
        }


        showcaseSection("Duration")

        paragraph {
            +"As default, a toast is shown 5000 ms. You can change the duration by setting the "
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
    }

}
