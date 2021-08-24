package dev.fritz2.kitchensink.demos

import dev.fritz2.components.card
import dev.fritz2.components.lineUp
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.kitchensink.paper_

fun RenderContext.cardDemo(): Div = contentFrame {
    showcaseHeader("Card")

    paragraph {
        +"Cards arrange text or any other fritz2 content inside a "
        internalLink("PaperComponent", paper_)
        +" and consist of dedicated header, footer and content sections."
    }

    showcaseSection("Usage")
    paragraph {
        +"A card can be created via the "
        c("card")
        +" factory method. "
        +"Use the "
        c("header")
        +", "
        c("footer")
        +" and "
        c("content")
        +" properties to specify the card's content. "
        +"Cards can both display Strings and Flows of Strings, as well as any other fritz2 content."
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

    showcaseSection("Types")
    paragraph {
        +"Since cards utilize paper they can have different appearances based on types. "
        +"It uses the same types as the "
        internalLink("PaperComponent", paper_)
        +": "
        c("normal")
        +", "
        c("outline")
        +" and "
        c("ghost")
        +". "
        +" The card's type can be specified via it's "
        c("type")
        +" property. Normal is used by default."
    }
    componentFrame {
        lineUp {
            items {
                card {
                    type { normal }
                    header("Normal card (default)")
                    content {
                        span {
                            +"Card with type "
                            c("normal")
                        }
                    }
                }
                card {
                    type { outline }
                    header("Outline card")
                    content {
                        span {
                            +"Card with type "
                            c("outline")
                        }
                    }
                }
                card {
                    type { ghost }
                    header("Ghost card")
                    content {
                        span {
                            +"Card with type "
                            c("ghost")
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
                    type { normal } // <- default, can be omitted
                    header("Normal card (default)")
                    content {
                        span {
                            +"Card with type "
                            c("normal")
                        }
                    }
                }
                card {
                    type { outline }
                    header("Outline card")
                    content {
                        span {
                            +"Card with type "
                            c("outline")
                        }
                    }
                }
                card {
                    type { ghost }
                    header("Ghost card")
                    content {
                        span {
                            +"Card with type "
                            c("ghost")
                        }
                    }
                }
            """.trimIndent()
        )
    }

    showcaseSection("Sizes")
    paragraph {
        +"Cards can come in different sizes: "
        c("small")
        +", "
        c("normal")
        +" and "
        c("large")
        +". "
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