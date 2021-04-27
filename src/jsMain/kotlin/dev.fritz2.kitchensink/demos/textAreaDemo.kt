package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.storeOf
import dev.fritz2.components.stackUp
import dev.fritz2.components.textArea
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.styling.theme.Theme
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
fun RenderContext.textareaDemo(): Div {

    return contentFrame {

        showcaseHeader("TextArea")

        paragraph {
            +"TextArea is a multiline input field."
        }


        showcaseSection("Usage")

        paragraph {
            +" A basic, vertically resizable textarea with its default size of "
            c("normal")
            +" can be created by simply calling its factory function. "
            +"By passing a store to the textarea, the events are connected automatically. "
            +"On top you can set a placeholder if you wish."
        }
        componentFrame {
            val textStore = storeOf("")
            textArea(value = textStore) {
                placeholder("Textarea with placeholder only")
            }
            storeContentBox("Text") {
                span {
                    textStore.data.asText()
                }
            }
        }

        highlight {
            source(
                """
                val textStore = storeOf("")
                textArea(value = textStore) {
                    placeholder("Textarea with placeholder only")
                }
                """
            )
        }

        showcaseSection("Sizes")
        paragraph {
            c("textArea")
            +" offers three different sizes: "
            c("small")
            +", "
            c("normal")
            +" (default) and "
            c("large")
            +"."
        }

        componentFrame {
            stackUp {
                items {
                    textArea {
                        placeholder("small")
                        size { small }
                    }
                    textArea {
                        placeholder("normal")
                    }
                    textArea {
                        placeholder("large")
                        size { large }
                    }
                }
            }
        }

        highlight {
            source(
                """
                 textArea {
                    placeholder("small")
                    size { small }
                 }
                 
                 textArea {
                    placeholder("normal")
                 }
                 
                 textArea {
                    placeholder("large")
                    size { large }
                 }
            """
            )
        }

        showcaseSection("Resize Behavior")
        paragraph {
            c("textArea")
            +" offers the following resizing options: "
            c("vertical")
            +", "
            c("horizontal")
            +", "
            c("both")
            +" (default) and "
            c("none")
            +"."
        }

        coloredBox(Theme().colors.danger) {
            +"Devices with touchscreen might not be able to demonstrate this feature."
        }

        componentFrame {
            stackUp {
                items {
                    textArea {
                        placeholder("resize: vertical")
                        resizeBehavior { vertical }
                    }
                    textArea {
                        placeholder("resize: horizontal")
                        resizeBehavior { horizontal }
                    }
                    textArea {
                        placeholder("resize: both")
                    }
                    textArea {
                        placeholder("resize: none")
                        resizeBehavior { none }
                    }
                }
            }
        }

        highlight {
            source(
                """
                textArea {
                    placeholder("resize: vertical")
                    resizeBehavior { vertical }
                }
                
                textArea {
                    placeholder("resize: horizontal")
                    resizeBehavior { horizontal }
                }
                
                textArea {
                    placeholder("resize: both")
                }
                    
                textArea {
                    placeholder("resize: none")
                    resizeBehavior { none }
                }
                """
            )
        }

        showcaseSection("Disabled")
        paragraph {
            +"Of course it is possible to disable a "
            c("textArea")
            +". This will also skip the tab index for this component."
        }
        componentFrame {
            textArea {
                placeholder("disabled")
                disabled(true)
            }
        }

        highlight {
            source(
                """
                 textArea {
                    placeholder("disabled")
                    disabled(true)
                 }
                """
            )
        }
    }
}