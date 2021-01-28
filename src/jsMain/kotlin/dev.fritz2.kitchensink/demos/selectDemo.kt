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
    val myOptionsOutline = listOf("Banana", "Strawberry", "Mango")
    val selectedItemOutline = storeOf("")
    val myOptionsFilled = listOf("Guacamole", "Salsa", "Sour Cream")
    val selectedItemFilled = storeOf("")
    val myOptionsSmall = listOf("Some", "things", "are", "little")
    val selectedItemSmall = storeOf("")
    val myOptionsNormal = listOf("some", "are", "boring")
    val selectedItemNormal = storeOf("")
    val myOptionsLarge = listOf("and", "some", "things", "are", "huge")
    val selectedItemLarge = storeOf("")
    val myOptionsCircle = listOf("sunny", "rainy", "cloudy", "snowy")
    val selectedItemCircle = storeOf("")
    val myOptionsArrow = listOf("rabbits", "squirrels", "mice", "foxes")
    val selectedItemArrow = storeOf("")
    val myOptionsDisabled = listOf("day", "night")
    val selectedItemDisabled = storeOf("")




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
                    selectField(store = selectedItem, items = myOptions) {
                        placeholder("placeholder")
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
                    selectField(store = selectedItem, items = myOptions) {
                        placeholder("placeholder")
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
                    selectField<Any>(items = emptyList()) {
                        placeholder("small")
                        size { small }

                    }
                    selectField<Any>(items = emptyList())  {
                        placeholder("normal (default)")
                    }
                    selectField<Any>(items = emptyList())  {
                        placeholder("large")
                        size { large }
                    }
                }

            }
        }

        playground {
            source(
                """
                     selectField(items = myList) {
                        placeholder("small")
                        size { small }
                    }
                     selectField(items = myList) {
                        placeholder("normal (default)")
                    }
                     selectField(items = myList) {
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
                    selectField<Any>(items = emptyList())  {
                        placeholder("outline (default)")
                    }
                    selectField<Any>(items = emptyList())  {
                        placeholder("filled")
                        variant { filled }
                    }
                }
            }
        }

        playground {
            source(
                """
                 selectField(items = myList) {
                    placeholder("outline (default)")
                }
                 selectField(items = myList) {
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
                    selectField<Any>(items = emptyList())  {
                        placeholder("Large with circleAdd-Icon")
                        icon { Theme().icons.circleAdd }
                        size { large }
                    }
                    selectField<Any>(items = emptyList())  {
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
            selectField(items = myList) {
                placeholder("Large with circleAdd-Icon")
                icon { Theme().icons.circleAdd }
                size { large }
            }
            select(items = myList) {
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
                    selectField(store = store, items = persons) {
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
                 selectField(store = store, items = persons) {
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
                    selectField<Any>(items = emptyList())  {
                        placeholder("disabled selectField")
                        disabled(true)
                    }
                }
            }
        }

        playground {
            source(
                """
                 selectField(items = myList) {
                        placeholder("disabled selectField")
                        disabled(true)
                    }
            """.trimIndent()
            )
        }
    }
}