package dev.fritz2.kitchensink.demos

import dev.fritz2.components.box
import dev.fritz2.components.lineUp
import dev.fritz2.components.tooltip
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.P
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.kitchensink.theme_
import dev.fritz2.styling.params.ColorProperty
import dev.fritz2.styling.params.SizesProperty
import dev.fritz2.styling.params.alterHexColorBrightness
import dev.fritz2.styling.params.styled
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
            c("tertiary")
            +", "
            c("info")
            +", "
            c("success")
            +", "
            c("warning")
            +" and "
            c("danger")
            +"."
            c("neutral")
            +", "
            c("grays [100 - 700]")
            +", "
        }

        (::div.styled {
            margins { top { huge } }
        }) {
            createColorBar(Theme().colors.primary.base, "primary.base", { "95%" }, { "100%" })
            createColorBar(Theme().colors.primary.highlight, "primary.highlight", { "95%" }, { "100%" })
//            createColorBar(Theme().colors.primary.complementary, "primary.complementary", { "95%" }, { "100%" })
            createColorBar(Theme().colors.secondary.base, "secondary.base", { "90%" }, { "95%" })
            createColorBar(Theme().colors.secondary.highlight, "secondary.highlight", { "90%" }, { "95%" })
//            createColorBar(Theme().colors.secondary.complementary, "secondary.complementary", { "90%" }, { "95%" })
            createColorBar(Theme().colors.tertiary.base, "tertiary.base", { "85%" }, { "90%" })
            createColorBar(Theme().colors.tertiary.highlight, "tertiary.highlight", { "85%" }, { "90%" })
//            createColorBar(Theme().colors.tertiary.complementary, "tertiary.complementary", { "85%" }, { "90%" })
            createColorBar(Theme().colors.info, "info", { "80%" }, { "85%" })
            createColorBar(Theme().colors.success, "success", { "75%" }, { "80%" })
            createColorBar(Theme().colors.warning, "warning", { "70%" }, { "75%" })
            createColorBar(Theme().colors.danger, "danger", { "65%" }, { "70%" })
        }

        (::div.styled {
            margins { top { huge } }
        }) {
            createColorBar(Theme().colors.gray100, "gray100", { "55%" }, { "60%" }, "6rem")
            createColorBar(Theme().colors.gray200, "gray200", { "60%" }, { "65%" }, "6rem")
            createColorBar(Theme().colors.gray300, "gray300", { "65%" }, { "70%" }, "6rem")
            createColorBar(Theme().colors.gray400, "gray400", { "70%" }, { "75%" }, "6rem")
            createColorBar(Theme().colors.gray500, "gray500", { "75%" }, { "80%" }, "6rem")
            createColorBar(Theme().colors.gray600, "gray600", { "80%" }, { "85%" }, "6rem")
            createColorBar(Theme().colors.gray700, "gray700", { "85%" }, { "90%" }, "6rem")
        }

        showcaseSection("Color Brightness")
        val demoBrightness = 1.5

        paragraph {
            +"Additionally, you can use the function "
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

fun RenderContext.createColorBar(
    color: ColorProperty,
    colorName: String,
    boxSizeMd: SizesProperty,
    hoverSizeMd: SizesProperty,
    textWidth: String = "10rem"
): Div {
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
                    tooltip(color) { right }()
                }
                radius { "1.3rem" }
            }) {
                (::p.styled {
                    width { textWidth }
                    background {
                        color { neutral }
                    }
                    radius { "1rem" }
                    paddings {
                        left { small }
                        right { small }
                    }
                }) { +colorName }
            }
        }
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
                width(sm = { "50%" }, md = { "45%" })
                background {
                    color { color }
                }
                border {
                    width { fat }
                    color { "transparent" }
                }
                hover {
                    tooltip(color) { right }()
                }
                radius { "1.3rem" }
            }) {
                lineUp {
                    items {
                        (::p.styled {
                            width { "10rem" }
                            background {
                                color { neutral }
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
                width(sm = { "50%" }, md = { "45%" })
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
        createBrightnessDemoBar(Theme().colors.primary.base, "primary.base", brightness)
        createBrightnessDemoBar(Theme().colors.secondary.base, "secondary.base", brightness)
        createBrightnessDemoBar(Theme().colors.tertiary.base, "tertiary.base", brightness)
        createBrightnessDemoBar(Theme().colors.info, "info", brightness)
        createBrightnessDemoBar(Theme().colors.success, "success", brightness)
        createBrightnessDemoBar(Theme().colors.warning, "warning", brightness)
        createBrightnessDemoBar(Theme().colors.danger, "danger", brightness)
    }
}