package dev.fritz2.kitchensink.base

import dev.fritz2.components.box
import dev.fritz2.components.stackUp
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.highlightBackgroundColor
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
        }, 300)
    }

    var source: String = "// your code goes here"
    fun source(value: String) {
        source = value.trimIndent()
    }
}

fun RenderContext.highlight(
    language: String = "kotlin",
    build: HighlightComponent.() -> Unit = {}
) {

    val component = HighlightComponent().apply(build)

    stackUp ({
        margins {
            top { large }
            bottom { large }
        }
    }){
        items {
            box({
                background { color { highlightBackgroundColor } }
                radius { small }
                width { full }
                padding { smaller }
                fontFamily { mono }
            }) {
                pre("highlight lang-$language") {
                    code {
                        +component.source
                    }
                }
            }
        }
    }
}