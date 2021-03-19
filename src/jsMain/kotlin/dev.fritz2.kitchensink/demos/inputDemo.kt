package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.storeOf
import dev.fritz2.components.inputField
import dev.fritz2.components.stackUp
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.kitchensink.buttons_
import dev.fritz2.kitchensink.checkbox_
import dev.fritz2.kitchensink.formcontrol_
import dev.fritz2.kitchensink.radio_
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
fun RenderContext.inputDemo(): Div {

    val textStore = storeOf("")

    return contentFrame {
        showcaseHeader("InputField")

        paragraph {
            +"""
            Input fields come in different sizes and variants, all of which can be customized with your own styling. 
            All input fields give you access to the underlying input's events and other properties, like placeholder.
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
            +"A basic input field can be created by passing a store into the factory method, so the input component "
            +"connects with the store's handler automatically."
        }

        componentFrame {
            inputField(store = textStore) {
                placeholder("Enter text")
            }
            storeContentBox {
                p {
                    b { +"Text: " }
                    textStore.data.render { span { +it } }
                }
            }
        }
        highlight {
            source(
                """
                val userNameStore = storeOf("")
                inputField(store = userNameStore) {
                    placeholder("Enter text")
                }
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
                        placeholder("small")
                    }
                    inputField {
                        placeholder("normal (default)")
                    }
                    inputField {
                        size { large }
                        placeholder("large")
                    }
                }
            }
        }
        highlight {
            source(
                """
                inputField {
                    size { small }
                    placeholder("small")
                }
                inputField {
                    placeholder("normal (default)")
                }
                inputField {
                    size { large }
                    placeholder("large")
                }
                """
            )
        }

        showcaseSection("Variants")
        paragraph {
            +"You can currently choose between two variants for input fields, "
            c("outline")
            +" (default) and "
            c("filled")
            +"."
        }
        componentFrame {
            stackUp {
                items {
                    inputField {
                        value("outline")
                    }
                    inputField {
                        variant { filled }
                        value("filled")
                    }
                }
            }
        }
        highlight {
            source(
                """
                    inputField {
                        value("outline")
                    }
                    inputField {
                        variant { filled }
                        value("filled")
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
                        value("readonly")
                        readonly(true)
                    }
                    inputField {
                        value("disabled (skips tab)")
                        disabled(true)
                    }
                }
            }
        }
        highlight {
            source(
                """
                    inputField {
                        value("readonly")
                        readonly(true)
                    }
                    inputField {
                        value("disabled (skips tab)")
                        disabled(true)
                    }
                """
            )
        }


        showcaseSection("Input Types")
        paragraph {
            +"You can specify any input type for the component. ("
            internalLink(
                "radios",
                radio_
            )
            +", "
            internalLink(
                "checkboxes",
                checkbox_
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
                        type("password")
                        placeholder("password")
                    }
                    inputField {
                        type("date")
                        placeholder("date")
                    }
                    inputField {
                        type("number")
                        placeholder("42")
                        step("1")
                    }
                }
            }
        }
        highlight {
            source(
                """
                inputField {
                    type("password")
                    placeholder("password")
                }
                inputField {
                    type("date")
                    placeholder("date")
                }
                inputField {
                    type("number")
                    placeholder("42")
                    step("1")
                }
                """
            )
        }
    }
}