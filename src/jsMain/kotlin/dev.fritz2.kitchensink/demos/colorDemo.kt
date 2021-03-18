package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.storeOf
import dev.fritz2.components.*
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.P
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.kitchensink.theme_
import dev.fritz2.styling.params.*
import dev.fritz2.styling.theme.ColorScheme
import dev.fritz2.styling.theme.Theme
import kotlinx.coroutines.ExperimentalCoroutinesApi


fun renderColorScheme(context: RenderContext, name: String, colorScheme: ColorScheme) {
    val hovered = storeOf(false)

    val gridAreas = object {
        val MAIN: AreaName = "main"
        val BASE: AreaName = "base"
        val BASE_CONTRAST: AreaName = "baseContrast"
        val HIGHLIGHT: AreaName = "highlight"
        val HIGHLIGHT_CONTRAST: AreaName = "highlightContrast"
    }

    val baseStyle: Style<BoxParams> = {
        justifyContent { center }
        alignItems { center }
        fontSize { normal }
    }

    val radiiSize: ScaledValueProperty = { "0.5rem" }

    context.apply {
        gridBox({
            margins { bottom { normal } }
            areas(
                sm = {
                    with(gridAreas) {
                        row(MAIN, MAIN)
                        row(BASE, BASE_CONTRAST)
                        row(HIGHLIGHT, HIGHLIGHT_CONTRAST)
                    }
                },
                md = {
                    with(gridAreas) {
                        row(MAIN, MAIN, MAIN, MAIN)
                        row(BASE, BASE_CONTRAST, HIGHLIGHT, HIGHLIGHT_CONTRAST)
                    }
                }
            )
            rows(
                sm = { "4fr 1fr 1fr" },
                md = { "2fr 1fr" }
            )
            columns(
                sm = { "1fr 1fr" },
                md = { repeat(4) { "1fr" } }
            )
            gap { tiny }
            background { color { neutral } }
            height(
                sm = { "9rem" },
                md = { "6rem" }
            )
        }) {
            flexBox({
                baseStyle(this as BoxParams)
                grid { area { gridAreas.MAIN } }
                background { color { colorScheme.base } }
                color { colorScheme.baseContrast }
                hover {
                    background { color { colorScheme.highlight } }
                    color { colorScheme.highlightContrast }
                }
                radii { top { radiiSize() } }
            }) {
                box({
                    fontSize { large }
                }) { +name }
                mouseenters.map { true } handledBy hovered.update
                mouseleaves.map { false } handledBy hovered.update
            }
            flexBox({
                grid { area { gridAreas.BASE } }
                baseStyle(this as BoxParams)
                background { color { colorScheme.base } }
                color { colorScheme.baseContrast }
                radii(
                    sm = { bottomLeft { none } },
                    md = { bottomLeft { radiiSize() } }
                )
            }) {
                hovered.data.render { if (!it) icon { fromTheme { expand } } }
                +".base"
            }
            flexBox({
                grid { area { gridAreas.BASE_CONTRAST } }
                baseStyle(this as BoxParams)
                background { color { colorScheme.baseContrast } }
                color { colorScheme.base }
            }) {
                hovered.data.render { if (!it) icon { fromTheme { edit } } }
                +".baseContrast"
            }
            flexBox({
                grid { area { gridAreas.HIGHLIGHT } }
                baseStyle(this as BoxParams)
                background { color { colorScheme.highlight } }
                color { colorScheme.highlightContrast }
                radii(
                    sm = { bottomLeft { radiiSize() } },
                    md = { bottomLeft { none } }
                )
            }) {
                hovered.data.render { if (it) icon { fromTheme { expand } } }
                +".highlight"
            }
            flexBox({
                grid { area { gridAreas.HIGHLIGHT_CONTRAST } }
                baseStyle(this as BoxParams)
                background { color { colorScheme.highlightContrast } }
                color { colorScheme.highlight }
                radii { bottomRight { radiiSize() } }
            }) {
                hovered.data.render { if (it) icon { fromTheme { edit } } }
                +".highlightContrast"
            }
        }
    }
}

fun RenderContext.createColorBar(
    color: ColorProperty,
    colorName: String,
    boxSizeMd: SizesProperty,
    hoverSizeMd: SizesProperty,
    textColor: ColorProperty = Theme().fontColor,
    textWidth: String = "10rem",
    withBackground: Boolean = true,
) {
    lineUp({
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
                }
                radius { "1.3rem" }
            }) {
                (::p.styled {
                    color { textColor }
                    width { textWidth }
                    radius { "1rem" }
                    paddings {
                        left { small }
                        right { small }
                    }
                    if (withBackground) background { color { neutral } }
                    tooltip(color) { right }()
                }) { +colorName }
            }
        }
    }
}

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
        showcaseSubSection("Color Schemes")

        paragraph {
            +"The main foundation for colors is a class named "
            c("ColorScheme")
            +" that groups semantically related ones together. It offers the following four properties:"
            ul {
                li {
                    c("base")
                    +": use for areas, surfaces, borders"
                }
                li {
                    c("baseContrast")
                    +": use for text, icons or alike rendered upon a surface colored with base"
                }
                li {
                    c("highlight")
                    +": use instead of "
                    c("base")
                    +" for effects like hovering and similar"
                }
                li {
                    c("highlightContrast")
                    +": use for text, icons or alike rendered upon a surface colored with "
                    c("highlight")
                }
            }
        }

        paragraph {
            +"The default theme provides the following three color schemes:"
        }

        renderColorScheme(this, "primary", Theme().colors.primary)
        renderColorScheme(this, "secondary", Theme().colors.secondary)
        renderColorScheme(this, "tertiary", Theme().colors.tertiary)

        showcaseSubSection("Alerts")

        paragraph {
            +"For typical alert messages we offer the following colors:"
        }

        div {
            createColorBar(Theme().colors.info, "info", { "100%" }, { "100%" })
            createColorBar(Theme().colors.success, "success", { "100%" }, { "100%" })
            createColorBar(Theme().colors.warning, "warning", { "100%" }, { "100%" })
            createColorBar(Theme().colors.danger, "danger", { "100%" }, { "100%" })
        }

        showcaseSubSection("Grays")

        paragraph {
            +"We provide 10 shades of grays with ascending brightness reflected by numbering their names accordingly:"
        }

        (::div.styled {
            margins { top { huge } }
        }) {
            createColorBar(Theme().colors.gray50, "gray50", { "100%" }, { "100%" }, "6rem")
            createColorBar(Theme().colors.gray100, "gray100", { "100%" }, { "100%" }, "6rem")
            createColorBar(Theme().colors.gray200, "gray200", { "100%" }, { "100%" }, "6rem")
            createColorBar(Theme().colors.gray300, "gray300", { "100%" }, { "100%" }, "6rem")
            createColorBar(Theme().colors.gray400, "gray400", { "100%" }, { "100%" }, "6rem")
            createColorBar(Theme().colors.gray500, "gray500", { "100%" }, { "100%" }, "6rem")
            createColorBar(Theme().colors.gray600, "gray600", { "100%" }, { "100%" }, "6rem")
            createColorBar(Theme().colors.gray700, "gray700", { "100%" }, { "100%" }, "6rem")
            createColorBar(Theme().colors.gray800, "gray800", { "100%" }, { "100%" }, "6rem")
            createColorBar(Theme().colors.gray900, "gray900", { "100%" }, { "100%" }, "6rem")
        }

        showcaseSubSection("Others")
        p {
            +"Last but not least there exist colors for typical global settings:"
        }

        createColorBar(Theme().backgroundColor, "backgroundColor", { "100%" }, { "100%" }, "6rem")
        createColorBar(Theme().fontColor, "fontColor", { "100%" }, { "100%" }, "6rem")
    }
}
