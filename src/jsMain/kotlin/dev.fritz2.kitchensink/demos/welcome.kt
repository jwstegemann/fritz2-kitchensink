package dev.fritz2.kitchensink.demos

import dev.fritz2.components.box
import dev.fritz2.components.icon
import dev.fritz2.components.lineUp
import dev.fritz2.components.stackUp
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.contentFrame
import dev.fritz2.kitchensink.base.externalLinkInverted
import dev.fritz2.kitchensink.base.paragraph
import dev.fritz2.kitchensink.base.warningBox
import dev.fritz2.styling.params.AlignContentValues.start
import dev.fritz2.styling.params.rgba
import dev.fritz2.styling.params.styled
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
fun RenderContext.welcome(): Div {
    return contentFrame {

        lineUp({
            alignItems { center }
            verticalAlign { start }
            margins {
                top { "3rem" }
            }
            height {
                maxContent
            }
        }) {
            items {
                icon({
                    size(
                        sm = { "3rem" },
                        md = { "10.0rem" }
                    )
                    color { "rgba(255,255,255,0.8)" }
                    css("drop-shadow(0 0 0.5rem grey);")
                }) { fromTheme { fritz2 } }
                box {
                    (::h1.styled {
                        fontSize(
                            sm = { "1.5rem" },
                            md = { "5.0rem" }
                        )
                        lineHeight(
                            sm = { "1.5rem" },
                            md = { "5.0rem" }
                        )
                        textShadow { flat }
                    }) { +"tailor-made" }
                    (::h1.styled {
                        fontSize(
                            sm = { "1.5rem" },
                            md = { "5.0rem" }
                        )
                        lineHeight(
                            sm = { "1.0rem" },
                            md = { "5.0rem" }
                        )
                        color { "rgba(255,255,255,0.85)" }
                        textShadow { flat }
                    }) { +"components" }
                }
            }
        }

        stackUp({
            padding { large }
            background { color { rgba(160,180,55,0.6) } }
            //background { color { rgba(112,145,26,0.7) } }
            //margins { top { "3rem" } }
            margins(
                sm = { top { "1.5rem" } },
                md = { top { "3rem" } }
            )
            radius { "2rem" }
            boxShadow { flat }
        }) {
            spacing { normal }
            items {
                (::h2.styled {
                    margins { vertical { normal } }
                }) {
                    +"All you need is less."
                }

                paragraph {
                    +"""
                        This is a set of ready-to-go components for building reactive themable web-apps 
                        following a consistent constraint-based design system using fritz2.
                    """.trimIndent()
                }

                paragraph {
                    +"""
                        While these components can be used out of the box with a provided default theme, they are designed to be
                        customizable and can easily be styled and composed to fit your needs.
                    """.trimIndent()
                }

                warningBox({
                    background { color { rgba(225,79,42,0.5) } }
                }) {
                    +"Remember that this is a preview release."
                    +" Most components still need some work, others are not implemented yet, and the API still needs"
                    +" to be completed and streamlined."
                }

                (::p.styled {
                    margins {
                        bottom { "2rem" }
                    }
                }) {
                    +"Your opinion and comments are very welcome. Please visit "
                    externalLinkInverted("fritz.dev", "http://fritz2.dev")
                    +" for further information, or go to our github page at "
                    externalLinkInverted("https://github.com/jwstegemann/fritz2", "https://github.com/jwstegemann/fritz2")
                    +" to open issues and check out the code."
                }

                p {
                    +"The concepts and design of the fritz2 component library are highly inspired by the beautiful "
                    externalLinkInverted("Chakra UI", "https://chakra-ui.com/")
                    +"."
                }

                p {
                    +"Photo by "
                    externalLinkInverted(
                        "Ochir-Erdene Oyunmedeg",
                        "https://unsplash.com/@chiklad?utm_source=unsplash&amp;utm_medium=referral&amp;utm_content=creditCopyText"
                    )
                    +" on "
                    externalLinkInverted(
                        "Unsplash",
                        "https://unsplash.com/s/photos/grass?utm_source=unsplash&amp;utm_medium=referral&amp;utm_content=creditCopyText"
                    )
                }
            }
        }
    }
}