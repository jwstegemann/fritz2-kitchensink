package dev.fritz2.kitchensink.demos

import dev.fritz2.components.*
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.kitchensink.dropdown_
import dev.fritz2.styling.StyleClass
import dev.fritz2.styling.params.BasicParams
import dev.fritz2.styling.params.BoxParams
import dev.fritz2.styling.params.Style
import dev.fritz2.styling.style
import dev.fritz2.styling.theme.Theme

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
                    b { +"Header: " }
                    +"Small headline that can be used to mark the beginning of a group of entries."
                    br { }
                    +"Created via the "
                    c("header")
                    +" context."
                }
                li(baseClass = listItemStyle.name) {
                    b { +"Divider: " }
                    +"A thin line that can be used to separate two entries or group multiple entries together."
                    br { }
                    +"Created via the "
                    c("divider")
                    +" method."
                }
                li(baseClass = listItemStyle.name) {
                    b { +"Custom components: " }
                    +"Can be any fritz2-component."
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
                coloredBox(Theme().colors.info) {
                    +"All menus in this demo are custom styled to appear inside a raised box. This is "
                    +"done to be able to better distinguish the menu from the rest of the page."
                }
            }
        }
        componentFrame {
            menu(demoMenuStyle) {
                header("Items")
                entry {
                    text("Basic item")
                }
                entry {
                    icon { sun }
                    text("Item with icon")
                }
                entry {
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
                entry {
                    text("Disabled item")
                    icon { ban }
                    disabled(true)
                }
                divider()
                header("Other")
                custom {
                    pushButton {
                        text("I'm a custom entry")
                    }
                }
            }
        }
        highlight {
            source(
                """
                    menu {
                        header("Items")
                        entry {
                            text("Basic item")
                        }
                        entry {
                            icon { sun }
                            text("Item with icon")
                        }
                        entry {
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
                        entry {
                            text("Disabled item")
                            icon { ban }
                            disabled(true)
                        }
                        divider()
                        header("Other")
                        custom {
                            pushButton {
                                text("I'm a custom entry")
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
            +" to create so called dropdown-menus. Just pass a MenuComponent as the dropdown's content as demonstrated "
            +"below:"
        }
        componentFrame {
            dropdown {
                content {
                    menu {
                        entry {
                            text("This is a menu item")
                        }
                    }
                }
            }
        }
        highlight {
            source(
                """
                dropdown {
                    content {
                        menu {
                            entry {
                                text("This is a menu item")
                            }
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
        }
        paragraph {
            +"A contexts can be injected by simply writing an extension method for the "
            c("MenuComponent")
            +" class that adds an implementation of the "
            c("MenuChild")
            +" interface to the menu."
            br { }
            +"You are not required to implement the custom DSL in any specific way. "
            +"It is necessary, however, to call the "
            c("addChild")
            +" method exposed by the receiving class in order for your component to be added to the Menu's list of "
            +"children."
        }
        componentFrame {
            class RadioGroupContext {
                val items = ComponentProperty(listOf<String>())

                fun build() = object : MenuChild {
                    override fun render(context: RenderContext) {
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

            fun MenuComponent.radios(expression: RadioGroupContext.() -> Unit) = RadioGroupContext()
                .apply(expression)
                .build()
                .run(::addChild)

            menu(demoMenuStyle) {
                radios {
                    items(listOf("Item 1", "Item 2", "Item 3"))
                }
            }
        }
        highlight {
            source(
                """
                class RadioGroupContext {
                    val items = ComponentProperty(listOf<String>())
    
                    fun build() = object : MenuChild {
                        override fun render(context: RenderContext) {
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
    
                fun MenuComponent.radios(expression: RadioGroupContext.() -> Unit) = RadioGroupContext()
                    .apply(expression)
                    .build()
                    .run(::addChild)
    
                menu {
                    radios {
                        items(listOf("Item 1", "Item 2", "Item 3"))
                    }
                }
                 """
            )
        }
    }
}