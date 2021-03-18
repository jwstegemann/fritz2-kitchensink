package dev.fritz2.kitchensink.base

import dev.fritz2.components.box
import dev.fritz2.components.stackUp
import dev.fritz2.dom.html.RenderContext
import kotlinx.browser.window

/**
 * Class for configuring the appearance of a PopoverComponent.
 */
class HighlightComponent {

    init {
        window.setTimeout({
            try {
                js("""document.querySelectorAll('.highlight').forEach(function(block) {
                            hljs.highlightBlock(block);
                        });""")
            } catch (t: Throwable) {
                console.error(t)
            }
        }, 500)
    }

    var source: String = "// your code goes here"
    fun source(value: String) {
        source = value
    }

    var size: Int = 200
    fun size(value: Int) {
        size = value
    }
}

fun RenderContext.highlight(
    language: String = "kotlin",
    build: HighlightComponent.() -> Unit = {}
) {

    val component = HighlightComponent().apply(build)

    stackUp ({
        margins { top { large } }
    }){
        items {
            box({
                background { color { "#2B2B2B" } }
                radius { small }
                width { full }
                padding { small }
                paddings { left{ "22px" } }
            }) {
                pre("highlight $language") {
                    code {
                        +component.source
                    }
                }
            }
        }
    }
}