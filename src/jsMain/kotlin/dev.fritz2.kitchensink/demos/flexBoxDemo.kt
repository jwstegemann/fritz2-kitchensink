package dev.fritz2.kitchensink.demos

import dev.fritz2.components.flexBox
import dev.fritz2.components.lineUp
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.styling.params.DirectionValues
import dev.fritz2.styling.params.JustifyContentValues
import dev.fritz2.styling.params.WrapValues
import dev.fritz2.styling.theme.Theme
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
fun RenderContext.flexBoxDemo(): Div {

    val item: RenderContext.(String, Int) -> Unit = { color, number ->
        flexBox({
            background { color { color } }
            colors { neutral }
            width (sm = { "50px" }, md = { "110px" }, lg = { "150px" })
            height { "50px" }
            justifyContent { center }
            alignItems { center }
            margin { smaller }
            radius { small }
        }) { +"Box $number" }
    }

    val threeItems: RenderContext.() -> Unit = {
        listOf(Theme().colors.primary.base, Theme().colors.secondary.base, Theme().colors.tertiary.base)
            .forEachIndexed { index, value -> item(value, index + 1) }
    }
    val fiveItems: RenderContext.() -> Unit = {
        listOf(
            Theme().colors.primary.base,
            Theme().colors.secondary.base,
            Theme().colors.tertiary.base,
            Theme().colors.success.base,
            Theme().colors.info.base,
        ).forEachIndexed { index, value -> item(value, index + 1) }
    }

    return contentFrame {

        showcaseHeader("Flexbox")
        paragraph {
            +"The flex layout enables a container to alter its items' width,"
            +" height, and order to best fill the available space. The container is basically a box which has the css"
            +" property "
            c("display: flex")
            +" attached."

        }
        showcaseSection("Usage")
        paragraph {
            +"A Flexbox does not need special properties, so this fritz2 component does not abstract anything"
            +" on top of the css basics. We believe their level of abstraction to be sufficient, and they"
            +" also offer enough flexibility."
        }
        componentFrame {
            lineUp {
                items {
                    flexBox({
                        width { full }
                    }) {
                        threeItems()
                    }
                }
            }
        }
        highlight {
            source(
                """
                flexBox({
                    width { full }
                }) {
                   
                    box({
                        background { color { primary.base } }
                        color { neutral }
                        width (sm = { "50px" }, md = { "110px" }, lg = { "150px" })
                        height { "50px" }
                        justifyContent { center }
                        alignItems { center }
                        margin { smaller }
                        radius { small }
                    }) { 
                        // choose any content for your flexbox
                        +"Box 1" 
                    }
                    
                    // styling omitted
                    box { +"Box 2" }
                    box { +"Box 3" }
                }
                """
            )
        }

        showcaseSection("Flex Direction")
        paragraph {
            +"fritz2's styling DSL provides these well-known flex-direction properties: "
            c("row")
            +" (default), "
            c("rowReverse")
            +", "
            c("column")
            +", and "
            c("columnReverse")
        }

        listOf(
            DirectionValues.row,
            DirectionValues.rowReverse,
            DirectionValues.column,
            DirectionValues.columnReverse,
        ).forEach {
            componentFrame {
                flexBox({
                    width { full }
                    direction { it }
                }) {
                    threeItems()
                }
            }
        }

        highlight {
            source(
                """
                flexBox({
                    direction { row }
                }) {
                    box { +"Box 1"}
                    box { +"Box 2"}
                    box { +"Box 3"}
                }
            """
            )
        }

        showcaseSection("Justify Content")
        paragraph {
            +"Justify Content defines the alignment along the main axis. Our flexbox provides these property values:"
            c("flexStart")
            +" (default), "
            c("flexEnd")
            +", "
            c("center")
            +", "
            c("spaceBetween")
            +", "
            c("spaceAround")
            +", and "
            c("spaceEvenly")
        }

        listOf(
            JustifyContentValues.flexStart,
            JustifyContentValues.flexEnd,
            JustifyContentValues.center,
            JustifyContentValues.spaceBetween,
            JustifyContentValues.spaceAround,
            JustifyContentValues.spaceEvenly
        ).forEach {
            componentFrame {
                flexBox({
                    width { full }
                    justifyContent { it }
                }) {
                    threeItems()
                }
            }
        }

        highlight {
            source(
                """
                flexBox({
                    justifyContent { flexStart }
                }) {
                    box { +"Box 1"}
                    box { +"Box 2"}
                    box { +"Box 3"}
                }
                """
            )
        }


        showcaseSection("Flex Wrap")
        paragraph {
            +"Flex items, by default, try to fit into one line. With flex wrap, you can change this behavior and"
            +" allow the items to wrap as needed."
            +" fritz2 provides these common properties for flex-wrap: "
            c("nowrap")
            +" (default), "
            c("wrap")
            +", and "
            c("wrap-reverse")


        }

        listOf(
            WrapValues.nowrap,
            WrapValues.wrap,
            WrapValues.wrapReverse
        ).forEach {
            componentFrame {
                flexBox({
                    width { full }
                    wrap { it }
                }) {
                    fiveItems()
                }
            }
        }

        highlight {
            source(
                """
                flexBox({
                    wrap { noWrap }
                }) {
                    box { +"Box 1"}
                    box { +"Box 2"}
                    box { +"Box 3"}
                    box { +"Box 4"}
                    box { +"Box 5"}
                }                    
                """
            )
        }
    }
}