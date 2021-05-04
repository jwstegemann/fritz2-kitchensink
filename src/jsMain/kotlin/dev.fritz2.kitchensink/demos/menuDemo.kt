package dev.fritz2.kitchensink.demos

import dev.fritz2.components.*
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.styling.StyleClass
import dev.fritz2.styling.params.BoxParams
import dev.fritz2.styling.params.DirectionValues
import dev.fritz2.styling.params.JustifyContentValues
import dev.fritz2.styling.params.WrapValues
import dev.fritz2.styling.theme.Theme
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
fun RenderContext.menuDemo(): Div {

    fun RenderContext.menuBox(expression: RenderContext.() -> Unit) {
        box({
            margin { smaller }
            display { inlineBlock }
        }) {
            expression()
        }
    }

    return contentFrame {

        showcaseHeader("Menu")
        paragraph {
            +"The Menu is a non-modal dialog that contains a list of menu entries and floats around a toggle element. "
            +"It is used to display settings and actions in a drop-down-menu similar to those used in traditional "
            + "desktop software. "
            +"Menus are closed whenever a click outside the dropdown occurs or if another Menu is opened."
        }
        showcaseSection("Usage")
        paragraph {
            +"Menus are created using the "
            c("menu")
            +" method. "
            br { }
            +"Follow the example below to create a simple menu with a default toggle and one single entry. "
            +"You will notice the "
            c("entries")
            +" context hosting all the entries of the Menu. In this example it only has a single item created via the "
            c("item")
            +" method. The latter also exposes a Flow of MouseEvents for clicks to be handled by a store."
        }
        componentFrame {
            menu {
                entries {
                    item {
                        icon { sun }
                        text("Hello world!")
                    }
                }
            }
        }
        highlight {
            source(
                """
                    menu {
                        entries {
                            item {
                                icon { sun }
                                text("Hello world!")
                            } handledBy someStore
                        }
                    }
                 """
            )
        }

        paragraph {
            +"Other possible children are Subheaders, Dividers and custom components, whith the latter being any fritz2-"
            +"component. "
            +"They are created via the "
            c("subheader")
            +", "
            c("divider")
            +" and "
            c("custom")
            +" contexts."
        }
        componentFrame {
            menu {
                entries {
                    subheader("Subheader")
                    item {
                        text("Hello world!")
                    }
                    divider()
                    custom {
                        pushButton {
                            text("I'm a custom entry")
                        }
                    }
                }
            }
        }
        highlight {
            source(
                """
                    menu {
                        entries {
                            subheader("Subheader")
                            item {
                                text("Hello world!")
                            }
                            divider()
                            custom {
                                pushButton {
                                    text("I'm a custom entry")
                                }
                            }
                        }
                    }
                 """
            )
        }

        showcaseSection("Toggle")
        paragraph {
            +"Any component can be used as a Menu's toggle and can be specified via the "
            c("toggle")
            +" method. The specified toggle does not need to be clickable by itself as the clicks are handled by the "
            +"MenuComponent."
        }
        alert({
            margins { top { normal } }
        }) {
            icon { circleInformation }
            variant { leftAccent }
            content("Buttons are good components to use for the toggle as they require very little adjustments " +
                    "to work well with the Menu.")
        }
        componentFrame {
            menuBox {
                menu {
                    toggle {
                        pushButton {
                            text("Toggle")
                        }
                    }
                    entries {
                        item {
                            text("Hello world!")
                        }
                    }
                }
            }

            menuBox {
                menu {
                    toggle {
                        pushButton {
                            text("Toggle")
                            variant { outline }
                        }
                    }
                    entries {
                        item {
                            text("Hello world!")
                        }
                    }
                }
            }

            menuBox {
                menu {
                    toggle {
                        icon {
                            fromTheme { chevronDoubleDown }
                        }
                    }
                    entries {
                        item {
                            text("Hello world!")
                        }
                    }
                }
            }
        }
        highlight {
            source(
                """
                    menu {
                        toggle {
                            pushButton {
                                text("Toggle")
                            }
                        }
                        entries {
                            item {
                                text("Hello world!")
                            }
                        }
                    }
                 """
            )
        }

        showcaseSection("Placement")
        paragraph {
            +"The dropdown's relative position to the toggle can either be to the left of, to the right of, or below "
            +"it. By default, the dropdown is rendered "
            i { +"below" }
            +" the toggle and growing to the right if needed ("
            i { +"right-facing" }
            +"). "
            +"The position can be changed via the "
            c("placement")
            +" property."
        }
        componentFrame {
            menuBox {
                menu {
                    toggle {
                        pushButton {
                            icon {
                                fromTheme { arrowLeft }
                            }
                            text("Left")
                        }
                    }
                    placement { left }
                    entries {
                        item {
                            text("Dropdown to the left")
                        }
                    }
                }
            }

            menuBox {
                menu {
                    toggle {
                        pushButton {
                            icon {
                                fromTheme { arrowLeftDown }
                            }
                            text("Bottom (left-facing)")
                        }
                    }
                    placement { bottomLeftFacing }
                    entries {
                        item {
                            text("Dropdown below")
                        }
                    }
                }
            }

            menuBox {
                menu {
                    toggle {
                        pushButton {
                            icon {
                                fromTheme { arrowRightDown }
                            }
                            text("Default: Bottom (right-facing)")
                        }
                    }
                    placement { bottomRightFacing }
                    entries {
                        item {
                            text("Dropdown below")
                        }
                    }
                }
            }

            menuBox {
                menu {
                    toggle {
                        pushButton {
                            icon {
                                fromTheme { arrowRight }
                            }
                            text("Right")
                        }
                    }
                    placement { right }
                    entries {
                        item {
                            text("Dropdown to the right")
                        }
                    }
                }
            }
        }
        highlight {
            source(
                """
                menu {
                    toggle {
                        pushButton {
                            icon {
                                fromTheme { chevronLeft }
                            }
                            text("Left")
                        }
                    }
                    placement { left }
                    
                    // entries go here
                }
                 """
            )
        }

        showcaseSection("Extending the DSL")
        paragraph {
            +"The Menu's DSL can be extended in such a way that the "
            c("entries")
            +" context supports arbitrary fritz2 components natively. "
            +"This can be handy when a specific, non-standard component is repeatedly used as a menu entry throughout "
            +"a project and the "
            c("custom")
            +" context would produce a lot of boilerplate code."
            br { }
            br { }
            +"A contexts can be injected by simply writing an extension method for the "
            c("MenuEntriesContext")
            +" class that adds a subclass of "
            c("MenuEntryComponent")
            +" to the menu."
            +"You are not required to implement the custom DSL in any specific way. "
            +"It is necessary, however, to call the "
            c("addItem")
            +" method exposed by the receiving class in order for your component to be added to the Menu's list of "
            +"entries."
        }
        componentFrame {
            class RadioGroupContext {
                val items = ComponentProperty(listOf<String>())

                fun build() = object : MenuEntryComponent {
                    override fun render(
                        context: RenderContext,
                        styling: BoxParams.() -> Unit,
                        baseClass: StyleClass,
                        id: String?,
                        prefix: String
                    ) {
                        context.apply {
                            radioGroup(items = items.value, styling = {
                                margins {
                                    horizontal { small }
                                    vertical { smaller }
                                }
                            })
                        }
                    }
                }
            }

            fun MenuEntriesContext.radios(expression: RadioGroupContext.() -> Unit) = RadioGroupContext()
                .apply(expression)
                .build()
                .run(::addEntry)

            menu {
                entries {
                    radios {
                        items(listOf("Item 1", "Item 2", "Item 3"))
                    }
                }
            }
        }
        highlight {
            source(
                """
                // Custom context to set up a radio-group from within the DSL:
                class RadioGroupContext {
                    val items = ComponentProperty(listOf<String>())
    
                    fun build() = object : MenuEntryComponent {
                        override fun render(
                            context: RenderContext,
                            styling: BoxParams.() -> Unit,
                            baseClass: StyleClass,
                            id: String?,
                            prefix: String
                        ) {
                            context.apply {
                                radioGroup(items = items.value, styling = {
                                    margins {
                                        horizontal { small }
                                        vertical { smaller }
                                    }
                                })
                            }
                        }
                    }
                }
    
                // Function to actually configure and add a radio group to the Menu:
                fun MenuEntriesContext.radios(expression: RadioGroupContext.() -> Unit) = RadioGroupContext()
                    .apply(expression)
                    .build()
                    .run(::addEntry)
    
                menu {
                    entries {
                        // The extended DSL is now available:
                        radios {
                            items(listOf("Item 1", "Item 2", "Item 3"))
                        }
                    }
                }
                 """
            )
        }
    }
}