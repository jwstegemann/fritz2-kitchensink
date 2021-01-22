package dev.fritz2.kitchensink.demos

import dev.fritz2.components.box
import dev.fritz2.components.lineUp
import dev.fritz2.components.tooltip
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.P
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.kitchensink.theme_
import dev.fritz2.styling.params.*
import dev.fritz2.styling.theme.Theme
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
fun RenderContext.colorDemo(): Div {

    return contentFrame {

        showcaseHeader("Colors")

        paragraph {
            +"The fritz2 components default theme has its own set of colors which you can view here."
            +" Please see "
            internalLink("the themes page", theme_)
            +" for more information on using themes. "
        }

        paragraph {
            +"The default theme provides the following colors: "
            c("primary")
            +", "
            c("primaryEffect")
            +", "
            c("secondary")
            +", "
            c("secondaryEffect")
            +", "
            c("dark")
            +", "
            c("light")
            +", "
            c("lightEffect")
            +", "
            c("info")
            +", "
            c("success")
            +", "
            c("warning")
            +" and "
            c("danger")
            +"."

        }

        colorBars()

        showcaseSection("Color Brightness")
        val demoBrightness = 1.5

        paragraph {   + "Additionally, you can use the function "
            c("alterHexColorBrightness(ColorProperty, double)")
            +" to freely change the brightness setting of any "
            b { +"hex (#rrggbb)" }
            +" color."
            +" Use a value between 1 and 2 to lighten up the color, and a value between 0 and 1 to darken it."
            +" The colors on the right side use a brightness setting of "
            c("$demoBrightness")
            +"."
        }

        colorBrightnessDemo(demoBrightness)
        val demoDarkness = 0.7
        paragraph {
            +"These colors use a brightness of "
            c("$demoDarkness")
            +"."
        }
        colorBrightnessDemo(demoDarkness)
    }
}

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

// TODO: Remove lighter version
fun RenderContext.colorBars(): Div {
    return (::div.styled {
        margins { top { huge } }
    }) {
        createColorBar(Theme().colors.primary, "primary", { "95%" }, { "100%" })
        createColorBar(Theme().colors.primaryEffect, "primaryEffect", { "90%" }, { "95%" })
        createColorBar(Theme().colors.secondary, "secondary", { "85%" }, { "90%" })
        createColorBar(Theme().colors.secondaryEffect, "secondaryEffect", { "80%" }, { "85%" })
        createColorBar(Theme().colors.dark, "dark", { "75%" }, { "80%" })
        createColorBar(Theme().colors.light, "light", { "70%" }, { "75%" })
        createColorBar(Theme().colors.lightEffect, "lightEffect", { "65%" }, { "70%" })
        createColorBar(Theme().colors.info, "info", { "60%" }, { "65%" })
        createColorBar(Theme().colors.success, "success", { "55%" }, { "60%" })
        createColorBar(Theme().colors.warning, "warning", { "50%" }, { "55%" })
        createColorBar(Theme().colors.danger, "danger", { "45%" }, { "50%" })
    }
}

fun RenderContext.createBrightnessDemoBar(color: ColorProperty, colorName: String, brightness: Double): Div {
    return lineUp({
        margins {
            top { tiny }
        }
    }) {
        items {
            box({
                width(sm = { "45%" }, md = { "40%" })
                background {
                    color { color }
                }
                border {
                    width { fat }
                    color { color }
                }
                hover {
                    tooltip(color) { right }()
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
                            color { color }
                            paddings {
                                left { small }
                                right { small }
                            }
                        }) { +colorName }
                    }
                }
            }
            box({
                width(sm = { "45%" }, md = { "40%" })
                height { "2.2rem" }
                background {
                    color { alterHexColorBrightness(color, brightness) }
                }
                border {
                    width { fat }
                    color { alterHexColorBrightness(color, brightness) }
                }
                hover {
                    tooltip(alterHexColorBrightness(color, brightness)) { right }()
                }
                radius { "1.3rem" }
            }) {}
        }
    }
}

fun RenderContext.colorBrightnessDemo(brightness: Double): P {
    return paragraph {
        createBrightnessDemoBar(Theme().colors.primary, "primary", brightness)
        createBrightnessDemoBar(Theme().colors.secondary, "secondary", brightness)
        createBrightnessDemoBar(Theme().colors.dark, "dark", brightness)
        createBrightnessDemoBar(Theme().colors.light, "light", brightness)
        createBrightnessDemoBar(Theme().colors.info, "info", brightness)
        createBrightnessDemoBar(Theme().colors.success, "success", brightness)
        createBrightnessDemoBar(Theme().colors.warning, "warning", brightness)
        createBrightnessDemoBar(Theme().colors.danger, "danger", brightness)
    }
}