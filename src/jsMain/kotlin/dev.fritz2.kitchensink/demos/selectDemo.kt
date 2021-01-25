package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.storeOf
import dev.fritz2.components.lineUp
import dev.fritz2.components.stackUp
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.styling.theme.Theme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import selectField

data class Person(val name: String = "", val id: Int = 0)

@ExperimentalCoroutinesApi
fun RenderContext.selectDemo(): Div {
    val myOptions = listOf("fr", "it", "z2")
    val selectedItem = storeOf("")



    return contentFrame {

        showcaseHeader("SelectField")
        paragraph { +"The SelectField component allows users to pick a single value from a list of predefined items." }

        showcaseSection("Usage")
        paragraph {
            +"To use this component, define a "
            c("List<T>")
            +" of your options and pass it to the "
            c("items")
            +" function. The currently selected item needs to be passed to the "
            c("store: Store<T>")
            +" parameter. You can also define a placeholder of type <T>. The following example uses simple Strings:"
        }

        componentFrame {
            stackUp {
                items {
                    selectField(store = selectedItem) {
                        placeholder("placeholder")
                        items(myOptions)
                    }
                    storeContentBox {
                        p {
                            b { +"Selected: " }
                            selectedItem.data.render {
                                span { +it }
                            }
                        }
                    }
                }
            }
        }


        playground {
            source(
                """
                    selectField(store = selectedItem) {
                        placeholder("placeholder")
                        items(myOptions)
                    }

                    div { // styling omitted
                        selectedItem.data.render { 
                            span { +"Selected: '${'$'}it'" }
                        }
                    }
            """.trimIndent()
            )
        }

        showcaseSection("Sizes")
        paragraph {
            +"Our select component offers three predefined sizes: "
            c("small")
            +", "
            c("normal")
            +" (default), or "
            c("large")
        }

        componentFrame {
            stackUp {
                items {
                    selectField(store = selectedItem) {
                        placeholder("small")
                        size { small }

                    }
                    selectField(store = selectedItem) {
                        placeholder("normal (default)")
                    }
                    selectField(store = selectedItem) {
                        placeholder("large")
                        size { large }
                    }
                }

            }
        }

        playground {
            source(
                """
                     selectField(store = selectedItem) {
                        placeholder("small")
                        size { small }
                    }
                     selectField(store = selectedItem) {
                        placeholder("normal (default)")
                    }
                     selectField(store = selectedItem) {
                        placeholder("large")
                        size { large }
                    }
            """.trimIndent()
            )
        }

        showcaseSection("Select Variants")
        paragraph {
            +"You can choose between the variants "
            c("outline")
            +" or "
            c("filled")
            +" for the selectField:"

        }
        componentFrame {
            stackUp {
                items {
                    selectField(store = selectedItem) {
                        placeholder("outline (default)")
                    }
                    selectField(store = selectedItem) {
                        placeholder("filled")
                        variant { filled }
                    }
                }
            }
        }

        playground {
            source(
                """
                 selectField(store = selectedItem) {
                    placeholder("outline (default)")
                }
                 selectField(store = selectedItem) {
                    placeholder("filled")
                    variant { filled }
                }
            """.trimIndent()
            )
        }

        showcaseSection("Change the Icon")
        paragraph {
            +"You can change the icon for your selectField by"
            +" passing one of our provided icons to the "
            c("icon")
            +" function. The icons will be resized with the component:"
        }
        componentFrame {
            stackUp {
                items {
                    selectField(store = selectedItem) {
                        placeholder("Large with circleAdd-Icon")
                        icon { Theme().icons.circleAdd }
                        size { large }
                    }
                    selectField(store = selectedItem) {
                        placeholder("Small with arrowDown-Icon")
                        icon { Theme().icons.arrowDown }
                        size { small }
                    }
                }
            }
        }
        playground {
            source(
                """
            selectField(store = selectedItem) {
                placeholder("Large with circleAdd-Icon")
                icon { Theme().icons.circleAdd }
                size { large }
            }
            select(store = selectedItem) {
                placeholder("Small with arrowDown-Icon")
                icon { Theme().icons.arrowDown }
                size { small }
            }
                """.trimIndent()
            )
        }



        showcaseSection("Passing Custom Types")

        paragraph {
            +"As mentioned in the usage section, you can pass items of any type to the select. You can specify how the label is generated (if "
            +" you don't specify anything, the "
            c( "toString()")
            +" method of your class will be used). The following example renders a list of persons with name and id."


        }

        componentFrame {

            val persons = listOf(Person("John Doe", 16), Person("Jane Smith", 42))
            val store = storeOf(persons[0])

            stackUp {
                items {
                    selectField(store = store) {
                        items(persons)
                        label { it.name }
                    }
                    storeContentBox {
                        p {
                            b { +"Selected: " }
                            store.data.render {
                                span { +it.name }
                            }
                        }
                    }
                }
            }
        }

        playground {
            source(
                """
                 val persons = listOf(Person("John Doe", 16), Person("Jane Doe", 42))
                 val store = storeOf(persons[0])
                 selectField(store = store) {
                        items(persons)
                        label { it.name } // instead of person.toString(), use name member as label
                 }
                """.trimIndent()
            )
        }

        showcaseSection("Disabled SelectField")
        paragraph {
            +"You can easily disable the component by using the"
            c("disabled")
            +" function."
        }

        componentFrame {
            lineUp {
                items {
                    selectField(store = selectedItem) {
                        placeholder("disabled selectField")
                        items(myOptions)
                        disabled(true)
                    }
                }
            }
        }

        playground {
            source(
                """
                 selectField(store = selectedItem) {
                        placeholder("disabled selectField")
                        options(myOptions)
                        disabled(true)
                    }
            """.trimIndent()
            )
        }
    }
}