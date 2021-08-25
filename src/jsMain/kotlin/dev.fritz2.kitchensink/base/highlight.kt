package dev.fritz2.kitchensink.base

import dev.fritz2.components.stackUp
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.highlightBackgroundColor
import dev.fritz2.styling.div
import kotlinx.browser.window

/**
 * Class for configuring the appearance of a PopoverComponent.
 */
class HighlightComponent(private val language: String) {

    init {
        window.setTimeout({
            try {
                js("""document.querySelectorAll('.highlight').forEach(function(block) {
                            hljs.highlightBlock(block);
                        });""")
            } catch (t: Throwable) {
                console.error(t)
            }
        }, 300)
    }

    private var source: String = ""
    fun source(value: String) {
        source = value.trimIndent()
    }

    fun render(context: RenderContext) {
        context.apply {
            stackUp ({
                margins {
                    top { large }
                    bottom { large }
                }
            }){
                items {
                    div({
                        background { color { highlightBackgroundColor } }
                        radius { small }
                        width { full }
                        padding { smaller }
                        fontFamily { mono }
                    }) {
                        pre("highlight lang-$language") {
                            code {
                                +source
                            }
                        }
                    }
                }
            }
        }
    }
}

fun RenderContext.highlight(
    language: String = "kotlin",
    build: HighlightComponent.() -> Unit = {}
) {
    HighlightComponent(language).apply(build).render(this)
}