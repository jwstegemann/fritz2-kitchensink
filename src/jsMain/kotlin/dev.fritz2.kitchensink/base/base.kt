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
import dev.fritz2.styling.theme.IconDefinition
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
            target("_blank")
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

val githubIcon: IconDefinition = IconDefinition(
    "github",
    "0 0 496 512",
    """
    <path fill="currentColor" d="M165.9 397.4c0 2-2.3 3.6-5.2 3.6-3.3.3-5.6-1.3-5.6-3.6 0-2 2.3-3.6 5.2-3.6 3-.3 5.6 1.3 
    5.6 3.6zm-31.1-4.5c-.7 2 1.3 4.3 4.3 4.9 2.6 1 5.6 0 6.2-2s-1.3-4.3-4.3-5.2c-2.6-.7-5.5.3-6.2 
    2.3zm44.2-1.7c-2.9.7-4.9 2.6-4.6 4.9.3 2 2.9 3.3 5.9 2.6 2.9-.7 4.9-2.6 
    4.6-4.6-.3-1.9-3-3.2-5.9-2.9zM244.8 8C106.1 8 0 113.3 0 252c0 110.9 69.8 205.8 169.5 239.2 12.8 
    2.3 17.3-5.6 17.3-12.1 0-6.2-.3-40.4-.3-61.4 0 0-70 15-84.7-29.8 0 0-11.4-29.1-27.8-36.6 0 
    0-22.9-15.7 1.6-15.4 0 0 24.9 2 38.6 25.8 21.9 38.6 58.6 27.5 72.9 20.9 2.3-16 8.8-27.1 
    16-33.7-55.9-6.2-112.3-14.3-112.3-110.5 0-27.5 7.6-41.3 23.6-58.9-2.6-6.5-11.1-33.3 2.6-67.9 
    20.9-6.5 69 27 69 27 20-5.6 41.5-8.5 62.8-8.5s42.8 2.9 62.8 8.5c0 0 48.1-33.6 69-27 13.7 34.7 
    5.2 61.4 2.6 67.9 16 17.7 25.8 31.5 25.8 58.9 0 96.5-58.9 104.2-114.8 110.5 9.2 7.9 17 22.9 17 
    46.4 0 33.7-.3 75.4-.3 83.6 0 6.5 4.6 14.4 17.3 12.1C428.2 457.8 496 362.9 496 252 496 113.3 383.5 
    8 244.8 8zM97.2 352.9c-1.3 1-1 3.3.7 5.2 1.6 1.6 3.9 2.3 5.2 1 1.3-1 
    1-3.3-.7-5.2-1.6-1.6-3.9-2.3-5.2-1zm-10.8-8.1c-.7 1.3.3 2.9 2.3 3.9 1.6 1 3.6.7 
    4.3-.7.7-1.3-.3-2.9-2.3-3.9-2-.6-3.6-.3-4.3.7zm32.4 35.6c-1.6 1.3-1 4.3 1.3 6.2 2.3 2.3 
    5.2 2.6 6.5 1 1.3-1.3.7-4.3-1.3-6.2-2.2-2.3-5.2-2.6-6.5-1zm-11.4-14.7c-1.6 1-1.6 3.6 0 5.9 1.6 
    2.3 4.3 3.3 5.6 2.3 1.6-1.3 1.6-3.9 0-6.2-1.4-2.3-4-3.3-5.6-2z" />                                        
    """.trimIndent()
)