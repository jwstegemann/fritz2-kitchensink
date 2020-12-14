package dev.fritz2.kitchensink.demos

import dev.fritz2.components.box
import dev.fritz2.components.lineUp
import dev.fritz2.components.tooltip
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.alterBrightness
import dev.fritz2.styling.params.ColorProperty
import dev.fritz2.styling.params.SizesProperty
import dev.fritz2.styling.params.styled
import dev.fritz2.styling.theme.Theme

fun RenderContext.createColorBar(color: ColorProperty, colorName: String, boxSizeMd: SizesProperty, hoverSizeMd: SizesProperty) : Div {
    return lineUp({
        margins {
            top { tiny }
        }
    }) {
        items {
            box({
                width(sm = { "100%" }, boxSizeMd)
                background {
                    color { color }
                }
                border {
                    width { fat }
                    color { "transparent" }
                }
                hover {
                    width(sm = { "100%" }, hoverSizeMd)
                    tooltip(color) { right } ()
                }
                radius { "1.3rem" }
                textAlign { right }
            }) {
                (::p.styled {
                    width { "8rem" }
                    background {
                        color { base }
                    }
                    radii {
                        left { "1rem" }
                    }
                    color { color }
                    paddings {
                        left { small }
                        right { small }
                    }
                }) { +colorName }
            }
        }
    }

}

fun RenderContext.colorDemo(): Div {
    return div {
        createColorBar(Theme().colors.primary, "primary", { "90%" }, { "100%" })
        createColorBar(Theme().colors.primary_hover, "primary_hover", { "85%" }, { "95%" })
        createColorBar(Theme().colors.secondary, "secondary", { "80%" }, { "90%" })
        createColorBar(Theme().colors.tertiary, "tertiary", { "75%" }, { "85%" })
        createColorBar(Theme().colors.dark, "dark", { "70%" }, { "80%" })
        createColorBar(Theme().colors.light, "light", { "65%" }, { "75%" })
        createColorBar(Theme().colors.light_hover, "light_hover", { "60%" }, { "70%" })
        createColorBar(Theme().colors.info, "info", { "55%" }, { "65%" })
        createColorBar(Theme().colors.success, "success", { "50%" }, { "60%" })
        createColorBar(Theme().colors.warning, "warning", { "45%" }, { "55%" })
        createColorBar(Theme().colors.danger, "danger", { "40%" }, { "50%" })
    }
}

fun RenderContext.colorConvertDemo(brightness: Double): Div {
    return div {
        lineUp({ //primary
            margins {
                top { huge }
            }
        }) {
            items {
                box({
                    width(sm = { "45%" }, md = { "40%" })
                    background {
                        color { primary }
                    }
                    border {
                        width { fat }
                        color { primary }
                    }
                    hover {
                        tooltip("${Theme().colors.primary}") { right }()
                    }
                    radius { "1.3rem" }
                }) {
                    lineUp {
                        items {
                            (::p.styled {
                                textAlign { center }
                                width { "6rem" }
                                background {
                                    color { base }
                                }
                                radius { "1rem" }
                                color { primary }
                                paddings {
                                    left { small }
                                    right { small }
                                }
                            }) { +"primary" }
                        }
                    }
                }
                box({
                    width(sm = { "45%" }, md = { "40%" })
                    height { "2.2rem" }
                    background {
                        color { alterBrightness(primary, brightness) }
                    }
                    border {
                        width { fat }
                        color { alterBrightness(primary, brightness) }
                    }
                    hover {
                        tooltip(alterBrightness(Theme().colors.primary, brightness)) { right }()
                    }
                    radius { "1.3rem" }
                }) {
                    lineUp {
                        items {
                            (::p.styled {
                                color { base }
                                fontSize(sm = { normal }, md = { "0rem" })
                            }) {
                                +alterBrightness(Theme().colors.primary, brightness)
                            }
                        }
                    }
                }
            }
        }
        lineUp({ //secondary
            margins {
                top { tiny }
            }
        }) {
            items {
                box({
                    width(sm = { "45%" }, md = { "40%" })
                    background {
                        color { secondary }
                    }
                    border {
                        width { fat }
                        color { secondary }
                    }
                    hover {
                        tooltip("${Theme().colors.secondary}") { right }()
                    }
                    radius { "1.3rem" }
                }) {
                    lineUp {
                        items {
                            (::p.styled {
                                textAlign { center }
                                width { "6rem" }
                                background {
                                    color { base }
                                }
                                radius { "1rem" }
                                color { secondary }
                                paddings {
                                    left { small }
                                    right { small }
                                }
                            }) { +"secondary" }
                        }
                    }
                }
                box({
                    width(sm = { "45%" }, md = { "40%" })
                    height { "2.2rem" }
                    background {
                        color { alterBrightness(secondary, brightness) }
                    }
                    border {
                        width { fat }
                        color { alterBrightness(secondary, brightness) }
                    }
                    hover {
                        tooltip(alterBrightness(Theme().colors.secondary, brightness)) { right }()
                    }
                    radius { "1.3rem" }
                }) {
                    lineUp {
                        items {
                            (::p.styled {
                                color { dark }
                                fontSize(sm = { normal }, md = { "0rem" })
                            }) {
                                +alterBrightness(Theme().colors.secondary, brightness)
                            }
                        }
                    }
                }
            }
        }
        lineUp({ //tertiary
            margins {
                top { tiny }
            }
        }) {
            items {
                box({
                    width(sm = { "45%" }, md = { "40%" })
                    background {
                        color { tertiary }
                    }
                    border {
                        width { fat }
                        color { tertiary }
                    }
                    hover {
                        tooltip("${Theme().colors.tertiary}") { right }()
                    }
                    radius { "1.3rem" }
                }) {
                    lineUp {
                        items {
                            (::p.styled {
                                textAlign { center }
                                width { "6rem" }
                                background {
                                    color { base }
                                }
                                radius { "1rem" }
                                color { tertiary }
                                paddings {
                                    left { small }
                                    right { small }
                                }
                            }) { +"tertiary" }
                        }
                    }
                }
                box({
                    width(sm = { "45%" }, md = { "40%" })
                    height { "2.2rem" }
                    background {
                        color { alterBrightness(tertiary, brightness) }
                    }
                    border {
                        width { fat }
                        color { alterBrightness(tertiary, brightness) }
                    }
                    hover {
                        tooltip(alterBrightness(Theme().colors.tertiary, brightness)) { right }()
                    }
                    radius { "1.3rem" }
                }) {
                    lineUp {
                        items {
                            (::p.styled {
                                color { base }
                                fontSize(sm = { normal }, md = { "0rem" })
                            }) {
                                +alterBrightness(Theme().colors.tertiary, brightness)
                            }
                        }
                    }
                }
            }
        }
        lineUp({ //dark
            margins {
                top { tiny }
            }
        }) {
            items {
                box({
                    width(sm = { "45%" }, md = { "40%" })
                    background {
                        color { dark }
                    }
                    border {
                        width { fat }
                        color { dark }
                    }
                    hover {
                        tooltip("${Theme().colors.dark}") { right }()
                    }
                    radius { "1.3rem" }
                }) {
                    lineUp {
                        items {
                            (::p.styled {
                                textAlign { center }
                                width { "6rem" }
                                background {
                                    color { base }
                                }
                                radius { "1rem" }
                                color { dark }
                                paddings {
                                    left { small }
                                    right { small }
                                }
                            }) { +"dark" }
                        }
                    }
                }
                box({
                    width(sm = { "45%" }, md = { "40%" })
                    height { "2.2rem" }
                    background {
                        color { alterBrightness(dark, brightness) }
                    }
                    border {
                        width { fat }
                        color { alterBrightness(dark, brightness) }
                    }
                    hover {
                        tooltip(alterBrightness(Theme().colors.dark, brightness)) { right }()
                    }
                    radius { "1.3rem" }
                }) {
                    lineUp {
                        items {
                            (::p.styled {
                                color { base }
                                fontSize(sm = { normal }, md = { "0rem" })
                            }) {
                                +alterBrightness(Theme().colors.dark, brightness)
                            }
                        }
                    }
                }
            }
        }
        lineUp({ //light
            margins {
                top { tiny }
            }
        }) {
            items {
                box({
                    width(sm = { "45%" }, md = { "40%" })
                    background {
                        color { light }
                    }
                    border {
                        width { fat }
                        color { light }
                    }
                    hover {
                        tooltip("${Theme().colors.light}") { right }()
                    }
                    radius { "1.3rem" }
                }) {
                    lineUp {
                        items {
                            (::p.styled {
                                textAlign { center }
                                width { "6rem" }
                                background {
                                    color { base }
                                }
                                radius { "1rem" }
                                color { light }
                                paddings {
                                    left { small }
                                    right { small }
                                }
                            }) { +"light" }
                        }
                    }
                }
                box({
                    width(sm = { "45%" }, md = { "40%" })
                    height { "2.2rem" }
                    background {
                        color { alterBrightness(light, brightness) }
                    }
                    border {
                        width { fat }
                        color { alterBrightness(light, brightness) }
                    }
                    hover {
                        tooltip(alterBrightness(Theme().colors.light, brightness)) { right }()
                    }
                    radius { "1.3rem" }
                }) {
                    lineUp {
                        items {
                            (::p.styled {
                                color { dark }
                                fontSize(sm = { normal }, md = { "0rem" })
                            }) {
                                +alterBrightness(Theme().colors.light, brightness)
                            }
                        }
                    }
                }
            }
        }
        lineUp({ //info
            margins {
                top { tiny }
            }
        }) {
            items {
                box({
                    width(sm = { "45%" }, md = { "40%" })
                    background {
                        color { info }
                    }
                    border {
                        width { fat }
                        color { info }
                    }
                    hover {
                        tooltip("${Theme().colors.info}") { right }()
                    }
                    radius { "1.3rem" }
                }) {
                    lineUp {
                        items {
                            (::p.styled {
                                textAlign { center }
                                width { "6rem" }
                                background {
                                    color { base }
                                }
                                radius { "1rem" }
                                color { info }
                                paddings {
                                    left { small }
                                    right { small }
                                }
                            }) { +"info" }
                        }
                    }
                }
                box({
                    width(sm = { "45%" }, md = { "40%" })
                    height { "2.2rem" }
                    background {
                        color { alterBrightness(info, brightness) }
                    }
                    border {
                        width { fat }
                        color { alterBrightness(info, brightness) }
                    }
                    hover {
                        tooltip(alterBrightness(Theme().colors.info, brightness)) { right }()
                    }
                    radius { "1.3rem" }
                }) {
                    lineUp {
                        items {
                            (::p.styled {
                                color { base }
                                fontSize(sm = { normal }, md = { "0rem" })
                            }) {
                                +alterBrightness(Theme().colors.info, brightness)
                            }
                        }
                    }
                }
            }
        }
        lineUp({ //success
            margins {
                top { tiny }
            }
        }) {
            items {
                box({
                    width(sm = { "45%" }, md = { "40%" })
                    background {
                        color { success }
                    }
                    border {
                        width { fat }
                        color { success }
                    }
                    hover {
                        tooltip("${Theme().colors.success}") { right }()
                    }
                    radius { "1.3rem" }
                }) {
                    lineUp {
                        items {
                            (::p.styled {
                                textAlign { center }
                                width { "6rem" }
                                background {
                                    color { base }
                                }
                                radius { "1rem" }
                                color { success }
                                paddings {
                                    left { small }
                                    right { small }
                                }
                            }) { +"success" }
                        }
                    }
                }
                box({
                    width(sm = { "45%" }, md = { "40%" })
                    height { "2.2rem" }
                    background {
                        color {
                            alterBrightness(
                                success, brightness
                            )
                        }
                    }
                    border {
                        width { fat }
                        color {
                            alterBrightness(
                                success, brightness
                            )
                        }
                    }
                    hover {
                        tooltip(
                            alterBrightness(
                                Theme().colors.success, brightness
                            )
                        ) { right }()
                    }
                    radius { "1.3rem" }
                }) {
                    lineUp {
                        items {
                            (::p.styled {
                                color { base }
                                fontSize(sm = { normal }, md = { "0rem" })
                            }) {
                                +alterBrightness(
                                    Theme().colors.success, brightness
                                )
                            }
                        }
                    }
                }
            }
        }
        lineUp({ //warning
            margins {
                top { tiny }
            }
        }) {
            items {
                box({
                    width(sm = { "45%" }, md = { "40%" })
                    background {
                        color { warning }
                    }
                    border {
                        width { fat }
                        color { warning }
                    }
                    hover {
                        tooltip("${Theme().colors.warning}") { right }()
                    }
                    radius { "1.3rem" }
                }) {
                    lineUp {
                        items {
                            (::p.styled {
                                textAlign { center }
                                width { "6rem" }
                                background {
                                    color { base }
                                }
                                radius { "1rem" }
                                color { warning }
                                paddings {
                                    left { small }
                                    right { small }
                                }
                            }) { +"warning" }
                        }
                    }
                }
                box({
                    width(sm = { "45%" }, md = { "40%" })
                    height { "2.2rem" }
                    background {
                        color {
                            alterBrightness(
                                warning, brightness
                            )
                        }
                    }
                    border {
                        width { fat }
                        color {
                            alterBrightness(
                                warning, brightness
                            )
                        }
                    }
                    hover {
                        tooltip(
                            alterBrightness(
                                Theme().colors.warning, brightness
                            )
                        ) { right }()
                    }
                    radius { "1.3rem" }
                }) {
                    lineUp {
                        items {
                            (::p.styled {
                                color { dark }
                                fontSize(sm = { normal }, md = { "0rem" })
                            }) {
                                +alterBrightness(
                                    Theme().colors.warning, brightness
                                )
                            }
                        }
                    }
                }
            }
        }
        lineUp({ //danger
            margins {
                top { tiny }
            }
        }) {
            items {
                box({
                    width(sm = { "45%" }, md = { "40%" })
                    background {
                        color { danger }
                    }
                    border {
                        width { fat }
                        color { danger }
                    }
                    hover {
                        tooltip("${Theme().colors.danger}") { right }()
                    }
                    radius { "1.3rem" }
                }) {
                    lineUp {
                        items {
                            (::p.styled {
                                textAlign { center }
                                width { "6rem" }
                                background {
                                    color { base }
                                }
                                radius { "1rem" }
                                color { danger }
                                paddings {
                                    left { small }
                                    right { small }
                                }
                            }) { +"danger" }
                        }
                    }
                }
                box({
                    width(sm = { "45%" }, md = { "40%" })
                    height { "2.2rem" }
                    background {
                        color {
                            alterBrightness(
                                danger, brightness
                            )
                        }
                    }
                    border {
                        width { fat }
                        color {
                            alterBrightness(
                                danger, brightness
                            )
                        }
                    }
                    hover {
                        tooltip(
                            alterBrightness(
                                Theme().colors.danger, brightness
                            )
                        ) { right }()
                    }
                    radius { "1.3rem" }
                }) {
                    lineUp {
                        items {
                            (::p.styled {
                                color { base }
                                fontSize(sm = { normal }, md = { "0rem" })
                            }) {
                                +alterBrightness(
                                    Theme().colors.danger, brightness
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
