package dev.fritz2.kitchensink.demos

import dev.fritz2.components.*
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
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
            +" context hosting all the entries of the Menu. In this example it only hosts a single item created via the "
            c("item")
            +" method."
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
                            }
                        }
                    }
                 """
            )
        }

        paragraph {
            +"Other possible children are Subheaders, Dividers and custom components, whith the latter being any fritz2-"
            +"component. "
            +"These are created via the "
            c("subheader")
            +", "
            c("divider")
            +" and "
            c("custom")
            +" contexts respectively:"
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
    }
}