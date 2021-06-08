package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.storeOf
import dev.fritz2.components.*
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.kitchensink.menu_
import dev.fritz2.styling.div
import dev.fritz2.styling.p
import dev.fritz2.styling.span
import dev.fritz2.styling.theme.Theme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.combine

@ExperimentalCoroutinesApi
fun RenderContext.dropdownDemo(): Div {

    return contentFrame {

        showcaseHeader("Dropdown")
        paragraph {
            +"The Dropdown is a non-modal dialog that contains arbitrary content and floats around a toggle element. "
            +"It can be used to display settings and actions in a dropdown similar to those used in traditional "
            +"desktop software, for example in combo-boxes or dropdown-menus. "
            +"The latter can be created as described in the "
            internalLink("Menu", menu_)
            +" demo. "
            +"Dropdowns are automatically closed whenever an outside click occurs, or if another one is opened."
        }

        showcaseSection("Usage")
        paragraph {
            +"Dropdown are created using the "
            c("dropdown")
            +" method."
            +"Follow the example below to create a simple dropdown with a default toggle and a basic demo content. "
        }

        componentFrame {
            dropdown {
                content {
                    p({ margin { smaller } }) {
                        +"Very basic content"
                    }
                }
            }
        }
        highlight {
            source(
                """
                    dropdown {
                        content {
                            p({ margin { smaller } }) {
                                +"Very basic content"
                            }
                        }
                    }
                 """
            )
        }

        showcaseSection("Toggle")
        paragraph {
            +"Any component can be used as a dropdown's toggle and can be specified via the "
            c("toggle")
            +" property. The specified toggle does not need to be clickable by itself as the clicks are handled by the "
            +"dropdown component."
        }

        paragraph {
            +"Please note that, compared to the actual dropdown, the toggle itself can only be styled by providing a "
            +"custom toggle element via the "
            c("toggle")
            +" context. Passing a style to the "
            c("dropdown")
            +" method will style the dropdown."
        }
        componentFrame {
            lineUp {
                items {
                    fun RenderContext.basicContent() {
                        p({ margin { smaller } }) {
                            +"Very basic content"
                        }
                    }

                    dropdown {
                        toggle {
                            pushButton {
                                text("Toggle")
                            }
                        }
                        content { basicContent() }
                    }

                    dropdown {
                        toggle {
                            pushButton {
                                text("Toggle")
                                variant { outline }
                            }
                        }
                        content { basicContent() }
                    }

                    dropdown {
                        toggle {
                            icon {
                                fromTheme { chevronDoubleDown }
                            }
                        }
                        content { basicContent() }
                    }
                }
            }
        }
        highlight {
            source(
                """
                fun RenderContext.basicContent() {
                    p({ margin { smaller } }) {
                        +"Very basic content"
                    }
                }

                dropdown {
                    toggle {
                        pushButton {
                            text("Toggle")
                        }
                    }
                    content { basicContent() }
                }

                dropdown {
                    toggle {
                        pushButton {
                            text("Toggle")
                            variant { outline }
                        }
                    }
                    content { basicContent() }
                }

                dropdown {
                    toggle {
                        icon {
                            fromTheme { chevronDoubleDown }
                        }
                    }
                    content { basicContent() }
                }
                 """
            )
        }

        showcaseSection("Placement and Alignment")

        coloredBox(Theme().colors.info) {
            +"Placements and alignments work differently on mobile devices: The dropdown uses "
            +"all the available width in this case and the configuration is ignored."
        }

        paragraph {
            +"The dropdown's relative position to the toggle can either be to the left, to the right, on top or below "
            +"of it. The possible values are named just like this. By default, the dropdown is rendered "
            i { +"below" }
            +" the toggle. "
            +"The position can be changed via the "
            c("placement")
            +" property."
        }

        paragraph {
            +"The dropdown's "
            c("alignment")
            +" property specifies how the dropdown is laid out on the cross-axis. Possible values are "
            c("start")
            +" or "
            c("end")
            +". If the dropdown's placement is on the vertical axis (top or bottom) the alignment specifies it's "
            +"alignment on the horizontal axis. For the horizontal placements (left or right) the alignment affects "
            +"the vertical axis respectively."
        }

        val placements = DropdownComponent.Placement.values().toList()
        val placementStore = storeOf(placements.last())

        val alignments = DropdownComponent.Alignment.values().toList()
        val alignmentStore = storeOf(alignments.first())

        val placementAlignmentFlow = placementStore.data
            .combine(alignmentStore.data) { placement, alignment -> Pair(placement, alignment) }

        componentFrame {
            flexBox({
                alignContent { center }
            }) {
                label { +"Placement: " }
                radioGroup(items = placements, value = placementStore, styling = {
                    margins { left { small } }
                }) {
                    direction { row }
                }
            }

            flexBox({
                alignContent { center }
            }) {
                label { +"Alignment: " }
                radioGroup(items = alignments, value = alignmentStore, styling = {
                    margins { left { small } }
                }) {
                    direction { row }
                }
            }

            box({
                margins { top { normal } }
            }) {
                placementAlignmentFlow.render {
                    dropdown {
                        toggle {
                            clickButton {
                                icon { fromTheme { menu } }
                                text("Open Dropdown")
                            }
                        }
                        placement { it.first }
                        alignment { it.second }
                        content {
                            div({
                                margin { small }
                            }) {
                                span({
                                    margins {
                                        bottom { smaller }
                                    }
                                }) {
                                    +"Selected values:"
                                }
                                p({
                                    fontFamily { mono }
                                }) {
                                    +"${it.first} ${it.second}"
                                }
                            }
                        }
                    }
                }
            }
        }
        highlight {
            source(
                """
                    dropdown {
                        placement { bottom }
                        alignment { start }
                        content {
                            // ...
                        }
                    }
                    """
            )
        }
    }
}