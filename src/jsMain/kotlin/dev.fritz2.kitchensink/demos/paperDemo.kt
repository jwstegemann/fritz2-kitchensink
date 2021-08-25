package dev.fritz2.kitchensink.demos

import dev.fritz2.components.lineUp
import dev.fritz2.components.paper
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.styling.span
import dev.fritz2.styling.theme.Theme

fun RenderContext.paperDemo() = contentFrame {
    showcaseHeader("Paper")

    paragraph {
        +"Paper is a component that displays content in a container, similar to a sheet of paper. "
    }

    showcaseSection("Usage")
    paragraph {
        +"A "
        c("PaperComponent")
        +" can hold arbitrary content that is specified via the "
        c("content")
        +" property as shown in the example below. "
        +"Additionally there are deifferent types of paper available, changing the look of the paper sheet. Those are "
        +"explained further down the page."
    }
    componentFrame {
        val lipsum = """
            Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore 
            et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. 
            Stet clita kasd gubergren, no sea takimata sanctus est. Lorem ipsum dolor sit amet. Lorem ipsum dolor sit 
            amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna 
            aliquyam erat, sed diam voluptua.
        """.trimIndent()

        paper {
            content {
                span { +lipsum }
            }
        }
    }
    highlight {
        source(
            """
                paper {
                    content {
                        span { /* lorem ipsum text here */ }
                    }
                }
            """.trimIndent()
        )
    }

    showcaseSection("Types")
    paragraph {
        +"There are different types of paper available:"
        ul {
            li {
                c("normal")
                +": Appears raised and with rounded borders (default)"
            }
            li {
                c("outline")
                +": Appears outlined and with rounded corners"
            }
            li {
                c("ghost")
                +": The sheet appears with no styling at all, displaying the content as-is"
            }
        }
    }
    componentFrame {
        lineUp {
            items {
                paper {
                    type { normal }
                    content {
                        span {
                            +"This sheet of paper has the "
                            c("normal")
                            +" type."
                        }
                    }
                }
                paper {
                    type { outline }
                    content {
                        span {
                            +"This sheet of paper has the "
                            c("outline")
                            +" type."
                        }
                    }
                }
                paper {
                    type { ghost }
                    content {
                        span {
                            +"This sheet of paper has the "
                            c("ghost")
                            +" type."
                        }
                    }
                }
            }
        }
    }
    highlight {
        source(
            """
                paper {
                    type { normal } // <-- default, can be omitted
                    content {
                        span {
                            +"This sheet of paper has the "
                            c("normal")
                            +" type."
                        }
                    }
                }
                paper {
                    type { outline }
                    content {
                        span {
                            +"This sheet of paper has the "
                            c("outline")
                            +" type."
                        }
                    }
                }
                paper {
                    type { ghost }
                    content {
                        span {
                            +"This sheet of paper has the "
                            c("ghost")
                            +" type."
                        }
                    }
                }
            """.trimIndent()
        )
    }

    showcaseSection("Sizes")
    paragraph {
        +"Similar to other components the PaperComponent scales by different sizes: "
        c("small")
        +", "
        c("normal")
        +" and "
        c("large")
        +"."
        br {}
        +"Normal is used by default."
    }
    coloredBox(Theme().colors.info) {
        +"Please note: The paddings and corner radii of the PaperComponent scale with the selected size specified via "
        +"the respective property. "
        +"It is your own responsibility to set a matching size for your provided content, however."
    }
    componentFrame {
        lineUp {
            items {
                paper {
                    size { small }
                    content {
                        span({
                            fontSize { small }
                        }) {
                            +"This sheet of paper is sized "
                            c("small")
                            +"."
                        }
                    }
                }
                paper {
                    size { normal }
                    content {
                        span({
                            fontSize { normal }
                        }) {
                            +"This sheet of paper is sized "
                            c("normal")
                            +"."
                        }
                    }
                }
                paper {
                    size { large }
                    content {
                        span({
                            fontSize { large }
                        }) {
                            +"This sheet of paper is sized "
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
                paper {
                    size { small }
                    content {
                        span({
                            fontSize { small }
                        }) {
                            +"This sheet of paper is sized "
                            c("small")
                            +"."
                        }
                    }
                }
                paper {
                    size { normal } // <-- default, can be omitted
                    content {
                        span({
                            fontSize { normal }
                        }) {
                            +"This sheet of paper is sized "
                            c("normal")
                            +"."
                        }
                    }
                }
                paper {
                    size { large }
                    content {
                        span({
                            fontSize { large }
                        }) {
                            +"This sheet of paper is sized "
                            c("large")
                            +"."
                        }
                    }
                }
            """.trimIndent()
        )
    }
}