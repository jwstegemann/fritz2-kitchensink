package dev.fritz2.kitchensink.demos

import dev.fritz2.components.card
import dev.fritz2.components.lineUp
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*

fun RenderContext.cardDemo(): Div = contentFrame {
    showcaseHeader("Card")

    paragraph {
        +"Cards are components that display a text or other content in a card-like container."
        +"Additionally, they can also contain dedicated header and footer sections."
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
                    size { normal }
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