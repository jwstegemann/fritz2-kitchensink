package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.storeOf
import dev.fritz2.components.lineUp
import dev.fritz2.components.stackUp
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.dom.values
import dev.fritz2.kitchensink.base.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
fun RenderContext.textareaDemo(): Div {

    return contentFrame {
        val dataStore = storeOf("Store Content")

        showcaseHeader("Textarea")

        paragraph {
            +"""
            A textarea is a multiline input field. It is resizable and comes in different basic sizes.
            """.trimIndent()
        }


        showcaseSection("Usage")

        paragraph {
            +" A basic, vertically resizable textarea with its default size of "
            c("normal")
            +" can be created by simply calling its factory function. Set a placeholder if you wish: "
        }
        componentFrame {
            lineUp {
                items {
                    textarea {
                        placeholder("Textarea with placeholder only")
                    }
                }
            }
        }
        playground {
            source(
                    """
                textarea {
                    placeholder("Textarea with placeholder only")
                }
                """.trimIndent()
            )
        }

        showcaseSection("Event Handling")

        paragraph {
            +"The simplest text area does not need a store, but if you need the events, you have to connect them manually"
            +" in this case. All functions offered by the underlying HTML component can be accessed using the "
            c("base")
            +" context. You can optionally set an initial value for the text content. This short example uses the"
            +" store's data flow for content."
        }

        componentFrame {
            lineUp {
                items {
                    textarea {
                        value { dataStore.data }
                        base {
                            changes.values() handledBy dataStore.update
                        }
                    }
                }
            }
        }

        playground {
            source(
                    """
                textarea {
                    value { dataStore.data }
                    base {
                        changes.values() handledBy dataStore.update
                    }
                }
                """.trimIndent()
            )
        }

        paragraph {
            +"When passing a store to a textarea, the events are connected automatically."
        }

        componentFrame {
            stackUp {
                items {
                    lineUp( {
                        // todo why is this neccessary, it works fine without full width in other examples
                        width { full }
                    }) {
                        items {
                            textarea(store = dataStore) {}
                        }
                    }
                    storeContentBox {
                        b { +"Store value: " }
                        dataStore.data.asText()
                    }
                }
            }
        }

        playground {
            source(
                    """
                 textarea(store = dataStore) {}
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
                    textarea {
                        placeholder("small")
                        size { small }
                    }
                    textarea {
                        placeholder("normal")
                    }
                    textarea {
                        placeholder("large")
                        size { large }
                    }
                }
            }
        }

        playground {
            source(
                    """
                 textarea {
                    placeholder("small")
                    size { small }
                 }
                 
                 textarea {
                    placeholder("normal")
                 }
                 
                 textarea {
                    placeholder("large")
                    size { large }
                 }
            """.trimIndent()
            )
        }

        showcaseSection("Resize Behavior")
        paragraph {
            +"fritz2 offers the following resizing options for text areas: "
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
                    textarea {
                        placeholder("resize: vertical")
                    }
                    textarea {
                        placeholder("resize: horizontal")
                        resizeBehavior { horizontal }
                    }
                    textarea {
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
        paragraph { +"Of course it is possible to disable a text area. This will also skip the tab index for this component." }
        componentFrame {
            lineUp {
                items {
                    textarea {
                        placeholder("disabled")
                        disable(true)
                    }
                }
            }
        }

        playground {
            source(
                    """
                 textArea {
                    placeholder("disabled")
                    disable(true)
                 }
                """.trimIndent()
            )
        }
    }
}