package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.storeOf
import dev.fritz2.components.lineUp
import dev.fritz2.components.stackUp
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import selectField

@ExperimentalCoroutinesApi
fun RenderContext.selectDemo(): Div {

    return contentFrame {

        showcaseHeader("SelectField")

        paragraph {
            +"The SelectField component allows users to pick a single item from a list of items."
        }

        showcaseSection("Usage")
        paragraph {
            +"To use this component, define a "
            c("List<T>")
            +" of your options and pass it to the "
            c("items")
            +" parameter. The currently selected item is managed by an optional "
            c("Store<T>")
            +" that can be passed to the "
            c("store")
            +" parameter. This selection store offers therefore the possibility to preselect an item. "
            +"If no item is preselected, a "
            c("placeholder")
            +" text is shown instead."
        }
        paragraph {
            +"The following example uses simple Strings as type "
            c("<T>")
            +":"
        }

        componentFrame {
            stackUp {
                items {
                    val languages = listOf("javascript", "java", "kotlin", "scala")
                    val selectedLanguage = storeOf("")
                    selectField(items = languages, value = selectedLanguage) {
                        placeholder("Select your favorite programming language")
                    }
                    storeContentBox("Selected") {
                        selectedLanguage.data.render {
                            span { +it }
                        }
                    }
                }
            }
        }


        highlight {
            source(
                """
                val languages = listOf("javascript", "java", "kotlin", "scala")
                val selectedLanguage = storeOf("")
                selectField(items = languages, value = selectedLanguage) {
                    placeholder("Select your favorite programming language")
                }
                """
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
                    selectField<Any>(items = emptyList()) {
                        placeholder("normal (default)")
                    }
                    selectField<Any>(items = emptyList()) {
                        placeholder("large")
                        size { large }
                    }
                }

            }
        }

        highlight {
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
                """
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
                    selectField<Any>(items = emptyList()) {
                        placeholder("outline (default)")
                    }
                    selectField<Any>(items = emptyList()) {
                        placeholder("filled")
                        variant { filled }
                    }
                }
            }
        }

        highlight {
            source(
                """
                selectField(items = myList) {
                    placeholder("outline (default)")
                }
                selectField(items = myList) {
                    placeholder("filled")
                    variant { filled }
                }
                """
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
                    selectField<Any>(items = emptyList()) {
                        placeholder("Large with circleAdd-Icon")
                        icon { circleAdd }
                        size { large }
                    }
                    selectField<Any>(items = emptyList()) {
                        placeholder("Small with arrowDown-Icon")
                        icon { arrowDown }
                        size { small }
                    }
                }
            }
        }
        highlight {
            source(
                """
                selectField(items = myList) {
                    placeholder("Large with circleAdd-Icon")
                    icon { circleAdd }
                    size { large }
                }
                select(items = myList) {
                    placeholder("Small with arrowDown-Icon")
                    icon { arrowDown }
                    size { small }
                }
                """
            )
        }



        showcaseSection("Passing Custom Types")

        paragraph {
            +"As mentioned in the usage section, you can pass items of any type to the select. You can specify how the label is generated (if "
            +" you don't specify anything, the "
            c("toString()")
            +" method of your class will be used). The following example renders a list of persons with name and id."
        }

        componentFrame {

            data class SimplePerson(val name: String = "", val id: Int = 0)

            val persons = listOf(SimplePerson("John Doe", 16), SimplePerson("Jane Smith", 42))
            val store = storeOf(persons[0])

            stackUp {
                items {
                    selectField(value = store, items = persons) {
                        label { it.name }
                    }
                    storeContentBox("Selected") {
                        store.data.render {
                            span { +it.toString() }
                        }
                    }
                }
            }
        }

        highlight {
            source(
                """
                 val persons = listOf(Person("John Doe", 16), Person("Jane Doe", 42))
                 val store = storeOf(persons[0])
                 selectField(value = store, items = persons) {
                     label { it.name } // instead of person.toString(), use name member as label
                 }
                """
            )
        }

        showcaseSection("Disabled SelectField")
        paragraph {
            +"You can easily disable the component by using the "
            c("disabled")
            +" function."
        }

        componentFrame {
            lineUp {
                items {
                    selectField<Any>(items = emptyList()) {
                        placeholder("disabled selectField")
                        disabled(true)
                    }
                }
            }
        }

        highlight {
            source(
                """
                selectField(items = myList) {
                    placeholder("disabled selectField")
                    disabled(true)
                }
                """
            )
        }
    }
}