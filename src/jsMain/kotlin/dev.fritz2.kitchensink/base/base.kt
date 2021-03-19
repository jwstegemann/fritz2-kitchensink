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

fun RenderContext.showcaseHeader(text: String) {
    (::h1.styled {
        margins {
            top { normal }
        }
        color { primary.base }
    }) { +text }
}

fun RenderContext.showcaseSection(text: String) {
    (::h2.styled {
        margins {
            top { giant }
        }
        color { primary.base }
    }) { +text }
}

fun RenderContext.showcaseSubSection(text: String) {
    (::h3.styled {
        margins {
            top { giant }
        }
        color { primary.base }
    }) { +text }
}

fun RenderContext.paragraph(
    styling: BasicParams.() -> Unit = {},
    baseClass: StyleClass = StyleClass.None,
    id: String? = null,
    prefix: String = "paragraph",
    init: P.() -> Unit = {}
): P = ::p.styled(styling, baseClass, id, prefix) {
        margins {
            top { small }
        }
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
        maxWidth(sm = { unset }, md = { "34rem" }, lg = { "52rem" })
        padding { large }
        radius { normal }
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
    label: String,
    init: RenderContext.() -> Unit = {}
): Div =
    (::div.styled {
        background {
            color { gray200 }
        }
        margins {
            top { "1.25rem" }
        }
        padding { smaller }
        paddings {
            left { normal }
        }
        radius { larger }
        width { full }
    }) {
        +label
        +": "
        init()
    }

val RenderContext.link
    get() = (::a.styled {
        padding { "0.2rem" }
        radius { small }
        color { secondary.base }
        hover {
            color { secondary.highlightContrast }
            background { color { secondary.highlight } }
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
            color { primary.highlightContrast }
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
            color { secondary.base }
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
        .distinctUntilChanged()//.onEach { if (it) PlaygroundComponent.update() }

    return (::p.styled {
        margins {
            top { tiny }
            bottom { tiny }
            left { none }
        }
        width { "90%" }
        radius { small }
        hover {
            color { primary.highlightContrast }
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
        paddings {
            left { ".4em" }
            right { ".4em" }
            top { ".2em" }
            bottom { ".2em" }
        }
        radius { small }
        margin { "0" }
        fontSize { "85%" }
        fontFamily { mono }
        color { gray600 }
        background { color { gray100 } }
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