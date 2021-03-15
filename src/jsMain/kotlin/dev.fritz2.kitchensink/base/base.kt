package dev.fritz2.kitchensink.base

import dev.fritz2.dom.html.A
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.P
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.router
import dev.fritz2.styling.*
import dev.fritz2.styling.params.BasicParams
import dev.fritz2.styling.params.ColorProperty
import dev.fritz2.styling.params.alterHexColorBrightness
import dev.fritz2.styling.params.styled
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

fun RenderContext.showcaseHeader(text: String) {
    (::h1.styled {
        fontFamily { "Inter, sans-serif" }
        margins {
            top { "2rem" }
            bottom { "2rem" }
        }
        color { primary }
        lineHeight { tiny }
        fontWeight { "700" }
        fontSize { huge }
        paddings { bottom { normal } }
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
        color { primary }
        letterSpacing { small }
        radii { left { small } }
        margins { top { "3rem !important" } }
    }) { +text }
}

fun RenderContext.paragraph(
    styling: BasicParams.() -> Unit = {},
    baseClass: StyleClass = StyleClass.None,
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
    }(init)

fun RenderContext.contentFrame(
    styling: BasicParams.() -> Unit = {},
    baseClass: StyleClass = StyleClass.None,
    id: String? = null,
    prefix: String = "contentframe",
    init: Div.() -> Unit = {}
): Div =
    ::div.styled(styling, baseClass, id, prefix) {
        margins {
            top { huge }
            bottom { huge }
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

fun RenderContext.coloredBox(baseColorAsHex: ColorProperty, init: P.() -> Unit): Div {
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
                color { baseColorAsHex }
            }
        }
        radius { small }
        background {
            color { alterHexColorBrightness(baseColorAsHex, 1.8) }
        }
    }){
        p {
            init()
        }
    }
}

fun RenderContext.componentFrame(padding: Boolean = true, init: Div.() -> Unit): Div { //Auslagerung von Komponente
    return (::div.styled {
        width { "100%" }
        margins {
            top { "1.25rem" }
        }
        border {
            width { thin }
            color { lightGray }
        }
        radius { small }
        if (padding) padding { normal }
    }){
        init()
    }
}

fun RenderContext.storeContentBox(
    init: Div.() -> Unit = {}
): Div =
    (::div.styled {
        background {
            color { lighterGray }
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
        paddings {
            left { "2px" }
            right { "2px" }
            top { "2px" }
            bottom { "3px" }
        }
        fontSize { inherit }
        color { secondary }
        hover {
            color { primary }
            background { color { primaryEffect } }
            radius { small }
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
        radius { small }
        border {
            width { none }
        }
        hover {
            background {
                color { lighterGray }
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
            textDecoration { initial }
            color { dark }
        }) {
            +linkText
            href(href)
        }
    }
}

fun RenderContext.menuHeader(text: String): Div {
    return (::div.styled {
        width { "100%" }
    }) {
        (::p.styled {
            paddings {
                top { large }
                left { tiny }
                right { small }
            }
            fontSize { small }
            fontWeight { bold }
            letterSpacing { giant }
            color { secondary }
        })  { +text }
    }
}

fun RenderContext.menuAnchor(linkText: String): P {

    val selected = style {
        color { primaryEffect }
    }

    val isActive = router.data.map { hash -> hash == linkText }
        .distinctUntilChanged().onEach { if (it) PlaygroundComponent.update() }

    return (::p.styled {
        margins {
            top { tiny }
            bottom { tiny }
            left { none }
        }
        width { "90%" }
        radius { small }
        hover {
            color { primary }
            background { color { base } }
        }
        paddings {
            top { "0.05rem" }
            bottom { "0.05rem" }
            left { tiny }
            right { tiny }
        }
        css("""text-overflow: ellipsis; overflow: hidden;""")
        fontWeight { medium }
        css("cursor: pointer")

    }) {
        className(selected.whenever(isActive).name)
        clicks.map { linkText } handledBy router.navTo
        +linkText
    }
}

val codeInText = staticStyle(
    "showcasecode", """
        white-space: nowrap;
        font-family: Courier;
    """
)

fun RenderContext.c(text: String) {
    (::span.styled(baseClass = codeInText) {
        padding { "0px 0.15rem" }
        fontSize { normal }
        fontWeight { "650" }
        fontFamily { "Courier" } // todo: added static code because this does not work
        lineHeight { larger }
        color { primary }
        letterSpacing { small }
    }) { +text }
}

fun RenderContext.teaserText(
    init: Div.() -> Unit = {}
): Div =
    ::div.styled {
        fontSize { small }
        textTransform { capitalize }
        color { info }
        fontWeight { semiBold }
        margins { bottom { "0.7rem" } }
        fontSize { small }
    }(init)