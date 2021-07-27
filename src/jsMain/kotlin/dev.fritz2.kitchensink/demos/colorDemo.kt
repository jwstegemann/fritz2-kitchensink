package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.storeOf
import dev.fritz2.components.flexBox
import dev.fritz2.components.gridBox
import dev.fritz2.components.lineUp
import dev.fritz2.components.tooltip
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.kitchensink.theme_
import dev.fritz2.styling.div
import dev.fritz2.styling.p
import dev.fritz2.styling.params.*
import dev.fritz2.styling.theme.ColorScheme
import dev.fritz2.styling.theme.Theme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map


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
            background { color { neutral.main } }
            height(
                sm = { "9rem" },
                md = { "6rem" }
            )
        }) {
            flexBox({
                baseStyle(this as BoxParams)
                grid { area { gridAreas.MAIN } }
                background { color { colorScheme.main } }
                color { colorScheme.mainContrast }
                hover {
                    background { color { colorScheme.highlight } }
                    color { colorScheme.highlightContrast }
                }
                radii { top { radiiSize() } }
            }) {
                div({
                    fontSize { large }
                }) { +name }
                mouseenters.map { true } handledBy hovered.update
                mouseleaves.map { false } handledBy hovered.update
            }
            flexBox({
                grid { area { gridAreas.BASE } }
                baseStyle(this as BoxParams)
                background { color { colorScheme.main } }
                color { colorScheme.mainContrast }
                radii(
                    sm = { bottomLeft { none } },
                    md = { bottomLeft { radiiSize() } }
                )
                tooltip(colorScheme.main)()
            }) {
                hovered.data.map { if(!it) "main" else "" }.asText()
            }
            flexBox({
                grid { area { gridAreas.BASE_CONTRAST } }
                baseStyle(this as BoxParams)
                background { color { colorScheme.mainContrast } }
                color { colorScheme.main }
                tooltip(colorScheme.mainContrast)()
            }) {
                hovered.data.map { if(!it) "mainContrast" else "" }.asText()
            }
            flexBox({
                grid { area { gridAreas.HIGHLIGHT } }
                baseStyle(this as BoxParams)
                background { color { colorScheme.highlight } }
                color { colorScheme.highlightContrast }
                tooltip(colorScheme.highlight)()
                radii(
                    sm = { bottomLeft { radiiSize() } },
                    md = { bottomLeft { none } }
                )
            }) {
                hovered.data.map { if(it) "highlight" else "" }.asText()
            }
            flexBox({
                grid { area { gridAreas.HIGHLIGHT_CONTRAST } }
                baseStyle(this as BoxParams)
                background { color { colorScheme.highlightContrast } }
                color { colorScheme.highlight }
                tooltip(colorScheme.highlightContrast)()
                radii { bottomRight { radiiSize() } }
            }) {
                hovered.data.map { if(it) "highlightContrast" else "" }.asText()
            }
        }
    }
}

fun RenderContext.createColorBar(
    color: ColorProperty,
    colorName: String,
    withBorder: Boolean = false,
) {
    lineUp({
        margins {
            top { tiny }
        }
    }) {
        items {
            div({
                width { "100%" }
                background {
                    color { color }
                }
                hover {
                    width { "100%" }
                }
                radius { "1.3rem" }
                if(withBorder) border { width { hair } }
            }) {
                p({
                    width { "6rem" }
                    radius { "1rem" }
                    paddings {
                        left { small }
                        right { small }
                    }
                    margin { "0.3rem" }
                    background { color { neutral.main } }
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
            +" For more information on using themes, have a look at the "
            internalLink("Themes section", theme_)
            +"."
        }

        showcaseSubSection("Color Schemes")
        paragraph {
            +"The main foundation for colors is a class named "
            c("ColorScheme")
            +" that groups semantically related together. It offers the following four properties:"
            ul {
                li {
                    c("main")
                    +" use for areas, surfaces, borders"
                }
                li {
                    c("mainContrast")
                    +" use for text, icons or alike placed upon a surface colored with "
                    c("main")
                }
                li {
                    c("highlight")
                    +" use instead of "
                    c("main")
                    +" for effects like hovering and similar"
                }
                li {
                    c("highlightContrast")
                    +" use for text, icons or alike placed upon a surface colored with "
                    c("highlight")
                }
            }
            +"The "
            c("ColorScheme")
            +"brings a function named "
            c("inverted()")
            +" it returns a "
            c("ColorScheme")
            +"which switches "
            c("base")
            +"  with "
            c("highlight")
            +" and "
            c("baseContrast")
            +"with "
            c("highlightContrast")
            +". A default use case can be if you want to create easily an inverted theme."
        }



        paragraph {
            +"The default theme provides the following color schemes:"
        }

        renderColorScheme(this, "primary", Theme().colors.primary)
        renderColorScheme(this, "secondary", Theme().colors.secondary)
        renderColorScheme(this, "tertiary", Theme().colors.tertiary)
        renderColorScheme(this, "neutral", Theme().colors.neutral)

        showcaseSubSection("Signal Colors")
        paragraph {
            +"For typical alert messages we offer the following sets of"
            c("ColorScheme")
        }
        renderColorScheme(this, "info", Theme().colors.info)
        renderColorScheme(this, "success", Theme().colors.success)
        renderColorScheme(this, "warning", Theme().colors.warning)
        renderColorScheme(this, "danger", Theme().colors.danger)

        showcaseSubSection("Shades of Gray")

        paragraph {
            +"We provide 10 shades of grays with ascending brightness reflected by numbering their names accordingly:"
        }

        createColorBar(Theme().colors.gray50, "gray50")
        createColorBar(Theme().colors.gray100, "gray100")
        createColorBar(Theme().colors.gray200, "gray200")
        createColorBar(Theme().colors.gray300, "gray300")
        createColorBar(Theme().colors.gray400, "gray400")
        createColorBar(Theme().colors.gray500, "gray500")
        createColorBar(Theme().colors.gray600, "gray600")
        createColorBar(Theme().colors.gray700, "gray700")
        createColorBar(Theme().colors.gray800, "gray800")
        createColorBar(Theme().colors.gray900, "gray900")

        showcaseSubSection("Others")
        p {
            +"Last but not least there exist colors for typical global settings:"
        }

        createColorBar(Theme().colors.background, "backgroundColor", true)
        createColorBar(Theme().colors.font, "fontColor")
    }
}
