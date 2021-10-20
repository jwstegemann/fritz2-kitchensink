package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.storeOf
import dev.fritz2.components.flexBox
import dev.fritz2.components.radioGroup
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
fun RenderContext.radiosDemo(): Div {

    return contentFrame {
        showcaseHeader("RadioGroup")

        paragraph {
            +"The RadioGroup component allows users to pick a single item from a list of items."
        }

        showcaseSection("Usage")
        paragraph {
            +"To use this component, define a "
            c("List<T>")
            +" of your options and pass it to the "
            c("items")
            +" parameter. The currently picked item is managed by an optional "
            c("Store<T>")
            +" that can be passed to the "
            c("store")
            +" parameter. This selection store offers therefore the possibility to preselect an item."
            +"Using the "
            c("orientation")
            +" property, you can display the radios in a row or as a column."
        }
        val languages = listOf("javascript", "java", "kotlin", "scala")
        val pickedLanguage = storeOf(languages[2])

        componentFrame {
            radioGroup(value = pickedLanguage, items = languages) {
                orientation { horizontal }
            }
            storeContentBox("Selected") {
                pickedLanguage.data.render {
                    span { +it }
                }
            }
        }
        highlight {
            source(
            """
            val languages = listOf("javascript", "java", "kotlin", "scala")
            val pickedLanguage = storeOf(languages[2])
            radioGroup(items = languages, store = pickedLanguage) {
               orientation { horizontal }
            }
            """
            )
        }

        showcaseSection("Sizes")
        paragraph {
            +"Choose from the three predefined sizes "
            c("small")
            +", "
            c("normal")
            +", "
            c("large")
            +" or scale your radios to your needs using the styling DSL."
        }

        val choices = listOf("good", "bad")

        componentFrame {
            flexBox({
                width { full }
                justifyContent { spaceAround }
            }) {
                radioGroup(value = storeOf(""), items = choices) {
                    size { small }
                }
                radioGroup(value = storeOf(""), items = choices) {
                    size { normal }
                }
                radioGroup(value = storeOf(""), items = choices) {
                    size { large }
                }
            }
        }
        highlight {
            source(
                """
                    val choices = listOf("good", "bad")
                    
                    radioGroup(value = storeOf(""), items = choices) {
                        size { small }
                    }
                    radioGroup(value = storeOf(""), items = choices) {
                        size { normal }
                    }
                    radioGroup(value = storeOf(""), items = choices) {
                        size { large }
                    }
                    """
            )
        }

        showcaseSection("Customizing")
        paragraph {
            +"You can customize the selected styles, unselected styles, and the component label. The unselected"
            +" styles go directly into the styling parameter, while you need to use the component functions "
            c("selectedStyle")
            +" and "
            c("labelStyle")
            +" for their respective changes in appearance."
        }

        componentFrame {
            radioGroup({
                border { color { secondary.main } }
            }, value = pickedLanguage, items = languages, id = "customizedLanguages") {
                selectedStyle {
                    background { color { secondary.highlight } }
                }
                labelStyle {
                    margins { left { larger } }
                }
            }
        }
        highlight {
            source(
                """
                radioGroup({
                    border { color { secondary.main } }
                }, value = pickedLanguage, items = languages) {
                    selectedStyle {
                        background { color { secondary.highlight } }
                    }
                    labelStyle {
                        margins { left { larger } }
                    }
                }
                """
            )
        }

        showcaseSection("Disabled Radios")
        componentFrame {
            radioGroup(value = pickedLanguage, items = languages, id = "disabledLanguages") {
                disabled(true)
            }
        }
        highlight {
            source(
                """                    
                radioGroup(store = pickedLanguage, items = languages) {
                    disabled(true)
                }
                """
            )
        }
    }
}