package dev.fritz2.kitchensink.demos

import dev.fritz2.components.flexBox
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.styling.div
import dev.fritz2.styling.h1
import dev.fritz2.styling.img
import dev.fritz2.styling.p
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
fun RenderContext.responsiveDemo(): Div {

    return contentFrame {

        showcaseHeader("Responsiveness")

        paragraph {
            +"fritz2 strives to make responsive styling with a mobile-first approach as easy as possible."
        }

        paragraph {
            +"""The theme defines four breakpoints of different viewport-sizes ("""
            c("sm")
            +", "
            c("md")
            +", "
            c("lg")
            +", and "
            c("xl")
            +"""). You can set each property independently for these viewport-sizes:""".trimIndent()
        }

        highlight {
            source(
                """
                div({
                    fontSize(sm = { tiny }, lg = { normal })
                    width(sm = { full }, lg = { "768px" })
                    color { primary.main }
                }) { + "my styled div" }"""
            )
        }

        paragraph {
            +"""In accordance with mobile-first, when no value is given for a particular size, the next smaller one will be applied. In the example above, the"""
            c("fontSize")
            +" will be "
            c("tiny")
            +" for "
            c("sm")
            +" and "
            c("md")
            +", and "
            c("normal")
            +" for "
            c("lg")
            +" and "
            c("xl")
            +". If only one value is given, it will be used for all sizes."
        }


        showcaseSection("Responsive Example")

        paragraph {
            +"This small example demonstrates responsive styling with fritz2. Resize your viewport to see the effect:"
        }

        componentFrame(padding = false) {
            flexBox({
                padding { small }
                direction(sm = { column }, md = { row })
            }) {
                div({
                    margins(
                        {
                            top { small }
                            bottom { small }
                        },
                        md = { left { normal } }
                    )
                    flex { shrink { "0" } }
                }) {
                    img({
                        width(sm = { full }, md = { wide.small })
                        boxShadow { flat }
                        radius { large }
                    }) {
                        src("img/background.jpg")
                        alt("Photo by Jong Marshes on Unsplash")
                    }
                }

                div({
                    margins(
                        {
                            top { small }
                            bottom { large }
                        },
                        md = { left { normal } }
                    )
                }) {
                    p({
                        fontSize { smaller }
                        paddings { top { "1.5rem" }}
                    }) {
                        +"Photo by "
                        externalLink(
                            "Jong Marshes",
                            "https://unsplash.com/@turnlip19?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText"
                        )
                        +" on "
                        externalLink(
                            "Unsplash",
                            "https://unsplash.com/s/photos/water?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText"
                        )
                    }
                    h1({
                        fontSize { large }
                        fontWeight { bold }
                    }) { +"Resize your viewport!" }
                    p({
                        paddings {
                            all { small }
                            left { none }
                        }
                        fontSize { small }
                    }) {
                        +"""Please note that some styling which does not
                            contribute to responsiveness was omitted in the source code example below. 
                         """
                    }
                }
            }
        }
        highlight {
            source(
                """
                flexBox({
                    direction(sm = { column }, md = { row })
                }) {
                    div({
                        margins(
                            {
                                top { small }
                                bottom { small }
                            },
                            md = { left { normal } }
                        )
                        flex { shrink { "0" } }
                    }) {
                        img({
                            width(sm = { full }, md = { wide.small })
                        }) {
                            src("...")
                        }
                    }
    
                    div({
                        margins(
                            sm = {
                                top { small }
                                bottom { large }
                            },
                            md = { left { normal } }
                        )
                    }) {
                        p { +"Photo by Lauren York on Unsplash" }
                        
                        h1 { +"Resize your viewport!" }
                        
                        p { 
                            +""${'"'}Please note that some styling which does not
                                contribute to responsiveness was omitted in 
                                the source code example below. 
                            ""${'"'}
                        }
                    }
                }
                """
            )
        }
    }
}



