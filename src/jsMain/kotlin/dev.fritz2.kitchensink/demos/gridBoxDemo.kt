package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.storeOf
import dev.fritz2.components.gridBox
import dev.fritz2.components.pushButton
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.styling.div
import dev.fritz2.styling.p
import dev.fritz2.styling.params.AreaName
import dev.fritz2.styling.params.end
import dev.fritz2.styling.params.start
import dev.fritz2.styling.theme.Theme
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
fun RenderContext.gridBoxDemo(): Div {
    // example from https://developer.mozilla.org/en/docs/Web/CSS/CSS_Grid_Layout/Layout_using_Named_Grid_Lines

    return contentFrame {
        val grid = object {
            val HEADER: AreaName = "header"
            val SIDEBAR: AreaName = "sidebar"
            val CONTENT: AreaName = "content"
            val FOOTER: AreaName = "footer"
        }
        showcaseHeader("GridBox")
        paragraph {
            +"Use a gridbox to create arbitrary complex layouts."
            +" It doesn't need special component properties, but instead relies on the "
            externalLink(
                "CSS based grid-layout",
                "https://developer.mozilla.org/en-US/docs/Web/CSS/CSS_Grid_Layout"
            )
            +" styling options."
        }
        showcaseSection("Usage")
        paragraph {
            +"In order to create a gridbox, you need to provide some specialized grid styling information."
            +" In this simple example, a four column grid layout is defined, but seven items are inserted."
            +" The gridbox then renders the surplus items into a second row:"
        }
        componentFrame {
            gridBox({
                columns { repeat(4) { "1fr" } }
                gap { normal }
                children("div") {
                    width { "120px" }
                    height { "50px" }
                    color { primary.mainContrast }
                    background { color { primary.main } }
                    display { flex }
                    radius { small }
                    css("justify-content: center")
                    css("align-items: center")
                }
            }) {
                div { +"one" }
                div { +"two" }
                div { +"three" }
                div { +"four" }
                div { +"five" }
                div { +"six" }
                div { +"seven" }
            }
        }
        highlight {
            source(
                """
                gridBox({
                    columns { repeat(4) { "1fr" } }
                    gap { normal }
                    children("div") {
                        width { "120px" }
                        height { "50px" }
                        color { primary.mainContrast }
                        background { color { primary.main } }
                        display { flex }
                        radius { small }
                        css("justify-content: center")
                        css("align-items: center")
                    }
                }) { // choose any content
                    div { +"one" }
                    div { +"two" }
                    div { +"three" }
                    div { +"four" }
                    div { +"five" }
                    div { +"six" }
                    div { +"seven" }
                }               
                """.trimIndent()
            )
        }

        showcaseSection("Complex Layout")
        paragraph {
            +"The following more complex layout includes responsive behaviour. It demonstrates"
            +" a very helpful technique based on Kotlin's "
            c("objects")
            +" for defining the overall column layout."
        }
        componentFrame {
            val toggle = storeOf(false)
            gridBox({
                fontSize { normal }
                columns {
                    repeat(3) { "1fr" }
                }

                css(
                    sm = "grid-template-rows: 1fr 1fr 3fr 1fr",
                    md = "grid-template-rows: 1fr 4fr 1fr"
                )

                areas(
                    sm = { // structure for small displays
                        with(grid) {
                            row(HEADER, HEADER, HEADER)
                            row(SIDEBAR, SIDEBAR, SIDEBAR)
                            row(CONTENT, CONTENT, CONTENT)
                            row(FOOTER, FOOTER, FOOTER)
                        }
                    },
                    md = { // structure for medium and larger displays
                        with(grid) {
                            row(HEADER, HEADER, HEADER)
                            row(SIDEBAR, CONTENT, CONTENT)
                            row(FOOTER, FOOTER, FOOTER)
                        }
                    }
                )
                gap { large }
            }) {
                div({
                    grid { area { grid.HEADER } }
                    background {
                        color { gray300 }
                    }
                    paddings { all { "0.2rem" } }
                }) {
                    p({
                        padding { "1rem" }
                    }) { +"Header" }
                }
                div({
                    grid { area { grid.SIDEBAR } }
                    background { color { primary.main } }
                    color { gray300 }
                    paddings { all { "0.2rem" } }
                }) {
                    p({
                        padding { "1rem" }
                    }) { +"Sidebar" }
                }
                div({
                    paddings { all { "0.2rem" } }
                    grid(sm = { area { grid.CONTENT } })
                    background(
                        sm = {
                            image { "https://via.placeholder.com/300/?text=small%20sized%20device" }
                            repeat { repeat }
                            positions {
                                horizontal { center }
                                vertical { center }
                            }
                        },
                        md = {
                            image { "https://via.placeholder.com/300/A9A9A9?text=medium%20sized%20device" }
                            repeat { repeat }
                            positions {
                                horizontal { center }
                                vertical { center }
                            }
                        }

                    )
                }) {
                    p({
                        padding { "1rem" }
                    }){ +"Content" }
                }
                div({
                    grid { area { grid.FOOTER } }
                    background { color { gray300 } }
                    paddings { all { "0.2rem" } }
                }) {
                    p({
                        padding { "1rem" }
                    }) { +"Footer" }
                }
                toggle.data.render { show ->
                    if (show) {
                        div({
                            margin { none }
                            paddings { all { "0.2rem" } }
                            grid(
                                sm = { // structure for small displays
                                    row {
                                        start { grid.HEADER.start }
                                        end { grid.CONTENT.end }
                                    }
                                    column {
                                        start { grid.CONTENT.start }
                                        end { grid.CONTENT.end }
                                    }
                                },
                                md = { // structure for medium and larger displays
                                    row {
                                        start { grid.HEADER.start }
                                        end { span(2) }
                                    }
                                    column {
                                        start { "3" }
                                        end { grid.CONTENT.end }
                                    }
                                }
                            )
                            background {
                                color { secondary.main }
                            }
                            padding { normal }
                            textAlign { center }
                        }) {
                             +"Drawer"
                        }
                    }
                }
            }
            pushButton({
                margins { top { normal } }
            }) {
                text("Toggle The Drawer")
                events {
                    clicks.events.map { !toggle.current } handledBy toggle.update
                }
            }
        }

        coloredBox(Theme().colors.info){
            +"This layout also transforms with screen size. Try resizing your browser window to see "
            +"how the sidebar is placed into its own row when the space narrows. "
            +"The content then appears on a separate row below."
        }

        showcaseSubSection("Column Layout")
        paragraph {
            +"Use a simple Kotlin object to define the "
            strong { +"types" }
            +" of columns."
            +" This gives you access to its properties for the column definitions:"
        }
        highlight {
            source(
                """
                // define the cells
                val grid = object {
                    val HEADER: AreaName = "header"
                    val SIDEBAR: AreaName = "sidebar"
                    val CONTENT: AreaName = "content"
                    val FOOTER: AreaName = "footer"
                }                    
                
                gridBox({
                    columns {
                        repeat(3) { "1fr" }
                    }
                                    
                    // refer to those, easy refactoring included
                    areas(
                        sm = {
                            with(grid) {
                                row(HEADER, HEADER, HEADER)
                                row(SIDEBAR, SIDEBAR, SIDEBAR)
                                row(CONTENT, CONTENT, CONTENT)
                                row(FOOTER, FOOTER, FOOTER)
                            }
                        },
                        md = {
                            with(grid) {
                                row(HEADER, HEADER, HEADER)
                                row(SIDEBAR, CONTENT, CONTENT)
                                row(FOOTER, FOOTER, FOOTER)
                            }
                        }
                    )
                }) // ...
                """.trimIndent()
            )
        }
        paragraph {
            +"The object also comes in handy for defining content:"
        }
        highlight {
            source(
                """
                gridBox({
                    // omitted, same as above
                }) {
                    div({
                        grid { area { grid.HEADER } } // refer to cell type 
                    }) {
                        // your content
                    }
                    // next type
                    div({
                        grid { area { grid.CONTENT } }  
                    }) {
                        // your content
                    }
                    // and so on..
                }
                """.trimIndent()
            )
        }

        showcaseSubSection("Defining Areas")
        paragraph {
            +"In order to group cells together, you can also define areas by referring to the name of the cell type."
            +" This technique is used for the simple togglable drawer above, that appears on the right side by"
            +" the click of the button below the gridbox."
        }
        highlight {
            source(
                """
                gridBox({
                    // as above
                }) {
                    // define the drawer
                    div({
                        grid(
                            sm = { // structure for small displays
                                row {
                                    start { grid.HEADER.start }
                                    end { grid.CONTENT.end }
                                }
                                column {
                                    start { grid.CONTENT.start }
                                    end { grid.CONTENT.end }
                                }
                            },
                            md = { // structure for medium and larger displays
                                row {
                                    start { grid.HEADER.start }
                                    end { span(2) }
                                }
                                column {
                                    start { "3" }
                                    end { grid.CONTENT.end }
                                }
                            }
                        )
                    }) {
                        // put the special content for the drawer
                    }
                }
            """.trimIndent()
            )
        }
    }
}