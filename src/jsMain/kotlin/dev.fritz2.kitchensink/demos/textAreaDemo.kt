package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.storeOf
import dev.fritz2.components.lineUp
import dev.fritz2.components.stackUp
import dev.fritz2.components.textArea
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.dom.values
import dev.fritz2.kitchensink.base.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
fun RenderContext.textareaDemo(): Div {

    return contentFrame {
        val dataStore = storeOf("")

        showcaseHeader("TextArea")

        paragraph {
            +"""
            A textarea is a multiline input field. It is resizable and comes in different basic sizes.
            """.trimIndent()
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
            textArea(store = dataStore) {
                placeholder("Textarea with placeholder only")
            }
            storeContentBox {
                p {
                    b { +"Text: " }
                    dataStore.data.asText()
                }
            }
        }
        playground {
            source(
                """
                val dataStore = storeOf("")                    
                textArea {
                    placeholder("Textarea with placeholder only")
                }
                """.trimIndent()
            )
        }

        showcaseSection("Sizes")
        paragraph {
            +"fritz2 offers three different sizes of textareas:"
            c("small")
            +", "
            c("normal")
            +" (default), and "
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

        playground {
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
            """.trimIndent()
            )
        }

        showcaseSection("Resize Behavior")
        paragraph {
            +"fritz2 offers the following resizing options for textareas: "
            c("vertical")
            +" (default), "
            c("horizontal")
            +", and "
            c("none")
            +"."
        }
        paragraph({
            display(sm = { block }, md = { none })
        }) { +"Devices with touchscreen might not be able to demonstrate this feature." }

        componentFrame {
            stackUp {
                items {
                    textArea {
                        placeholder("resize: vertical")
                    }
                    textArea {
                        placeholder("resize: horizontal")
                        resizeBehavior { horizontal }
                    }
                    textArea {
                        placeholder("resize: none")
                        resizeBehavior { none }
                    }
                }
            }
        }

        playground {
            source(
                """
                textArea {
                    placeholder("resize: vertical")
                }
                
                textArea {
                    placeholder("resize: horizontal")
                    resizeBehavior { horizontal }
                }
                
                textArea {
                    placeholder("resize: none")
                    resizeBehavior { none }
                }
                """.trimIndent()
            )
        }

        showcaseSection("Disabled TextArea")
        paragraph { +"Of course it is possible to disable a textarea. This will also skip the tab index for this component." }
        componentFrame {
            lineUp {
                items {
                    textArea {
                        placeholder("disabled")
                        disabled(true)
                    }
                }
            }
        }

        playground {
            source(
                """
                 textArea {
                    placeholder("disabled")
                    disabled(true)
                 }
                """.trimIndent()
            )
        }
    }
}