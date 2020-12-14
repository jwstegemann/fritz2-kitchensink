package dev.fritz2.kitchensink.demos

import dev.fritz2.components.box
import dev.fritz2.components.lineUp
import dev.fritz2.components.tooltip
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
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
