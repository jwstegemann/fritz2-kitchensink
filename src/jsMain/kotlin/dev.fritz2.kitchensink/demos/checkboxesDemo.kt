package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.storeOf
import dev.fritz2.components.checkbox
import dev.fritz2.components.checkboxGroup
import dev.fritz2.components.lineUp
import dev.fritz2.components.stackUp
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
fun RenderContext.checkboxesDemo(): Div {

    return contentFrame {
        showcaseHeader("Checkbox")

        paragraph {
            +"Checkboxes and CheckboxGroups provide a solution for multi selections. They come with their own options, and"
            +" of course you can customize their appearance with the use of the styling parameter."
        }

        paragraph {
            +"Please note that the creation of stores was omitted in some of the examples to keep the source fragments short."
        }

        val demoItems = listOf("item 1", "item 2", "item 3")
        val usageCheckboxStore = storeOf(true)
        val usageCheckboxGroupStore = storeOf(listOf("item 2", "item 3"))
        val customCheckboxStore1 = storeOf(false)
        val customCheckboxStore2 = storeOf(true)
        val customCheckboxStore3 = storeOf(true)
        val customCheckboxStore4 = storeOf(false)
        val sizesCheckboxStore1 = storeOf(false)
        val sizesCheckboxStore2 = storeOf(true)
        val sizesCheckboxStore3 = storeOf(true)

        showcaseSection("Usage")
        paragraph {
            +"Single checkboxes simply need a Flow of Boolean representing their state, passed via the"
            c("checked")
            +" function. If you want to connect a handler to the state changes, use the event context."
        }
        componentFrame {
            checkbox(value = usageCheckboxStore) {
                label("A single Checkbox")
            }
            storeContentBox("Checked") {
                usageCheckboxStore.data.render {
                    span { +it.toString() }
                }
            }
        }
        highlight {
            source(
                """
                val checkBoxStore = storeOf(true)

                checkbox(value = checkBoxStore) {
                    label("A single Checkbox")
                }
                """
            )
        }

        paragraph {
            +" If you need more than one checkbox, try using a checkbox group component. It accepts a Flow of"
            c("List<T>")
            +" as group items, and its selection event returns all currently checked entries."
            +" The example below uses Strings, but any type can be displayed. Since the store is a non-optional"
            +" argument anyway, the component always connects the checked-handler automatically. Using the"
            c("direction")
            +" function, you can display the group in a row or as a column."
        }
        componentFrame {
            checkboxGroup(values = usageCheckboxGroupStore, items = demoItems) {
                direction { row }
            }
            storeContentBox("Checked") {
                usageCheckboxGroupStore.data.render {
                    span { +it.joinToString() }
                }
            }
        }
        highlight {
            source(
                """
                val allItems = listOf("item 1", "item 2", "item 3")
                val checkedItems = storeOf(listOf("item 2", "item 3"))
                checkboxGroup(values = checkedItems, items = allItems) {
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
            +" (default), or  "
            c("large")
            +", or scale your checkboxes to your needs using the styling parameter."
        }

        componentFrame {
            lineUp(
                {
                    alignItems { center }
                }
            ) {
                items {
                    checkbox(value = sizesCheckboxStore1) {
                        label("small")
                        size { small }
                    }
                    checkbox(value = sizesCheckboxStore2) {
                        label("normal")
                    }
                    checkbox(value = sizesCheckboxStore3) {
                        label("large")
                        size { large }
                    }
                }
            }
            storeContentBox("Checked") {
                sizesCheckboxStore1.data.render {
                    if (it) span { +"small " }
                }
                sizesCheckboxStore2.data.render {
                    if (it) span { +"normal " }
                }
                sizesCheckboxStore3.data.render {
                    if (it) span { +"large" }
                }
            }
        }
        highlight {
            source(
            """
                checkbox(value = checkboxStore1) {
                    label("small")
                    size { small }
                }
                checkbox(value = checkboxStore2) {
                    label("normal")
                }
                checkbox(value = checkboxStore3) {
                    label("large")
                    size { large }
                }
                """
            )
        }

        showcaseSection("Customizing")
        paragraph {
            +"You can customize the checked styles, unchecked styles, and the component label. The unchecked"
            +" styles go directly into the styling parameter, while you need to use the component functions "
            c("checkedStyle")
            +" and "
            c("labelStyle")
            +" for their respective changes in appearance."
            +" Additionally, you can replace the checkmark with an icon."
        }

        componentFrame {
            stackUp {
                items {
                    checkbox({
                        background { color { secondary.main } }
                    }, value = customCheckboxStore1) {
                        label("Changed unchecked background color")
                    }

                    checkbox(value = customCheckboxStore2) {
                        label("Changed checked border color")
                        checkedStyle {
                            border { color { secondary.main } }
                        }
                    }

                    checkbox(value = customCheckboxStore3) {
                        label("Changed checkmark to fritz2 icon")
                        icon { fritz2 }
                    }

                    checkbox(value = customCheckboxStore4) {
                        label("Custom label style: larger margin")
                        labelStyle { margins { left { larger } } }
                    }
                }
            }
        }
        highlight {
            source(
                """
                    checkbox({
                        background { color { secondary.main } }
                    }, store = checkboxStore1) {
                        label("Changed unchecked background color")
                    }

                    checkbox(value = checkboxStore2) {
                        label("Changed checked border color")
                        checkedStyle {
                            border { color { secondary.main } }
                        }
                    }

                    checkbox(value = checkboxStore3) {
                        label("Changed checkmark to fritz2 icon")
                        icon { fritz2 }
                    }

                    checkbox(value = checkboxStore4) {
                        label("Custom label style: larger margin")
                        labelStyle { margins { left { larger } } }
                    }
                    """
            )
        }



        showcaseSection("Disabled")
        componentFrame {
            stackUp {
                items {
                    checkbox(value = usageCheckboxStore) {
                        label("A disabled checkbox or checkboxGroup can not be selected.")
                        disabled(true)
                    }
                    checkboxGroup(values = usageCheckboxGroupStore, items = demoItems) {
                        direction { column }
                        disabled(true)
                    }
                }

            }
        }
        highlight {
            source(
                """
                    checkbox(value = usageCheckboxStore) {
                        label("A disabled checkbox or checkboxGroup can not be selected.")
                        disabled(true)
                    }
                    checkboxGroup(values = usageCheckboxGroupStore, items = demoItems) {
                        direction { column }
                        disabled(true)
                    }
                    """
            )
        }
    }
}

