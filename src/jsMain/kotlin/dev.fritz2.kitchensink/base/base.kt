package dev.fritz2.kitchensink.base

import dev.fritz2.dom.html.A
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.P
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.router
import dev.fritz2.styling.*
import dev.fritz2.styling.params.BasicParams
import dev.fritz2.styling.params.styled
import dev.fritz2.styling.staticStyle
import dev.fritz2.styling.style
import dev.fritz2.styling.whenever
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

fun RenderContext.showcaseHeader(text: String) {
    (::h1.styled {
        fontFamily { "Inter, sans-serif" }
        margins {
            top { "2rem" }
            bottom { ".25rem" }
        }
        lineHeight { tiny }
        fontWeight { "700" }
        fontSize { huge }
        letterSpacing { small }
    }) { +text }
}

fun RenderContext.showcaseSubSection(text: String) {
    (::h2.styled {
        fontFamily { "Inter, sans-serif" }
        margins {
            top { "4rem" }
            bottom { ".5rem" }
        }
        lineHeight { small }
        fontWeight { "600" }
        fontSize { normal }
        letterSpacing { small }
    }) { +text }
}

fun RenderContext.showcaseSection(text: String) {
    (::h3.styled {
        fontFamily { "Inter, sans-serif" }
        lineHeight { smaller }
        fontWeight { "600" }
        fontSize { large }
        letterSpacing { small }

        borders {
            left {
                width { "6px" }
                style { solid }
                color { primary }
            }
        }
        radii { left { small } }
        margins { top { "3rem !important" } }
        paddings { left { smaller } }
    }) { +text }
}

fun RenderContext.paragraph(
    styling: BasicParams.() -> Unit = {},
    baseClass: StyleClass? = null,
    id: String? = null,
    prefix: String = "paragraph",
    init: P.() -> Unit = {}
): P =
    ::p.styled(styling, baseClass, id, prefix) {
        fontFamily { "Inter, sans-serif" }
        margins {
            top { "1.25rem" }
        }
        lineHeight { larger }
        fontWeight { "400" }
        fontSize { normal }
        letterSpacing { small }
    } (init)

fun RenderContext.contentFrame(
        styling: BasicParams.() -> Unit = {},
        baseClass: StyleClass? = null,
        id: String? = null,
        prefix: String = "contentframe",
        init: Div.() -> Unit = {}
): Div =
        ::div.styled(styling, baseClass, id, prefix) {
            margins {
                top { "2rem" }
            }
            maxWidth(sm = { unset }, md = { "75%" }, lg = { "48rem" })
            paddings(
                    sm = {
                        top { normal }
                    },
                    md = {
                        top { huge }
                        left { normal }
                        right { normal }
                    }
            )
        }(init)

// todo: create ONE box function for all background boxes as soon as opacity/lightness problem is solved
fun RenderContext.infoBox(init: P.() -> Unit): Div {
    return (::div.styled {
        margins {
            top { larger }
            bottom { larger }
        }
        paddings {
            top { small }
            left { small }
            bottom { small }
            right { normal }
        }
        borders {
            left {
                width { "6px" }
                style { solid }
                color { info }
            }
        }
        radius { small }
        background {
            color { alterBrightness(info, 2.0) }
        }
    }){
        p {
            init()
        }
    }
}

fun RenderContext.warningBox(
    styling: BasicParams.() -> Unit = {},
    baseClass: StyleClass? = null,
    id: String? = null,
    prefix: String = "warningbox",
    init: Div.() -> Unit = {}
): Div =
    ::div.styled(styling, baseClass, id, prefix) {
        margins {
            top { larger }
            bottom { larger }
        }
        paddings {
            top { small }
            left { small }
            bottom { small }
            right { normal }
        }
        borders {
            left {
                width { "4px" }
                style { solid }
                color { danger }
            }
        }
        radius { small }
        background {
            color { alterBrightness(warning, 2.0) }
        }
    }(init)

fun RenderContext.componentFrame(padding: Boolean = true, init: Div.() -> Unit): Div { //Auslagerung von Komponente
    return (::div.styled {
        width { "100%" }
        margins {
            top { "1.25rem" }
        }
        border {
            width { thin }
            color { light }
        }
        radius { larger }
        if (padding) padding { normal }
    }){
        init()
    }
}

fun RenderContext.storeContentBox(
        styling: BasicParams.() -> Unit = {},
        baseClass: StyleClass? = null,
        id: String? = null,
        prefix: String = "storecontentbox",
        init: Div.() -> Unit = {}
): Div =
        (::div.styled {
            background {
                color { light }
            }
            margins {
                top { "1.25rem" }
            }
            paddings {
                left { "0.5rem" }
                right { "0.5rem" }
            }
            radius { larger }
        })(init)

val RenderContext.link
    get() = (::a.styled {
        fontSize { normal }
        color { primary }
        hover {
            color { tertiary }
            background { color { light_hover } }
            radius { normal }
        }
        css("cursor: pointer")
    })

fun RenderContext.externalLink(text: String, url: String, newTab: Boolean = true): A {
    return link {
        +text
        href(url)
        if (newTab) target("_new")
    }
}

fun RenderContext.internalLink(text: String, page: String): A {
    return link {
        +text
        clicks.map { page } handledBy router.navTo
    }
}

fun RenderContext.navAnchor(linkText: String, href: String): Div {
    return (::div.styled {
        radius { normal }
        border {
            width { none }
        }
        hover {
            background {
                color { light_hover }
            }
        }
        paddings {
            top { tiny }
            bottom { tiny }
            left { small }
            right { small }
        }
    }){
        (::a.styled {
            fontSize { normal }
            fontWeight { semiBold }
            color { dark }
        }) {
            +linkText
            href(href)
        }
    }
}

fun alterBrightness(color: String, brightness: Double): String {
    if (color.length != 7 || color[0] != '#') {
        console.log("wrong color input format")
    }
    val r: Long = color.subSequence(1,3).toString().toLong(16)
    val g: Long = color.subSequence(3,5).toString().toLong(16)
    val b: Long = color.subSequence(5,7).toString().toLong(16)

    val rgb = longArrayOf(r,g,b)
    var res = arrayOf("1", "2", "3")

    for (i: Int in 0..2) {
        var newCalc: Double = 1.0
        if (brightness > 1) {
            newCalc = rgb[i] + ((brightness-1) * ((255-rgb[i])))
        } else if (brightness < 1) {
            newCalc = rgb[i] - ((1-brightness) * (rgb[i]))
        } else return color

        var new: Int = newCalc.toInt()
        if (new > 255) { new = 255 }
        res[i] = new.toString(16)
        if (res[i].length == 1) { res[i] = "0" + res[i] }
    }
    return "#${res[0]}${res[1]}${res[2]}"
}

fun RenderContext.menuHeader(init: P.() -> Unit): P {
    return (::p.styled {
        paddings {
            top { large }
            left { small }
            right { small }
        }
        fontSize { small }
        fontWeight { bold }
        color { tertiary }
    })  {
        init()
    }
}


fun RenderContext.menuAnchor(linkText: String): P {

    val selected = style {
        width { "90%" }
        radius { normal }
        border {
            width { none }
        }
        background { color { primary_hover } }
        paddings {
            top { tiny }
            bottom { tiny }
            left { small }
            right { small }
        }
    }

    val isActive = router.data.map { hash -> hash == linkText }
        .distinctUntilChanged().onEach { if (it) PlaygroundComponent.update() }

    return (::p.styled {
        width { full }
        radius { normal }
        border {
            width { none }
        }
        hover {
            background {
                color { tertiary }
            }
        }
        paddings {
            top { tiny }
            bottom { tiny }
            left { small }
            right { small }
        }
//        fontSize { small }
        fontWeight { medium }
        css("cursor: pointer")
    }) {
        className(selected.whenever(isActive).name) // der Name der StyleClass wird das zu stylende Element (in diesem Fall der Div-Container) angehängt
        clicks.map { linkText } handledBy router.navTo
        +linkText
    }
}

val showcaseCode = staticStyle(
    "showcase-code", """
            padding: 2px 0.25rem;
            font-size: 0.875em;
            white-space: nowrap;
            line-height: normal;
            color: rgb(128, 90, 213);
            font-family: SFMono-Regular, Menlo, Monaco, Consolas, monospace;
    """
)

fun RenderContext.c(text: String) {
    span(showcaseCode.name) { +text }
}