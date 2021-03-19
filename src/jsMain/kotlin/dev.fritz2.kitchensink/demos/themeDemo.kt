package dev.fritz2.kitchensink.demos

import dev.fritz2.components.lineUp
import dev.fritz2.components.switch
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.dom.states
import dev.fritz2.kitchensink.ThemeStore
import dev.fritz2.kitchensink.base.*
import dev.fritz2.kitchensink.colors_
import dev.fritz2.kitchensink.icons_
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map

@ExperimentalCoroutinesApi
fun RenderContext.themeDemo(): Div {

    return contentFrame {

        showcaseHeader("Themes")

        paragraph {
            +"A "
            c("Theme")
            +" "
            +""" groups predefined sets of values and scales to implement a consistent constraint-based design system.
            """.trimIndent()
        }

        paragraph {
            +"fritz2's components come with a predefined "
            c("DefaultTheme")
            +" "
            +""" which can be used to style your app. Of course you can derive custom themes 
                from it to change values, or even to extend the theme by your own definitions, from simple property 
                values to complex predefined styles.""".trimIndent()
        }

        showcaseSection("Content")
        paragraph {
            +"A theme groups the following presets:"
        }
        table("settings-table") {
            tr {
                th { +"Name" }
                th { +"Type" }
                th { +"Description" }
            }
            tr {
                td { +"reset" }
                td { c("String") }
                td { +"CSS to reset browser's defaults and set your own" }
            }
            tr {
                td { +"name" }
                td { c("String") }
                td { +"A human-readable name" }
            }
            tr {
                td { +"breakPoints" }
                td { c("ResponsiveValue") }
                td { +"Break points for different screen sizes when working with ResponsiveValues" }
            }
            tr {
                td { +"space" }
                td { c("ScaledValue") }
                td { +"Scale for spacing (margin, padding, etc.)" }
            }
            tr {
                td { +"position" }
                td { c("ScaledValue") }
                td { +"Scale for positions (top, bottom, etc.)" }
            }
            tr {
                td { +"fontSizes" }
                td { c("ScaledValue") }
                td { +"Scale for font-sizes" }
            }
            tr {
                td { +"colors" }
                td { c("Colors") }
                td { +"Theme's color-scheme, see "
                    internalLink("Colors", colors_)
                }
            }
            tr {
                td { +"fonts" }
                td { c("Fonts") }
                td { +"Definition of used fonts" }
            }
            tr {
                td { +"lineHeights" }
                td { c("ScaledValue") }
                td { +"Scale for line-heights" }
            }
            tr {
                td { +"letterSpacings" }
                td { c("ScaledValue") }
                td { +"Scale for letter-spacing" }
            }
            tr {
                td { +"sizes" }
                td { c("Sizes") }
                td { +"Scale for sizes (width, height)" }
            }
            tr {
                td { +"borderWidths" }
                td { c("Thickness") }
                td { +"Scale for border-widths" }
            }
            tr {
                td { +"radii" }
                td { c("ScaledValue") }
                td { +"Scale for border-radius" }
            }
            tr {
                td { +"shadows" }
                td { c("Shadows") }
                td { +"Set of shadows used in the theme" }
            }
            tr {
                td { +"zIndices" }
                td { c("ZIndices") }
                td { +"Scheme used for layering" }
            }
            tr {
                td { +"opacities" }
                td { c("WeightedValue") }
                td { +"Scale for opacities" }
            }
            tr {
                td { +"gaps" }
                td { c("ScaledValue") }
                td { +"Scale for gaps" }
            }
            tr {
                td { +"icons" }
                td { c("Icons") }
                td { +"Set of icons used in the theme, see "
                    internalLink("Icons", icons_) }
            }

        }

        showcaseSection("Custom Themes")
        paragraph {
            +"To create a custom "
            c("Theme")
            +", simply create a new class inheriting from "
            c("DefaultTheme")
            +" and change only the definitions you want."

        }
        highlight {
            source(
                """
                    class dev.fritz2.kitchensink.base.LargeFonts : DefaultTheme() {
                        override val name = "large Fonts"
                    
                        override val fontSizes = ScaledValue(
                            smaller = "1.125rem",
                            small = "1.25rem",
                            normal = "1.5rem",
                            large = "1.875rem",
                            larger = "2.25rem",
                            huge = "3rem"
                        )                    
                    }
                """
            )
        }

        showcaseSection("Changing the current Theme")
        paragraph {
            +"To access the current theme, use "
            c("Theme()")
            +". Change the current theme with "
            c("Theme.use(myTheme)")
            +"."
        }
        componentFrame {
            lineUp {
                items {
                    switch {
                        label("Use large fonts theme")
                        checked ( ThemeStore.data.map { it == 1 } )
                        events {
                            changes.states().map { if (it) 1 else 0 } handledBy ThemeStore.selectTheme
                        }
                    }
                }
            }
        }
        highlight {
            source(
                """
                    val themes = listOf<ExtendedTheme>(SmallFonts(), LargeFonts())

                    object ThemeStore : RootStore<Int>(0) {
                        val selectTheme = handle<Int> { _, index ->
                            Theme.use(themes[index])
                            index
                        }
                    }
                        
                    //set your default theme    
                    render(themes.first()) { theme ->
                        //...

                        switch {
                            label("Use large fonts theme")
                            checked ( ThemeStore.data.map { it == 1 } )
                            events {
                                changes.states().map { if (it) 1 else 0 } handledBy ThemeStore.selectTheme
                            }
                        }
                    }
                """
            )
        }
    }
}