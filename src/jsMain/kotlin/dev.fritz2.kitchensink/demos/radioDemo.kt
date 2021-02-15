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
import kotlinx.coroutines.flow.flowOf


@ExperimentalCoroutinesApi
fun RenderContext.radiosDemo(): Div {

    return contentFrame {
        showcaseHeader("Radio")

        paragraph {
            c("Radios")
            +" and "
            c("RadioGroups")
            +" are single selection components. They come with their own options, and"
            +" of course you can customize their appearance with the use of the styling parameter."
        }

        paragraph {
            +"Please note that the creation of stores was omitted in some of the examples to keep the source fragments short."
        }

        val demoItems = listOf("item 1", "item 2", "item 3")
        val usageRadioStore = storeOf(true)
        val usageRadioGroupStore = storeOf("item 2")

        showcaseSection("Usage")
        paragraph {
            +"Single "
            c("Radios")
            +" do not have a wide range of use cases, but we provide them anyway. You"
            +" need to supply a Flow of "
            c("List<T>")
            +" representing the selected state via the "
            c("selected")
            +" function. If you want to connect a handler to the state changes, use the events context."
        }
        componentFrame {
            radio {
                label("A single Radio")
                selected(usageRadioStore.data)
                events {
                    changes.states() handledBy usageRadioStore.update
                }
            }
        }
        playground {
            source(
                """
                radio {
                    label("A single Radio")
                    selected (usageRadioStore.data)
                    events {
                        changes.states() handledBy usageRadioStore.update
                    }
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
            +", or scale your radios to your needs using the styling parameter."
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
                    radio {
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
        playground {
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

        showcaseSection("RadioGroups And Layouts")
        paragraph {
            +" For most use cases, you will want a radio group. It accepts a Flow of "
            c("List<T>")
            +" as group items, and its selection event returns the currently selected entry instead of Boolean."
            +" The example below uses Strings, but any type can be displayed. Since the store is a non-optional"
            +" argument anyway, the component always connects the selected-handler automatically. Using the "
            c("direction")
            +" parameter, you can display the radios in a row or as a column."
        }
        componentFrame {
            radioGroup(store = usageRadioGroupStore, items = demoItems) {
                direction { row }
            }
        }
        playground {
            source(
                """
                 val allItems = listOf("item 1", "item 2", "item 3")
                 val selectedItem = storeOf("item 2")
                 radioGroup(store = selectedItem, items = allItems) {
                    direction { row }
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
                        border { color { secondary } }
                    }) {
                        label("Custom unselected style")
                        selected(false)
                    }

                    radio {
                        label("Custom selected style")
                        selected(true)
                        selectedStyle {
                            background { color { secondary } }
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
        playground {
            source(
                """
                    radio({
                        border { color { secondary } }
                    }) {
                        label("Custom unselected style")
                    }

                    radio {
                        label("Custom selected style")
                        selected(true)
                        selectedStyle { 
                            background { color { secondary } }  
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

                    radioGroup(store = usageRadioGroupStore, items = demoItems) {
                        direction { column }
                        disabled(true)
                    }
                }

            }
        }
        playground {
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