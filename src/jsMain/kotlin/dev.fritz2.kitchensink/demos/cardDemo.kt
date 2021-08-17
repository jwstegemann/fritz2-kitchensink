package dev.fritz2.kitchensink.demos

import dev.fritz2.components.card
import dev.fritz2.components.lineUp
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.styling.params.BasicParams
import dev.fritz2.styling.params.Style
import dev.fritz2.styling.theme.Theme

private val demoCardStyle: Style<BasicParams> = {
    background { color { background } }
    boxShadow { raised }
    radius { large }
    padding { small }
}

fun RenderContext.cardDemo(): Div = contentFrame {
    showcaseHeader("Card")

    paragraph {
        +"Cards provide a layout consisting of a header, content and a footer."
    }

    coloredBox(Theme().colors.info) {
        +"All cards in this demo are custom styled to appear inside a raised box. This is "
        +"done to be able to better distinguish them from the rest of the page."
    }

    showcaseSection("Usage")
    paragraph {
        +"A card can easily be created via the "
        c("card")
        +" factory method. "
        +"Use the "
        c("header")
        +", "
        c("footer")
        +" and "
        c("content")
        +" properties to specify the card's content. "
        +"Cards can contain both display String and Flows of Strings, as well as any other fritz2 content."
    }
    componentFrame {
        lineUp {
            items {
                card(demoCardStyle) {
                    header("Header")
                    content("This is a card with both a header and a footer.")
                    footer("Footer")
                }

                card(demoCardStyle) {
                    header("Header")
                    content("This card has no footer.")
                }

                card(demoCardStyle) {
                    content("This card has no header.")
                    footer("Footer")
                }

                card(demoCardStyle) {
                    content("This card has no header or footer.")
                }
            }
        }
    }
    highlight {
        source(
            """
                card {
                    header("Header")
                    content("This is a card with both a header and a footer.")
                    footer("Footer")
                }

                card {
                    header("Header")
                    content("This card has no footer.")
                }

                card {
                    content("This card has no header.")
                    footer("Footer")
                }

                card {
                    content("This card has no header or footer.")
                }
            """.trimIndent()
        )
    }

    showcaseSection("Sizes")
    paragraph {
        +"Cards can come in different sizes: Small, Normal and Large. "
        +"Use the "
        c("size")
        +" property to specify the card's size. Normal is used by default."
    }
    componentFrame {
        lineUp {
            items {
                card(demoCardStyle) {
                    size { small }
                    header("Small")
                    content {
                        span {
                            +"This is a card with size "
                            c("small")
                            +"."
                        }
                    }
                }
                card(demoCardStyle) {
                    size { normal }
                    header("Normal (default)")
                    content {
                        span {
                            +"This is a card with size "
                            c("normal")
                            +"."
                        }
                    }
                }
                card(demoCardStyle) {
                    size { large }
                    header("Large")
                    content {
                        span {
                            +"This is a card with size "
                            c("large")
                            +"."
                        }
                    }
                }
            }
        }
    }
    highlight {
        source(
            """
                card {
                    size { small }
                    header("Small card")
                    content {
                        span {
                            +"This is a card with size "
                            c("small")
                            +"."
                        }
                    }
                }
                card {
                    size { normal } // <-- can be omitted (default)
                    header("Normal card (default)")
                    content {
                        span {
                            +"This is a card with size "
                            c("normal")
                            +"."
                        }
                    }
                }
                card {
                    size { large }
                    header("Large card")
                    content {
                        span {
                            +"This is a card with size "
                            c("large")
                            +"."
                        }
                    }
                }
            """.trimIndent()
        )
    }
}