package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.storeOf
import dev.fritz2.components.inputField
import dev.fritz2.components.stackUp
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.dom.values
import dev.fritz2.kitchensink.base.*
import dev.fritz2.kitchensink.buttons_
import dev.fritz2.kitchensink.checkboxes_
import dev.fritz2.kitchensink.formcontrol_
import dev.fritz2.kitchensink.radios_
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
fun RenderContext.inputDemo(): Div {

    val userNameStore = storeOf("")

    return contentFrame {
        showcaseHeader("Input Field")

        paragraph {
            +"""
            Input fields come in different sizes and variants, all of which can be customized with your own styling. 
            The simplest form does not even need a store. All input fields give you access to the underlying input's 
            events and other properties, like placeholder.
            """.trimIndent()
        }

        paragraph {
            +"""Please note: Should you require labels or validation, use an input field with a """
            internalLink("FormControl", formcontrol_)
            +" "
            +"""component. A standalone input field does not offer those functions.
            """.trimIndent()
        }

        showcaseSection("Usage")
        paragraph {
            +"A basic input field can be created without a store, but you have to manually connect the handlers in"
            +" this case. Every input offers the sub context"
            c(" base")
            +", where you can access the underlying input's properties."
        }
        componentFrame {
            stackUp {
                items {
                    inputField {
                        base {
                            changes.values() handledBy userNameStore.update
                            placeholder("Enter text")
                        }
                    }
                }
            }
        }
        playground {
            source(
                """
                val userNameStore = storeOf("")
                inputField {
                    base {
                        placeholder("Enter text")
                        changes.values() handledBy userNameStore.update
                    }
                }
                """
            )
        }
        paragraph {
            +"When providing the store parameter, the input component connects with the store's handler automatically."
            +" The following example is a one-liner: "
        }
        componentFrame {
            stackUp {
                items {
                    inputField(store = userNameStore)
                }
            }
        }
        playground {
            source(
                """
                inputField(store = userNameStore)
                """
            )
        }


        showcaseSection("Sizes")
        paragraph {
            +"Use one of our predefined sizes for your input:"
            c("small")
            +", "
            c("normal")
            +" (default), or  "
            c("large")
            +"."
        }
        componentFrame {
            stackUp {
                items {
                    inputField {
                        size { small }
                        base {
                            placeholder("small")
                        }
                    }
                    inputField {
                        base {
                            placeholder("normal (default)")
                        }
                    }
                    inputField {
                        size { large }
                        base {
                            placeholder("large")
                        }
                    }
                }
            }
        }
        playground {
            source(
                """
                 inputField {
                    size { small }
                    base {
                        placeholder("small")
                    }
                }
                inputField {
                    base {
                        placeholder("normal (default)")
                    }
                }
                inputField {
                    size { large }
                    base {
                        placeholder("large")
                    }
                }
                """
            )
        }

        showcaseSection("Variants")
        paragraph {
            +"You can currently choose between two variants for input fields, outline (default) and filled."
        }
        componentFrame {
            stackUp {
                items {
                    inputField {
                        base {
                            placeholder("outline")
                        }
                    }
                    inputField {
                        variant { filled }
                        base {
                            placeholder("filled")
                        }
                    }
                }
            }
        }
        playground {
            source(
                """
                    inputField {
                        base {
                            placeholder("outline")
                        }
                    }
                    inputField {
                        variant { filled }
                        base {
                            placeholder("filled")
                        }
                    }
                """
            )
        }

        showcaseSection("Readonly And Disabled Inputs")
        paragraph {
            +"There are two options to prevent users from editing the content of an input. The "
            c("readonly")
            +" option lives up to its name and simply does not allow text input. If you want to take it further, use "
            c("disabled")
            +" instead, which additionally skips the focus for this component when the user tabs through the page or"
            +" selects the input."
        }
        componentFrame {
            stackUp {
                items {
                    inputField {
                        base {
                            value("readonly")
                            readOnly(true)
                        }
                    }
                    inputField {
                        base {
                            value("disabled (skips tab)")
                            disabled(true)
                        }
                    }
                }
            }
        }
        playground {
            source(
                """
                    inputField {
                        base {
                            value("readonly")
                            readOnly(true)
                        }
                    }
                    inputField {
                        base {
                            value("disabled (skips tab)")
                            disabled(true)
                        }
                    }
                """
            )
        }


        showcaseSection("Input Types")
        paragraph {
            +"You can specify any input type for the component. ("
            internalLink(
                "radios",
                radios_
            )
            +", "
            internalLink(
                "checkboxes",
                checkboxes_
            )
            +", and "
            internalLink(
                "buttons",
                buttons_
            )
            +" however are different components). Some examples for password, date, and number inputs:"

        }
        componentFrame {
            stackUp {
                items {
                    inputField {
                        base {
                            type("password")
                            placeholder("password")
                        }
                    }
                    inputField {
                        base {
                            type("date")
                            placeholder("date")
                        }
                    }
                    inputField {
                        base {
                            type("number")
                            placeholder("42")
                            step("1")
                        }
                    }
                }
            }
        }
        playground {
            source(
                """
                inputField {
                    base {
                        type("password")
                        placeholder("password")
                    }
                }
                inputField {
                    base {
                        type("date")
                    }
                }
                inputField {
                    base {
                        type("number")
                        placeholder("42")
                        step("42")
                    }
                }
                """
            )
        }
    }
}