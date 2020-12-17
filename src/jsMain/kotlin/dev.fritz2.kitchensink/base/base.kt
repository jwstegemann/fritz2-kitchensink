package dev.fritz2.kitchensink.base

import dev.fritz2.components.box
import dev.fritz2.dom.html.A
import dev.fritz2.dom.html.Div
import dev.fritz2.dom.html.P
import dev.fritz2.dom.html.RenderContext
import dev.fritz2.kitchensink.router
import dev.fritz2.styling.*
import dev.fritz2.styling.params.BasicParams
import dev.fritz2.styling.params.alterBrightness
import dev.fritz2.styling.params.styled
import dev.fritz2.styling.theme.Theme
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
            color { alterBrightness(info, 1.8) }
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
            color { alterBrightness(warning, 1.5) }
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
        color { secondary }
        hover {
            color { alterBrightness(secondary, 0.7) }
            background { color { alterBrightness(secondary, 1.5) } }
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
            color { primary }
        })  { +text }

        box({
            width { "100%" }
            height { "1px" }
            background {
                color { primary_hover }
            }
        }) {}
        box({
            margins {
                top { "1px" }
                left { "3%" }
            }
            width { "92%" }
            height { "1px" }
            background {
                color { primary_hover }
            }
        }) {}
        box({
            margins {
                top { "1px" }
            }
            width { "90%" }
            height { "1px" }
            background {
                color { primary_hover }
            }
        }) {}
    }
}

fun RenderContext.menuAnchor(linkText: String): P {

    val selected = style {
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
        margins { left { "1rem" } }
        width { "90%" }
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
            padding: 0px 0.15rem;
            font-size: ${Theme().fontSizes.normal};
            white-space: nowrap;
            line-height: ${Theme().lineHeights.larger};
            color: ${Theme().colors.primary};
            letter-spacing: ${Theme().letterSpacings.small};
            font-weight: 500;
    """
)

fun RenderContext.c(text: String) {
    span(showcaseCode.name) { +text }
}