package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.RootStore
import dev.fritz2.binding.storeOf
import dev.fritz2.components.*
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map


@ExperimentalCoroutinesApi
fun RenderContext.toastDemo(): Div {
    val items = listOf("Toasts" to "created", "for" to "every", "element" to "on a flow ")
    val data = flow {
        for (element in items) {
            delay(1500)
            emit(element)
        }
    }


    return contentFrame {
        /*showcaseHeader("Toast")
        paragraph {
            +"As known from mobile devices, a toast provides some feedback in form of an alert after some action has taken placed ."
            br { }
            +""
        }


        showcaseSection("Usage")
        paragraph {
            +"A basic toast can created by just defining the "
            c("title")
            +"and"
            c("description")
            +"property ."
            +"This will create a toast with "
            c("title,")
            c("description,")
            c("a default timer with 5000 ms,")
            +"and a "
            c("status")
            +" of type "
            c("info")
        }
        componentFrame {

            lineUp {
                items {

                    clickButton {
                        text("Show Toast")
                    } handledBy toast {
                        title { "Success" }
                        description { "Your account." }

                    }
                }
            }

        }

        playground {
            source(
                """
                toast {
                        title { "Success" }
                        description { "Your account was successfully created." }
                    }
            """.trimIndent()
            )
        }

        showcaseSection("How To Handle The Toast Component")
        paragraph {
            +"To use a toast we offer two possibilities. First, you can create a toast by binding it to an component,which offers an flow of events or actions like a clickbutton."
            +" So every time you click the button, a toast is created."
            br { }
            +" The second possibility is to create so called standalone toast. Therefore you call the render function on any Flow and define the toast."
            +"  It is important that in this case you set the standAlone property to true. Now, every time a new element appears on this flow, a toast is created."


        }

        showcaseSubSection("Handle A Components Flow ")
        paragraph { +"This is how we create a toast most of the time in this documentation. We use a button which events are handled by the toast component." }
        componentFrame {
            lineUp {
                items {
                    clickButton {
                        text("Show")
                    } handledBy toast {
                        title { "Success" }
                        description { "Your account ." }
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
                        title { "Success" }
                        description { "Your account was successfully created." }
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

        val list = listOf("Toast" to "created", "for" to "every", "element" to "on a flow ")
        val someFlow = flow {
            for (element in list) {
                delay(1000)
                emit(element)
            }
        }

        componentFrame {
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
                someFlow.render {
                    toast {
                        standAlone { true }
                        position { bottomRight }
                        title { it.first.toString() }
                        description { it.second }
                    }
                }
            }

        }



        playground {
            source(
                """
                val list = listOf("Toasts" to "created", "for" to "every", "element" to "on a flow ")
        val someFlow = flow {
            for (element in list) {
                delay(1000)
                emit(element)
            }
        }

        someFlow.render {
            toast {
                standAlone { true }
                position { bottomRight }
                title { it.first.toString() }
                description { it.second }
             }
        }
            """.trimIndent()
            )
        }





        showcaseSection("Custom Component")
        paragraph { +"Instead defining a title or description, you can display a custom component." }
        componentFrame {
            clickButton {

                text("Show Custom Component")
            } handledBy toast {
                customComponent {
                    box { +"Hello Fritz2" }
                }
            }
        }

        playground {
            source(
                """
                toast {
                customComponent {
                    p { +"Hello Fritz2" }
                }
            }
            """.trimIndent()
            )
        }

        showcaseSection("Positioning Your Toast")
        paragraph {
            +"You can configure the position where your toast will be popup from."
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
            +" where"
            c("bottom")
            +"is the default position."
        }

        componentFrame {
            lineUp {
                items {
                    clickButton {
                        text("Top-Left")
                    } handledBy toast {
                        title { "Top-Left" }
                        description { "top-left" }
                        position { topLeft }
                    }
                    clickButton {
                        text("Top")
                    } handledBy toast {
                        title { "Top" }
                        description { "top" }
                        position { top }
                    }
                    clickButton {
                        text("Top-Right")
                    } handledBy toast {
                        title { "Top-Right" }
                        description { "top-right" }
                        position { topRight }
                    }
                    clickButton {
                        text("Bottom-Right")
                    } handledBy toast {
                        title { "Bottom-Right" }
                        description { "bottom-right" }
                        position { bottomRight }
                    }
                    clickButton {
                        text("Bottom")
                    } handledBy toast {
                        title { "Bottom" }
                        description { "default" }
                    }
                    clickButton {
                        text("Bottom-Left")
                    } handledBy toast {
                        title { "Bottom-Left" }
                        description { "bottom-left" }
                        position { bottomLeft }
                    }


                }
            }
        }

        playground {
            source(
                """
                toast {
                        title { "Position" }
                        description { "Position" }
                        position { top }           
                    }
            """.trimIndent()
            )
        }

        showcaseSection("Status")
        paragraph {
            +"Our toast provides multiple status  :"

            c("info (default)")
            +"|"
            c("success")
            +"|"
            c("warning")
            +"|"
            c("error")


        }

        componentFrame {
            lineUp {
                items {
                    clickButton {
                        text("Info")
                    } handledBy toast {
                        title { "info" }
                        description { "default status" }
                        duration { 1000 }
                    }
                    clickButton {
                        text("Success")
                    } handledBy toast {
                        title { "Success" }
                        description { "success status" }
                        status { success }
                        duration { 1000 }
                    }
                    clickButton {
                        text("Warning")
                    } handledBy toast {
                        title { "Warning" }
                        description { "warning status" }
                        status { warning }
                        duration { 1000 }
                    }
                    clickButton {
                        text("Error")
                    } handledBy toast {
                        title { "Error" }
                        description { "error status" }
                        status { error }
                        duration { 1000 }
                    }

                }
            }
        }

        playground {
            source(
                """
                   toast {
                        title { "Success" }
                        description { "success status" }
                        status { success }                 
                      }
            """.trimIndent()
            )
        }

        showcaseSection("Closing Toasts")
        paragraph {
            +"As mentioned before, toasts close themselves after a specified time or can be closed by the close button."
            br {}
            +"But you have two more possibilities to close toasts: "
            c("closeLastToast()")
            +"|"
            c("closeAllToasts()")
            br { }
            +"You can also remove the close button so that the toast couldn't be closed by the user before the timer is expired."

        }

        componentFrame {
            lineUp {

                items {

                    clickButton {
                        text("Add Toast")
                    } handledBy toast {
                        title { "Title" }
                        description { "Descripton" }

                    }
                    clickButton {
                        text("No Close Button")
                    } handledBy toast {
                        title { "No" }
                        description { "close button" }
                        isCloseable { false }

                    }
                    clickButton {
                        text("Close Latest")
                    } handledBy ToastComponent().closeLastToast()

                    clickButton {
                        text("Close All")
                    } handledBy ToastComponent().closeAllToasts()
                }
            }
        }

        playground {
            source(
                """
                    
                    toast {
                        title { "No" }
                        description { "close button" }
                        isCloseable { false }

                    }
                    
                clickButton {
                       } handledBy ToastComponent().closeLastToast()

                    clickButton {
                         text("Close All")
                    } handledBy ToastComponent().closeAllToasts()
                
            """.trimIndent()
            )
        }

        showcaseSection("Duration")
        paragraph {
            +"As default, a toast is shown 5000 ms. You can change the duration by setting the"
            c("duration")
            +"property in ms."
        }

        componentFrame {
            lineUp {
                items {
                    clickButton {
                        text("Default")
                    } handledBy toast {
                        title { "Default" }
                        description { "5000 ms" }

                    }

                    clickButton {
                        text("2000ms")
                    } handledBy toast {
                        title { "Custom Duration" }
                        description { "2000ms" }
                        position { top }
                        duration { 2000 }

                    }
                }
            }
        }

        playground {
            source(
                """
                toast {
                        title { "Custom Duration" }
                        description { "2000ms" }
                        duration { 2000 }
                    }
            """.trimIndent()
            )
        }

        showcaseSection("Change The Default Icon")
        paragraph {
            +"Changing the default icon is really easy. Just pass one of our predefined icons."
        }
        componentFrame {
            lineUp {
                items {
                    clickButton {
                        text("Icon")
                    } handledBy toast {
                        icon { eye }
                        title { "Icon" }
                        description { "custom icon" }
                    }
                }
            }
        }

        playground {
            source(
                """
                toast {
                        icon { eye }
                        title { "Icon" }
                        description { "custom icon" }
                    }
            """.trimIndent()
            )
        }*/
    }

}
