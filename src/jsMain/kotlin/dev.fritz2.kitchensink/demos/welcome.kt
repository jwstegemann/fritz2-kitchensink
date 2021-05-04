package dev.fritz2.kitchensink.demos

import dev.fritz2.components.box
import dev.fritz2.components.icon
import dev.fritz2.components.lineUp
import dev.fritz2.components.stackUp
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.styling.h1
import dev.fritz2.styling.h2
import dev.fritz2.styling.p
import dev.fritz2.styling.params.AlignContentValues.start
import dev.fritz2.styling.theme.Theme
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
fun RenderContext.welcome(): Div {
    return contentFrame({
        background { color { "rgba(255,255,255,0.95)" } }
    }) {

        lineUp({
            alignItems { center }
            verticalAlign { start }
            height { maxContent }
            margins { top { giant } }
        }) {
            items {
                icon({
                    size(
                        sm = { "5rem" },
                        md = { "9rem" }
                    )
                    color { primary.main }
                    css("drop-shadow(0 0 0.5rem gray);")
                }) { fromTheme { fritz2 } }
                box {
                    h1({
                        fontSize(
                            sm = { "1.5rem" },
                            md = { "4.0rem" }
                        )
                        lineHeight(
                            sm = { "1.5rem" },
                            md = { "4.0rem" }
                        )
                        color { primary.main }
                        textShadow { flat }
                    }) { +"tailor-made" }
                    h1({
                        fontSize(
                            sm = { "1.5rem" },
                            md = { "4.0rem" }
                        )
                        lineHeight(
                            sm = { "1.0rem" },
                            md = { "4.0rem" }
                        )
                        color { tertiary.main }
                        textShadow { flat }
                    }) { +"components" }
                }
            }
        }

        stackUp({
            margins(sm = { top { "1.5rem" } }, md = { top { "2rem" } })
        }) {
            spacing { normal }
            items {
                h2({
                    margins { vertical { small } }
                }) {
                    +"All you need is less."
                }

                paragraph {
                    +"""
                        This is a set of ready-to-go components for building reactive themeable web-apps, 
                        following a consistent constraint-based design system using fritz2.
                    """.trimIndent()
                }

                paragraph {
                    +"""
                        While these components can be used out of the box with a provided default theme, they are designed to be
                        customizable and can easily be styled and composed to fit your needs.
                    """.trimIndent()
                }

                coloredBox(Theme().colors.warning) {
                    // todo: If Status is something other than ALPHA, replace an with a. Also check Getting Started (same thing)
                    +"Remember that this is an ${appStatus()} version."
                    +" Most components still need some work, others are not implemented yet, and the API still needs"
                    +" to be completed and streamlined."
                }

                p {
                    +"Your opinion and comments are very welcome. Please visit "
                    externalLink("fritz.dev", "http://fritz2.dev")
                    +" for further information, or go to our github page at "
                    externalLink("https://github.com/jwstegemann/fritz2", "https://github.com/jwstegemann/fritz2")
                    +" to open issues and check out the code. To view the code for this demonstration, go to "
                    externalLink("https://github.com/jwstegemann/fritz2-kitchensink", "https://github.com/jwstegemann/fritz2-kitchensink")
                    +"."
                }

                p {
                    +"The concepts and designs of the fritz2 component library are highly inspired by the beautiful "
                    externalLink("Chakra UI", "https://chakra-ui.com/")
                    +"."
                }

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
            }
        }
    }
}