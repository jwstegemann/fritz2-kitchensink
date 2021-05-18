package dev.fritz2.kitchensink.demos

import dev.fritz2.components.*
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.kitchensink.dropdown_
import dev.fritz2.styling.StyleClass
import dev.fritz2.styling.params.BasicParams
import dev.fritz2.styling.params.BoxParams
import dev.fritz2.styling.params.DisplayValues
import dev.fritz2.styling.params.Style
import dev.fritz2.styling.style

fun RenderContext.menuDemo(): Div {

    val listItemStyle = style {
        margins {
            bottom { small }
        }
    }

    val demoMenuStyle: Style<BasicParams> = {
        radius { "6px" }
        boxShadow { raised }
    }

    return contentFrame {
        showcaseSection("Menu")
        paragraph {
            +"A Menu is a component displaying typical menu-entries such as buttons, headers and dividers in a vertical "
            +"view."
        }
        paragraph {
            +"Find a list of the menu entries that are available below:"
            ul {
                li(baseClass = listItemStyle.name) {
                    b { +"Item: " }
                    +"Clickable menu-button consisting of a title and an optional icon. Clicks are exposed via the "
                    c("events")
                    +" context just like with regular buttons. Adding to this, it is also possible to disable an item "
                    +"via the "
                    c("disabled")
                    +" or "
                    c("enabled")
                    +" properties."
                    br { }
                    +"Created via the "
                    c("item")
                    +" context."
                }
                li(baseClass = listItemStyle.name) {
                    b { +"Subheader: " }
                    +"Small headline that can be used to mark the beginning of a group of entries."
                    br { }
                    +"Created via the "
                    c("subheader")
                    +" context."
                }
                li(baseClass = listItemStyle.name) {
                    b { +"Divider: " }
                    +"A thin line that can be used to separate two entries or group multiple entries together"
                    br { }
                    +"Created via the "
                    c("divider")
                    +" method."
                }
                li(baseClass = listItemStyle.name) {
                    b { +"Custom components " }
                    +"that can be any fritz2-component: "
                    br { }
                    +"Created via the "
                    c("custom")
                    +" context."
                }
            }
        }
        paragraph {
            +"A complete example of all the available types of entries is available below."

            box({
                margins { vertical { smaller } }
            }) {
                alert {
                    severity { info }
                    variant { leftAccent }
                    stacking { compact }
                    content(
                        "All menus in this demo are custom styled to appear inside a raised box. This is " +
                                "done to be able to better distinguish the menu from the rest of the page."
                    )
                }
            }
        }
        componentFrame {
            menu(demoMenuStyle) {
                entries {
                    subheader("Items")
                    item {
                        text("Basic item")
                    }
                    item {
                        icon { sun }
                        text("Item with icon")
                    }
                    item {
                        text("Clickable item")
                        icon { notification }
                        events {
                            clicks handledBy toast {
                                placement { bottomRight }
                                hasCloseButton(false)
                                content {
                                    alert {
                                        variant { leftAccent }
                                        stacking { compact }
                                        content("Menu item clicked")
                                    }
                                }
                            }
                        }
                    }
                    item {
                        text("Disabled item")
                        icon { ban }
                        disabled(true)
                    }
                    divider()
                    subheader("Other")
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
                            subheader("Items")
                            item {
                                text("Basic item")
                            }
                            item {
                                text("Item with icon")
                                icon { sun }
                            }
                            item {
                                text("Clickable item")
                                icon { notification }
                                events {
                                    clicks handledBy someHandler
                                }
                            }
                            item {
                                text("Disabled item")
                                icon { ban }
                                disabled(true)
                            }
                            divider()
                            subheader("Other")
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

        showcaseSection("Dropdown Menus")
        paragraph {
            +"Menus are often used together with "
            internalLink("Dropdowns", dropdown_)
            +" to create so called dropdown-menus. Instead of creating a dropdown manually you can simply use the "
            +"builtin "
            c("dropdownMenu")
            +" convenience method. "
            +"It creates a menu wrapped inside a dropdown and can be customized just like a normal menu. "
        }
        componentFrame {
            dropdownMenu {
                entries {
                    item {
                        text("This is a menu item")
                    }
                }
            }
        }
        highlight {
            source(
                """
                    dropdownMenu {
                        entries {
                            item {
                                text("This is a menu item")
                            }
                        }
                    }
                """.trimIndent()
            )
        }
        paragraph {
            +"Additionally, a "
            c("dropdown")
            +" context is available that can be used to customize properties of the underlying dropdown component such "
            +"as it's placement, alignment or toggle element."
        }
        componentFrame {
            dropdownMenu {
                dropdown {
                    placement { right }
                    alignment { start }
                    toggle {
                        clickButton {
                            text("Dropdown-Menu")
                            icon { fromTheme { chevronRight } }
                            variant { outline }
                        }
                    }
                }
                entries {
                    item {
                        text("This is a menu item")
                    }
                }
            }
        }
        highlight {
            source(
                """
                    dropdownMenu {
                        dropdown {
                            placement { right }
                            alignment { start }
                            toggle {
                                clickButton {
                                    text("Dropdown-Menu")
                                    icon { fromTheme { chevronRight } }
                                    variant { outline }
                                }
                            }
                        }
                        entries {
                            item {
                                text("This is a menu item")
                            }
                        }
                    }
                """.trimIndent()
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

            menu(demoMenuStyle) {
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