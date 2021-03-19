package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.storeOf
import dev.fritz2.components.lineUp
import dev.fritz2.components.radio
import dev.fritz2.components.radioGroup
import dev.fritz2.components.stackUp
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.dom.states
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
            c("direction")
            +" property, you can display the radios in a row or as a column."
        }
        val languages = listOf("javascript", "java", "kotlin", "scala")
        val pickedLanguage = storeOf(languages[2])
        val usageRadioStore = storeOf(false)

        componentFrame {
            radioGroup(store = pickedLanguage, items = languages) {
                direction { row }
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
               direction { row }
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
            +", or  "
            c("large")
            +", or scale your radios to your needs using the styling DSL."
        }

        val smallStore = storeOf(false)
        val normalStore = storeOf(true)
        val largeStore = storeOf(true)

        componentFrame {
            lineUp(
                {
                    alignItems { center }
                }
            ) {
                items {
                    radio(store = smallStore) {
                        label("small")
                        size { small }
                        selected(smallStore.data)
                        events {
                            changes.states() handledBy smallStore.update
                        }
                    }
                    radio {
                        label("normal")
                        selected(normalStore.data)
                        events {
                            changes.states() handledBy normalStore.update
                        }
                    }
                    radio {
                        label("large")
                        size { large }
                        selected(largeStore.data)
                        events {
                            changes.states() handledBy largeStore.update
                        }
                    }
                }
            }
        }
        highlight {
            source(
                """
                    radio {
                        label("small")
                        size { small }
                    }
                    radio {
                        label("normal")
                    }
                    radio {
                        label("large")
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
            lineUp(switchLayoutSm) {
                items {
                    radio({
                        border { color { secondary.base } }
                    }) {
                        label("Custom unselected style")
                        selected(false)
                    }

                    radio {
                        label("Custom selected style")
                        selected(true)
                        selectedStyle {
                            background { color { secondary.base } }
                        }
                    }

                    radio {
                        label("Custom label style: margin")
                        selected(usageRadioStore.data)
                        labelStyle {
                            margins { left { larger } }
                        }
                        events {
                            changes.states() handledBy usageRadioStore.update
                        }
                    }
                }
            }
        }
        highlight {
            source(
                """
                    radio({
                        border { color { secondary.base } }
                    }) {
                        label("Custom unselected style")
                    }

                    radio {
                        label("Custom selected style")
                        selected(true)
                        selectedStyle { 
                            background { color { secondary.base } }  
                        }
                    }

                    radio {
                        label("Custom label style: margin")
                        labelStyle { 
                            margins { left { larger } } 
                        }     
                    }
                    """
            )
        }

        showcaseSection("Disabled Radios")
        componentFrame {
            stackUp {
                items {
                    radio {
                        label("A disabled Radio or RadioGroup can not be selected.")
                        disabled(true)
                        selected(usageRadioStore.data)
                    }

                    radioGroup(store = pickedLanguage, items = languages) {
                        direction { column }
                        disabled(true)
                    }
                }

            }
        }
        highlight {
            source(
                """
                    radio {
                        label("A disabled Radio or RadioGroup can not be selected.")
                        disabled(true)
                    }
                    
                    radioGroup(store = usageRadioGroupStore, items = demoItems) {
                        direction { column }
                        disabled(true)
                    }
                    """
            )
        }
    }
}