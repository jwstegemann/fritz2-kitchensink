package dev.fritz2.kitchensink.demos

import dev.fritz2.components.lineUp
import dev.fritz2.components.stackUp
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.kitchensink.flexbox_
import dev.fritz2.kitchensink.gridbox_
import dev.fritz2.styling.div
import dev.fritz2.styling.params.ColorProperty
import dev.fritz2.styling.theme.Theme
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalCoroutinesApi
fun RenderContext.stackDemo(): Div {

    val item: RenderContext.(String, String) -> Unit = { color, text ->
        div({
            display { flex }
            justifyContent { center }
            alignItems { center }
            background { color { color } }
            color { neutral.main }
            size { "40px" }
            radius { small }
        }) { +text }
    }

    return contentFrame {
        showcaseHeader("Stack")
        paragraph {
            +"A stack is a layout component which allows arbitrary elements to be aligned either in a vertical or"
            +" horizontal way. We offer dedicated components for both use cases:"
        }
        ul {
            li {
                c("stackUp")
                +" for vertical alignment"
            }
            li {
                c("lineUp")
                +" for horizontal alignment"
            }
        }
        paragraph {
            +"These stacks are basically specialized "
            internalLink("Flexboxes", flexbox_)
            +" which expose a built-in way to set the content direction and define the spacing"
            +" between items. Both are not responsive by default. If you need responsiveness, you have to use "
            internalLink("Flexboxes", flexbox_)
            +" instead."
        }



        paragraph {
            +"You can put arbitrary content into the stack components, which can be anything from just one HTML element to a complex"
            +" structure of elements, or other components of course."
        }

        showcaseSection("Usage")
        paragraph {
            +"In order to arrange items horizontally, just use a "
            c("lineUp")
            +" component and put your content within the "
            c("items")
            +" context:"
        }
        componentFrame {
            lineUp {
                items {
                    item(Theme().colors.primary.main, "1")
                    item(Theme().colors.secondary.main, "2")
                    item(Theme().colors.tertiary.main, "3")
                }
            }
        }
        highlight {
            source(
                """
                lineUp {
                    items {
                        // some styled boxes for content:
                        box({
                            display { flex }
                            justifyContent { center }
                            alignItems { center }
                            background { color { primary.highlight } }
                            color { neutral }
                            size { "40px" }       
                            radius { small }
                        }) { +"1" }
                        
                        // styling omitted for readability
                        box { +"2" }
                        box { +"3" }
                    }
                }                    
                """.trimIndent()
            )
        }

        paragraph {
            +"Use "
            c("stackUp")
            +" to arrange items vertically. Always remember to use the "
            c("items")
            +" context for your content."

        }
        componentFrame {
            stackUp {
                items {
                    item(Theme().colors.primary.main, "1")
                    item(Theme().colors.secondary.main, "2")
                    item(Theme().colors.tertiary.main, "3")
                }
            }
        }
        highlight {
            source(
                """
                stackUp {
                    items {
                        // styling omitted for readability
                        box { +"1" }
                        box { +"2" }
                        box { +"3" }
                    }
                }                    
                """.trimIndent()
            )
        }

        showcaseSection("Arranging Items")
        paragraph {
            +"Stacks can be customized by changing their order of appearance using the boolean "
            c("reversed")
            +" property, and by defining the "
            c("spacing")
            +" between items. Of course you could change the spacing via the regular styling DSL as well, but the stack"
            +" components additionally offer this spacing context."
        }
        componentFrame {
            lineUp {
                spacing { huge }
                items {
                    stackUp {
                        spacing { tiny }
                        reversed(true)
                        items {
                            item(Theme().colors.primary.main, "1")
                            item(Theme().colors.secondary.main, "2")
                            item(Theme().colors.tertiary.main, "3")
                        }
                    }
                    item(Theme().colors.primary.main, "4")
                    item(Theme().colors.secondary.main, "5")
                }
            }
        }
        highlight {
            source(
                """
                lineUp {
                    spacing { huge }
                    items {
                        stackUp {
                            spacing { tiny }
                            reversed(true)
                            items {
                                box { +"1" }
                                box { +"2" }
                                box { +"3" }
                            }
                        }
                        box { +"4" }
                        box { +"5" }
                    }
                }
                """.trimIndent()
            )
        }

        showcaseSection("Other Layout Techniques")

        paragraph {
            +"The default layout of our stack components uses the following properties:"
        }
        ul {
            li {
                c("align-items = flex-start")
            }
            li {
                c("justify-content = flex-start")
            }
        }
        paragraph {
            +"In order to adopt the alignment to your needs, apply the appropriate values via the "
            c("styling")
            +" attribute of the components."
        }
        componentFrame {
            val sizedBox: RenderContext.(Int, Int, ColorProperty) -> Unit = { w, h, color ->
                div({
                    background { color { color } }
                    width { "${w}px" }
                    height { "${h}px" }
                    textAlign { center }
                    radius { small }
                }) { +"${w}x${h}" }
            }

            lineUp({
                justifyContent { center }
                alignItems { center }
            }) {
                items {
                    stackUp({
                        alignItems { flexEnd }
                    }) {
                        items {
                            sizedBox(60, 60, Theme().colors.secondary.main)
                            sizedBox(100, 100, Theme().colors.secondary.main)
                            sizedBox(80, 30, Theme().colors.secondary.main)
                        }
                    }
                    sizedBox(100, 40, Theme().colors.secondary.main)
                    sizedBox(80, 80, Theme().colors.secondary.main)
                }
            }
        }
        highlight {
            source(
                """
                val sizedBox: RenderContext.(Int, Int) -> Unit = { w, h ->
                    // some styling omitted for readability
                    box({
                        width { "${'$'}{w}px" }
                        height{ "${'$'}{h}px" }
                    }) { ... }
                }
    
                lineUp({
                    // center the stack
                    justifyContent { center } 
                    // vertically align around cross axis
                    alignItems { center } 
                }) {
                    items {
                        stackUp({
                            // right-align the column items
                            alignItems { flexEnd } 
                        }) {
                            items { 
                                sizedBox(60, 60)
                                sizedBox(100, 100)
                                sizedBox(80, 30)
                            }
                        }
                        sizedBox(100, 40)
                        sizedBox(80, 80)
                    }
                }                    
                """.trimIndent()
            )
        }

        coloredBox(Theme().colors.info) {
            +"Tip for complex layouts:"
            +" Favor the application of a "
            internalLink("Gridbox", gridbox_)
            +" over complex styling for stack components. The "
            externalLink(
                "CSS grid model ",
                "https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Grid_Layout"
            )
            +" offers much more control over the layout than a flexbox approach."
        }
    }
}