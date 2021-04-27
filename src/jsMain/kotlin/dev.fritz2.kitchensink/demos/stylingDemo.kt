package dev.fritz2.kitchensink.demos

import dev.fritz2.components.icon
import dev.fritz2.components.lineUp
import dev.fritz2.components.pushButton
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.kitchensink.theme_
import dev.fritz2.styling.params.BasicParams
import dev.fritz2.styling.params.Style
import dev.fritz2.styling.params.styled
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
fun RenderContext.stylingDemo(): Div {

    return contentFrame {

        showcaseHeader("Styling")

        paragraph {
            +"""
            fritz2 allows you to style its components, as well as standard HTML-elements, using a type-safe DSL which 
            enables you to conveniently set the most common style properties.""".trimIndent()
        }

        paragraph {
            +"""These style properties are connected to sets or scales of predefined values which are grouped in 
            a """
            internalLink("Theme", theme_)
            +""". We heavily rely on the ideas and concepts of """.trimMargin()
            externalLink("Styled Systems", "https://styled-system.com")
            +""" here."""
        }

        showcaseSection("Styling Elements")
        paragraph {
            +"To style an existing HTML-element like "
            c("span")
            +", define your properties using the "
            c("styled()")
            +" function."
        }

        componentFrame {
            lineUp {
                items {
                    (::span.styled {
                        color { secondary.mainContrast }
                        background { color { secondary.main } }
                        fontWeight { bold }
                        boxShadow { raised }
                        padding { normal }
                        radius { small }
                        hover {
                            color { secondary.highlightContrast }
                            background { color { secondary.highlight } }
                        }
                    }) { +"Raised Text" }
                }
            }
        }
        highlight {
            source(
                """
                (::span.styled {
                    color { secondary.mainContrast }
                    background { color { secondary.main } }
                    fontWeight { bold }
                    boxShadow { raised }
                    padding { normal }
                    radius { small }
                    hover {
                        color { secondary.highlightContrast }
                        background { color { secondary.highlight } } 
                    }
                }) { +"Raised Text" }
                """
            )
        }

        paragraph {
            +"To remain as flexible as possible, values of properties can alternatively be passed as "
            c("String")
            +", like "
            c("""width { "75%" }""")
            +". Also all css pseudo-elements and classes are available in styling DSL."
            +" Additionally, you can set any other property that is not part of the DSL by using "
            c("""css()""")
            +"."
        }

        showcaseSection("Styling Components")
        paragraph {
            +"Every component, like "
            c("icon()")
            +" for example, can easily be styled by using the first parameter of its factory-function:"
        }
        componentFrame {
            lineUp {
                items {
                    icon({
                        size { normal }
                        color { danger.main }
                    }) { fromTheme { heart } }

                    icon({
                        size { huge }
                        border {
                            width { normal }
                            color { primary.main }
                        }
                        radius { full }
                    }) { fromTheme { chevronUp } }
                }
            }
        }
        highlight {
            source(
                """
                icon({
                    size { normal }
                    color { danger.main }
                }) { fromTheme { heart } }

                icon({
                    size { huge }
                    border {
                        width { normal }
                        color { primary.main }
                    }
                    radius { full }
                }) { fromTheme { chevronUp } }
                """
            )
        }

        showcaseSection("Predefined Styles")
        paragraph {
            +"You can group a set of styling properties and reuse them throughout your source code:"
        }
        componentFrame {
            lineUp {
                items {
                    val veryImportant: Style<BasicParams> = {
                        boxShadow { raised }
                        background { color { danger.main } }
                        color { neutral.main }
                        radius { larger }
                        hover {
                            background { color { warning.main } }
                        }
                        active {
                            border { color { warning.main } }
                        }
                        focus {
                            boxShadow { danger }
                        }
                    }
                    pushButton({
                        veryImportant()
                    }) { text("Very Important Button") }
                }
            }
        }
        highlight {
            source(
                """
                val veryImportant: Style<BasicParams> = {
                    boxShadow { raised }
                    background { color { danger.main } }
                    color { neutral }
                    radius { larger }
                    hover {
                        background { color { danger.gray700 } }
                    }
                    active {
                        border { color { warning.main } }
                    }
                    focus {
                        boxShadow { danger.main }
                    }
                }

                pushButton ({
                    veryImportant()
                }) { text("Very Important Button") }

                """
            )
        }
    }
}
