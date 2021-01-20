package dev.fritz2.kitchensink.demos

import dev.fritz2.components.icon
import dev.fritz2.components.lineUp
import dev.fritz2.components.pushButton
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.kitchensink.theme_
import dev.fritz2.styling.params.*
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
                        background { color { secondary } }
                        color { base }
                        fontWeight { "700" }
                        boxShadow { raised }
                        padding { normal }
                        radius { small }
                    }) { +"Raised Text" }
                }
            }
        }
        playground {
            source(
                """
                (::span.styled {
                    background { color { secondary } }
                    color { base }
                    fontWeight { "700" }
                    boxShadow { raised }
                    padding { normal }
                    radius { small }
                }) { +"Raised Text" }
                """
            )
        }

        paragraph {
            +"To remain as flexible as possible, values of properties can alternatively be passed as "
            c("String")
            +"s, like "
            c("""width { "75%" }""")
            +". Additionally, you can set any other property that is not part of the DSL by using "
            c("""css()""")
            +"."
        }

        showcaseSection("Styling Components")
        paragraph {
            +"Every component, like "
            c("icon")
            +" for example, can easily be styled by using the first parameter of its factory-function:"
        }
        componentFrame {
            lineUp {
                items {
                    icon({
                        size { small }
                        color { "tomato" }
                    }) { fromTheme { heart } }

                    icon({
                        size { huge }
                        border {
                            width { normal }
                            color { primary }
                        }
                        radius { full }
                    }) { fromTheme { chevronUp } }
                }
            }
        }
        playground {
            source(
                """
                icon(
                    {  // styling is the first parameter 
                        size { small }
                        color { "tomato"" }
                    }
                ) { fromTheme { heart } }

                icon({
                    size { huge }
                    border {
                        width { normal }
                        color { primary }
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
                        background { color { danger } }
                        color { base }
                        radius { larger }
                        hover {
                            background { color { warning } }
                        }
                        active {
                            border { color { warning } }
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
        playground {
            source(
                """
                val veryImportant: Style<BasicParams> = {
                    boxShadow { raised }
                    background { color { danger } }
                    color { base }
                    radius { larger }
                    hover {
                        background { color { danger.darker } }
                    }
                    active {
                        border { color { warning } }
                    }
                    focus {
                        boxShadow { danger }
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
