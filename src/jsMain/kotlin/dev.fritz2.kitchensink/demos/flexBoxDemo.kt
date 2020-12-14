package dev.fritz2.kitchensink.demos

import dev.fritz2.components.box
import dev.fritz2.components.flexBox
import dev.fritz2.components.lineUp
import dev.fritz2.components.stackUp
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.styling.params.*
import dev.fritz2.styling.theme.Theme
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
fun RenderContext.flexBoxDemo(): Div {

    val item: RenderContext.(String, Int) -> Unit = { color, number ->
        flexBox({
            background { color { color } }
            color { base }
            width (sm = { "50px" }, md = { "110px" }, lg = { "150px" })
            height { "50px" }
            justifyContent { center }
            alignItems { center }
            margin { smaller }
        }) { +"Box $number" }
    }

    val threeItems: RenderContext.() -> Unit = {
        listOf(Theme().colors.primary, Theme().colors.secondary, Theme().colors.tertiary)
            .forEachIndexed { index, value -> item(value, index + 1) }
    }

    val nineItems: RenderContext.() -> Unit = {
        listOf(
            Theme().colors.primary,
            Theme().colors.secondary,
            Theme().colors.tertiary,
            Theme().colors.primary,
            Theme().colors.secondary,
            Theme().colors.tertiary,
            Theme().colors.primary,
            Theme().colors.secondary,
            Theme().colors.tertiary,
        ).forEachIndexed { index, value -> item(value, index + 1) }
    }

    return contentFrame {

        showcaseHeader("Flexbox")
        paragraph {
            +"The main idea behind the flex layout is to give a container the ability to alter its items' width,"
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
        playground {
            source(
                """
                flexBox({
                    width { full }
                }) {
                    
                    box({
                        display { flex }
                        justifyContent { center }
                        alignItems { center }
                        margin { smaller }
                        width { "150px" }
                        height { "50px" }
                        background { color { primary } }
                        color { base }
                    }) { 
                        // choose any content for your flexbox
                        +"Box 1" 
                    }
                    
                    // styling omitted
                    box { +"Box 2" }
                    box { +"Box 3" }
                }
                """.trimIndent()
            )
        }

        showcaseSection("Flex Direction")
        paragraph {
            +"fritz2's styling DSL provides these well-known flex-direction properties: "
            c("row (default)")
            +", "
            c("rowReverse")
            +", "
            c("column")
            +", "
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

        playground {
            source(
                """
                flexBox({
                    direction { row }
                }) {
                    box { +"Box 1"}
                    box { +"Box 2"}
                    box { +"Box 3"}
                }
            """.trimIndent()
            )
        }

        showcaseSection("Justify Content")
        paragraph {
            +"Justify Content defines the alignment along the main axis. Our flexbox provides the default properties:"
            c("flextStart (default)")
            +", "
            c("flexEnd")
            +", "
            c("center")
            +", "
            c("spaceBetween")
            +", "
            c("spaceAround")
            +", "
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

        playground {
            source(
                """
                flexBox({
                    justifyContent { flexStart }
                }) {
                    box { +"Box 1"}
                    box { +"Box 2"}
                    box { +"Box 3"}
                }
        """.trimIndent()
            )
        }


        showcaseSection("Flex Wrap")
        paragraph {
            +"Flex items, by default, try to fit into one line. With flex wrap, you can change this behavior and"
            +" allow the items to wrap as needed."
            +"fritz2 provides these common properties for flex-wrap: "
            c("nowrap (default)")
            +", "
            c("wrap")
            +", "
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
                    nineItems()
                }
            }
        }

        playground {
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
                    box { +"Box 6"}
                    box { +"Box 7"}
                    box { +"Box 8"}
                    box { +"Box 9"}
                }                    
            """.trimIndent()
            )
        }
    }
}