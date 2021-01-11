package dev.fritz2.kitchensink.demos

import dev.fritz2.components.box
import dev.fritz2.components.lineUp
import dev.fritz2.components.switch
import dev.fritz2.components.tooltip
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.P
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.ThemeStore
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
            c("secondary")
            +", "
            c("dark")
            +", "
            c("light")
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
        paragraph {
            +"You can change the brightness of any color by using its "
            c("lighter")
            +" and "
            c("darker")
            +" function. They stack and can be used multiple times: "
            c("info.lighter.lighter")
        }

        paragraph {
            createColorBar(Theme().colors.info.lighter.lighter, "info lighter x 2", { "65%" }, { "70%" })
            createColorBar(Theme().colors.info, "info", { "65%" }, { "70%" })
            createColorBar(Theme().colors.info.darker.darker, "info darker x 2", { "65%" }, { "70%" })
        }

        paragraph {   + "Additionally, you can use the function "
            c("alterBrightness(ColorProperty, double)")
            +" to freely change the brightness setting of any color."
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
        createColorBar(Theme().colors.secondary, "secondary", { "90%" }, { "95%" })
        createColorBar(Theme().colors.dark, "dark", { "85%" }, { "90%" })
        createColorBar(Theme().colors.light, "light", { "80%" }, { "85%" })
        createColorBar(Theme().colors.info, "info", { "75%" }, { "80%" })
        createColorBar(Theme().colors.success, "success", { "70%" }, { "75%" })
        createColorBar(Theme().colors.warning, "warning", { "65%" }, { "70%" })
        createColorBar(Theme().colors.danger, "danger", { "60%" }, { "65%" })
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
                    color { alterBrightness(color, brightness) }
                }
                border {
                    width { fat }
                    color { alterBrightness(color, brightness) }
                }
                hover {
                    tooltip(alterBrightness(color, brightness)) { right }()
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