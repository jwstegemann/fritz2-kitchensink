package dev.fritz2.kitchensink.base

import dev.fritz2.dom.html.A
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.P
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.router
import dev.fritz2.styling.StyleClass
import dev.fritz2.styling.name
import dev.fritz2.styling.params.BasicParams
import dev.fritz2.styling.params.ColorProperty
import dev.fritz2.styling.params.alterHexColorBrightness
import dev.fritz2.styling.params.styled
import dev.fritz2.styling.style
import dev.fritz2.styling.whenever
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

fun RenderContext.showcaseHeader(text: String) {
    (::h1.styled {
        margins {
            top { normal }
            bottom { normal }
        }
        color { primary.base }
        lineHeight { tiny }
        fontWeight { bolder }
        fontSize { huge }
    }) { +text }
}

fun RenderContext.showcaseSubSection(text: String) {
    (::h2.styled {
        margins {
            top { "4rem" }
            bottom { ".5rem" }
        }
        lineHeight { small }
        fontWeight { bold }
        fontSize { normal }
    }) { +text }
}

fun RenderContext.showcaseSection(text: String) {
    (::h3.styled {
        fontFamily { "Inter, sans-serif" }
        lineHeight { smaller }
        fontWeight { "600" }
        fontSize { large }
        color { primary.base }
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
        background { color { neutral } }
        margins(sm = {
            top { "4rem" }
        }, md = {
            top { "5rem" }
            bottom { huge }
        })
        maxWidth(sm = { unset }, md = { "75%" }, lg = { "48rem" })
        padding { normal }
        radius { small }
        boxShadow { flat }
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
            color { gray300 }
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
            color { gray200 }
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
        color { tertiary.base }
        hover {
            color { tertiary.complementary }
            background { color { tertiary.highlight } }
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
        color { primary.base }
        hover {
            color { primary.complementary }
            background {
                color { primary.highlight }
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
            fontSize { normal }
            fontWeight { bold }
            letterSpacing { large }
            color { tertiary.base }
        })  { +text }
    }
}

fun RenderContext.menuAnchor(linkText: String): P {

    val selected = style {
        color { gray100 }
        background {
            color { primary.base }
        }
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
            color { primary.complementary }
            background { color { primary.highlight } }
        }
        paddings {
            top { tiny }
            bottom { tiny }
            left { small }
            right { small }
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

fun RenderContext.c(text: String) {
    (::span.styled {
//        padding { "0px 0.15rem" }
        paddings {
            left { "2px" }
            right { "2px" }
            top { "2px" }
            bottom { "3px" }
        }
        fontSize { inherit }
        fontWeight { semiBold }
        fontFamily { mono }
        lineHeight { larger }
        color { gray600 }
        css("white-space: nowrap")
    }) { +text }
}

fun RenderContext.teaserText(
    init: Div.() -> Unit = {}
): Div =
    ::div.styled {
        fontSize { small }
        textTransform { capitalize }
        color { primary.base }
        fontWeight { semiBold }
        margins { bottom { "0.7rem" } }
        fontSize { small }
    }(init)