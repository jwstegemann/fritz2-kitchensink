package dev.fritz2.kitchensink.demos

import dev.fritz2.binding.storeOf
import dev.fritz2.components.gridBox
import dev.fritz2.components.icon
import dev.fritz2.components.popup
import dev.fritz2.components.popup.Placement
import dev.fritz2.components.slider
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.base.*
import dev.fritz2.styling.*
import dev.fritz2.styling.params.BasicParams
import dev.fritz2.styling.params.Style
import dev.fritz2.styling.theme.Theme


val triggerStyle: Style<BasicParams> = {
    background { color { primary.main } }
    color { primary.mainContrast }
    radius { normal }
    padding { normal }
}

val contentStyle: Style<BasicParams> = {
    background { color { primary.highlight } }
    color { primary.highlightContrast }
    radius { normal }
    padding { normal }
}

val offsetStore = storeOf(10)

fun RenderContext.createHoveringPopup(
    name: String,
    placement: Placement,
    flipping: Boolean = false,
    text: String = "Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt " +
            "ut labore."
) =
    offsetStore.data.render { offset ->
        popup({
            zIndex { appFrame.plus(10) }
        }) {
            flipping(flipping)
            offset(offset.toDouble())
            placement { placement }
            trigger { toggle, close ->
                div({
                    grid { area { name } }
                    triggerStyle()
                    margin { small }
                    display { flex }
                    justifyContent { center }
                }) {
                    +name
                    mouseenters.map { it.currentTarget } handledBy toggle
                    clicks.map { it.currentTarget } handledBy toggle
                    mouseleaves.map { } handledBy close
                }
            }
            content {
                div({
                    contentStyle()
                    maxWidth { "15rem" }
                }) {
                    +text
                }
            }
        }
    }

fun RenderContext.popupDemo(): Div {

    return contentFrame {
        showcaseHeader("Popup")
        paragraph {
            +"The Popup is a generic component to render some arbitrary content as overlay over the rest of the "
            +" application activated by some trigger like a button click or some hovering action."
        }
        coloredBox(Theme().colors.info) {
            +"The Popup acts as the foundation for other commonly used components, like "
            internalLink("Tooltip", "Tooltip")
            +", PopupMenu, or "
            +"PopupCard. You should prefer those ready to use components over tinkering your own specific popup "
            +"variation. The latter should be really the last resort, if all other popup based elements won't fit!"
        }
        coloredBox(Theme().colors.warning) {
            +"PopupCard "
            externalLink("(Issue #517)", "https://github.com/jwstegemann/fritz2/issues/517")
            +" and PopupMenu "
            externalLink("(Issue #518)", "https://github.com/jwstegemann/fritz2/issues/518")
            +" will be delivered soon and replace the currently available components "
            internalLink("Dropdown", "Dropdown")
            +" and "
            internalLink("Popover", "Popover")
            +"."
        }

        showcaseSection("Usage")
        paragraph {
            +"In order to create a popover one has to define a "
            c("trigger")
            +" which is the element that invokes the popup content rendering and the "
            c("content")
            +" itself. Last but not least one triggering event must be connected to the "
            c("toggle")
            +" handler injected into the "
            c("trigger")
            +" context, in order to open and close the content."
        }
        componentFrame {
            popup({
                zIndex { appFrame.plus(10) }
            }) {
                trigger { toggle, _ ->
                    div({
                        triggerStyle()
                        margin { normal }
                    }) {
                        +"Click me to toggle!"
                        clicks.map { it.currentTarget } handledBy toggle
                    }
                }
                content { close ->
                    div(contentStyle) {
                        +"Some content to be displayed as overlay"
                    }
                }
            }
        }
        highlight {
            source(
                """
                popup {
                    trigger { toggle, _ ->
                        div {
                            +"Click me to toggle!"
                            clicks.map { it.currentTarget } handledBy toggle
                        }
                    }
                    content {
                        div { +"Some content to be displayed as overlay" }
                    }
                }
                """
            )
        }

        showcaseSection("Events")
        paragraph {
            +"In order to use the Popup to implement some fitting overlay the event handling is the most important "
            +"aspect to know about. The Popup enables one to assign events to three dedicated handlers, which are "
            +"passed as parameters into two different context sections:"
            ul {
                li {
                    c("trigger")
                    +": Offers "
                    c("toggle")
                    +" and "
                    c("close")
                    +" as handlers. The first needs some "
                    c("EventTarget")
                    +" as type, the second just "
                    c("Unit")
                    +"."
                }
                li {
                    c("content")
                    +": Offers a "
                    c("close")
                    +" handler only."
                }
            }
            +"Those handlers enables one to realize all sceneries of opening and closing a popup"
        }
        highlight {
            source(
                """
                popup {
                    trigger { toggle, close ->
                    }
                    content { close ->
                    }
                }
                """
            )
        }
        paragraph {
            +"Typical use cases are:"
            ul {
                li {
                    +"Tooltips: "
                    em { +"Toggle on hovering" }
                }
                li {
                    +"PopupCard, PopupMenu: "
                    em { +"Toggle on mouse click" }
                }
            }
        }

        showcaseSubSection("Toggle on hovering")
        componentFrame {
            popup({
                zIndex { appFrame.plus(10) }
            }) {
                trigger { toggle, close ->
                    div({
                        triggerStyle()
                        margin { normal }
                    }) {
                        +"Hover me to toggle!"
                        mouseenters.map { it.currentTarget } handledBy toggle
                        mouseleaves.map { } handledBy close
                    }
                }
                content {
                    div(contentStyle) {
                        +"Some content to be displayed as overlay"
                    }
                }
            }
        }
        highlight {
            source(
                """
                popup {
                    trigger { toggle, close -> // bind both handlers to identifiers
                        div {
                            +"Hover me to toggle!"
                            // open only...
                            mouseenters.map { it.currentTarget } handledBy toggle
                            // ...because we close always on leave!
                            mouseleaves.map { } handledBy close
                        }
                    }
                    content {
                        div(contentStyle) {
                            +"Some content to be displayed as overlay"
                        }
                    }
                }
                """
            )
        }

        showcaseSubSection("Toggle on click")
        componentFrame {
            popup({
                zIndex { appFrame.plus(10) }
            }) {
                trigger { toggle, close ->
                    div({
                        triggerStyle()
                        margin { normal }
                    }) {
                        +"Click me to toggle!"
                        clicks.map { it.currentTarget } handledBy toggle
                    }
                }
                content { close ->
                    div({
                        contentStyle()
                        margins { left { large } }
                    }) {
                        +"Some content to be displayed as overlay"
                        icon({
                            position {
                                absolute {
                                    top { tiny }
                                    right { tiny }
                                }
                            }
                        }) {
                            fromTheme { circleError }
                            events {
                                clicks.map { } handledBy close
                            }
                        }
                    }
                }
            }
        }
        highlight {
            source(
                """
                popup {
                    trigger { toggle, _ -> // only toggle needed
                        div {
                            +"Click me to toggle!"
                            clicks.map { it.currentTarget } handledBy toggle
                        }
                    }
                    content { close -> // capture handler on content too
                        div {
                            +"Some content to be displayed as overlay"
                            icon({
                                position {
                                    absolute {
                                        top { tiny }
                                        right { tiny }
                                    }
                                }
                            }) {
                                fromTheme { circleError }
                                events {
                                    // use close handler on icon
                                    clicks.map { } handledBy close
                                }
                            }
                        }
                    }
                }
                """
            )
        }

        showcaseSection("Positioning")

        val placements = listOf(
            "top" to Placement.Top,
            "topStart" to Placement.TopStart,
            "topEnd" to Placement.TopEnd,
            "bottom" to Placement.Bottom,
            "bottomStart" to Placement.BottomStart,
            "bottomEnd" to Placement.BottomEnd,
            "left" to Placement.Left,
            "leftStart" to Placement.LeftStart,
            "leftEnd" to Placement.LeftEnd,
            "right" to Placement.Right,
            "rightStart" to Placement.RightStart,
            "rightEnd" to Placement.RightEnd
        )

        showcaseSubSection("Manual Positioning")
        paragraph {
            +"The Popup component offers the following parameters to configure its appearance onto the screen:"
            ul {

                li {
                    c("offset")
                    +": defines the space between the trigger element and the popup content. Only a pure "
                    c("float")
                    +" based value of pixels is accepted due to the internal positioning calculation!"
                }
                li {
                    c("placement")
                    +": accepts one value of the discrete enumeration of:"
                    ul {
                        placements.forEach {
                            li { c(it.first) }
                        }
                    }
                }
            }
        }
        componentFrame {
            gridBox({
                columns(
                    sm = { repeat(3) { "1fr" } },
                    md = { repeat(5) { "1fr" } }
                )
                areas(
                    sm = {
                        row("topStart", " top ", " topEnd ")
                        row("leftStart", "offset", "rightStart")
                        row("left", "slider", "right")
                        row("leftEnd", ".", "rightEnd")
                        row("bottomStart", "bottom", "bottomEnd")
                    },
                    md = {
                        row(".", "topStart", "top", "topEnd", ".")
                        row("leftStart", ".", ".", ".", "rightStart")
                        row("left", "slider", "slider", "offset", "right")
                        row("leftEnd", ".", ".", ".", "rightEnd")
                        row(".", "bottomStart", "bottom", "bottomEnd", ".")
                    }
                )
            }) {
                placements.forEach { (name, placement) ->
                    createHoveringPopup(name, placement)
                }
                div({
                    grid { area { "slider" } }
                    display { flex }
                    alignItems { center }
                    margin { small }
                }) {
                    slider(value = offsetStore) {
                    }
                }
                offsetStore.data.render { offset ->
                    div({
                        grid { area { "offset" } }
                        display { flex }
                        alignItems { center }
                        justifyContent { center }
                    }) {
                        +"Offset: $offset"
                    }
                }
            }
        }
        highlight {
            source(
                """
                popup {
                    offset(25) // 10 is default
                    placement { leftEnd } // top is default
                    // almost similar to first examples above...
                    trigger { toggle, close ->
                        div {
                            +"leftEnd"
                            mouseenters.map { it.currentTarget } handledBy toggle
                            mouseleaves.map { } handledBy close
                        }
                    }
                    content {
                        div {
                            +"Lorem ipsum..."
                        }
                    }
                }                    
                """.trimIndent()
            )
        }

        showcaseSubSection("Automatic Positioning")
        paragraph {
            +"As it is almost impossible to ensure that there is always enough space for rendering the content of a "
            +"Popup at design time (think of some vertical app layout, where the user can scroll up and down), "
            +"this component offers a built-in mechanism to detect too small area for rendering the content: "
            c("flipping")
        }
        paragraph {
            +"By default "
            c("flipping")
            +" is activated, so the Popup will "
            em { +"overrule" }
            +" any explicit "
            c("placement")
            +" configuration in favour of rendering the overlay on an opposite side in order to make the content fit "
            +"into the available space."
        }

        componentFrame {
            coloredBox(Theme().colors.info) {
                +"Try to scroll the page completely down, so the overlay should appear really on bottom"
                br {}
                +"Then try to scroll some amounts upwards in multiple steps. You should recognize that the overlay "
                +"appears from some point on the left hand side and at least at top!"
            }
            createHoveringPopup(
                "\"prefer\" bottom",
                Placement.Bottom,
                flipping = true,
                text = """
Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore
 magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd
 gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing
 elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero 
 eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum 
 dolor sit amet.                
            """.trimIndent()
            )
        }

        highlight {
            source(
                """
                popup {
                    flipping(true) // could be omitted, as it is by default `true`!
                    placement { top }
                    trigger { toggle, close ->
                        div {
                            +"\"prefer\" bottom"
                            mouseenters.map { it.currentTarget } handledBy toggle
                            mouseleaves.map { } handledBy close
                        }
                    }
                    content {
                        div {
                            +"Lorem ipsum..."
                        }
                    }
                }                    
                """.trimIndent()
            )
        }
    }
}