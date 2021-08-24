package dev.fritz2.kitchensink.demos

import dev.fritz2.components.lineUp
import dev.fritz2.components.paper
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*

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
        paper {
            content {
                span { +"This is a sheet of paper." }
            }
        }
    }
    highlight {
        source(
            """
                paper {
                    content {
                        span { +"This is a sheet of paper." }
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
                            +"Type: "
                            c("normal")
                        }
                    }
                }
                paper {
                    type { outline }
                    content {
                        span {
                            +"Type: "
                            c("outline")
                        }
                    }
                }
                paper {
                    type { ghost }
                    content {
                        span {
                            +"Type: "
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
                paper { 
                    type { normal }  // <-- default, can be omitted
                    content {
                        span { 
                            +"Type: "
                            c("normal")
                        }
                    }
                }
                paper {
                    type { outline }
                    content {
                        span {
                            +"Type: "
                            c("outline")
                        }
                    }
                }
                paper {
                    type { ghost }
                    content {
                        span {
                            +"Type: "
                            c("ghost")
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
    componentFrame {
        lineUp {
            items {
                paper {
                    size { small }
                    content {
                        span {
                            +"Size: "
                            c("small")
                        }
                    }
                }
                paper {
                    size { normal } // <-- default, can be omitted
                    content {
                        span {
                            +"Size: "
                            c("normal")
                        }
                    }
                }
                paper {
                    size { large }
                    content {
                        span {
                            +"Size: "
                            c("large")
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
                        span {
                            +"Size: "
                            c("small")
                        }
                    }
                }
                paper {
                    size { normal } // <-- default, can be omitted
                    content {
                        span {
                            +"Size: "
                            c("normal")
                        }
                    }
                }
                paper {
                    size { large }
                    content {
                        span {
                            +"Size: "
                            c("large")
                        }
                    }
                }
            """.trimIndent()
        )
    }
}